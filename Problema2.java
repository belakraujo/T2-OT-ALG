import java.util.*;

// Define a classe Item com atributos: peso (weight), valor (value), índice (index) e custo (cost).
class Item {
    int weight, value, index;
    double cost;

    // Construtor que inicializa o índice, peso, valor e calcula o custo
    Item(int index, int weight, int value) {
        this.index = index;
        this.weight = weight;
        this.value = value;
        cost = (double) value / weight; // Calcula o custo como valor/peso
    }
}

// Define a classe Node com atributos: nível (level), lucro (profit), bound e
// peso (weight).
class Node {
    int level, profit, bound, weight;
    List<Integer> itemsIncluded; // Lista que armazena os itens incluídos

    // Construtor que inicializa a lista de itens incluídos
    Node() {
        itemsIncluded = new ArrayList<>();
    }
}

public class Problema2 {
    static Item[] items;

    // Função que calcula a fronteira por item
    static int bound(Node u, int n, int W) {
        // Se o peso do item ultrapassar o peso máximo, retorna 0
        if (u.weight >= W)
            return 0;

        // Inicializa bound com o lucro do item
        int profit_bound = u.profit;
        int j = u.level + 1;
        int totalweight = u.weight;

        // Enquanto o item não ultrapassar o limite de peso, soma os pesos e valores dos
        // itens
        while ((j < n) && (totalweight + items[j].weight <= W)) {
            totalweight += items[j].weight;
            profit_bound += items[j].value;
            j++;
        }

        // Se houver espaço restante após inclusão de j itens
        if (j < n)
            profit_bound += (W - totalweight) * items[j].cost; // Adiciona fração do j+1th item

        return profit_bound;
    }

    // Função que resolve o problema da mochila
    static void solveP2(int n, int wi[], int vi[], int W) {

        // Cria um objeto para cada Item
        items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(i, wi[i], vi[i]);
        }

        // Ordena os itens em ordem decrescente de custo
        Arrays.sort(items, (i1, i2) -> Double.compare(i2.cost, i1.cost));

        // Cria uma lista para armazenar os nós (itens)
        Queue<Node> Q = new LinkedList<>();
        Node u, v;

        // Criação do primeiro nó (raiz da árvore)
        u = new Node();
        u.level = -1;
        u.profit = u.weight = 0;

        // Adiciona o primeiro nó em Q
        Q.offer(u);

        // Inicializa maxProfit com 0
        int maxProfit = 0;
        List<Integer> resultList = null; // Lista que armazena o posição dos items depois da solução

        // Enquanto a fila não esta vazia
        while (!Q.isEmpty()) {
            u = Q.poll(); // Pega o nó do começo da fila

            if (u.level == -1)
                v = new Node();
            else
                v = new Node();

            // Se não é o último nível
            if (u.level != n - 1) {
                v.level = u.level + 1; // v se torna filho de u
                v.weight = u.weight + items[v.level].weight;
                v.profit = u.profit + items[v.level].value;

                // Se o peso de v é menor que W e seu lucro é maior que maxProfit, atualiza
                // maxProfit
                if (v.weight <= W && v.profit > maxProfit) {
                    maxProfit = v.profit;
                    resultList = v.itemsIncluded;
                }

                v.bound = bound(v, n, W);

                // Se o bound é maior que maxProfit, coloca v na fila
                if (v.bound > maxProfit)
                    Q.offer(v);
            }
            // atualiza v para próximo nó do nível
            v = new Node();
            v.level = u.level + 1;
            v.weight = u.weight;
            v.profit = u.profit;
            v.bound = bound(v, n, W);

            // Se o bound é maior que maxProfit, coloca v na fila
            if (v.bound > maxProfit)
                Q.offer(v);
        }

        System.out.println("Os itens na mochila sao: " + resultList);
        System.out.println("O valor máximo é: " + maxProfit);
    }

    // Função Principal
    public static void main(String[] args) {
        int n = 4; // Número de itens
        int[] wi = { 2, 5, 7, 8 }; // Pesos dos itens
        int[] vi = { 20, 10, 42, 56 }; // Valores dos itens
        int W = 17; // Peso máximo da mochila

        solveP2(n, wi, vi, W); // Chama a função que resolve o problema
    }
}