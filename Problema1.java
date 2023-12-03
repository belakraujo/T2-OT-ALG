public class Problema1 {

    public static void solveP1(int[] l, int[] h) {
        // n é o número de semanas
        int n = l.length;
        // dp é uma matriz que armazena o valor máximo que pode ser alcançado até a
        // semana i
        int[][] dp = new int[n + 1][2];
        // plano é uma matriz que armazena a sequência de tarefas que levam ao valor
        // máximo até a semana i
        String[][] plano = new String[n + 1][2];

        // Inicializa a primeira semana
        dp[0][0] = l[0];
        dp[0][1] = h[0];
        plano[0][0] = "Tarefa de baixa dificuldade na semana 1\n";
        plano[0][1] = "Sem tarefa na semana 1\nTarefa de alta dificuldade na semana 2\n";

        // Loop para calcular o valor máximo e o plano correspondente para cada semana
        for (int i = 1; i < n; i++) {
            // O valor máximo na semana i se uma tarefa de baixa dificuldade for escolhida
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]) + l[i];
            // O plano correspondente
            plano[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]) == dp[i - 1][0]
                    ? plano[i - 1][0] + "Tarefa de baixa dificuldade na semana " + (i + 1) + "\n"
                    : plano[i - 1][1] + "Tarefa de baixa dificuldade na semana " + (i + 1) + "\n";

            // O valor máximo na semana i se uma tarefa de alta dificuldade for escolhida
            dp[i][1] = i > 1 ? dp[i - 2][0] + h[i] : h[i];
            // O plano correspondente
            plano[i][1] = i > 1
                    ? plano[i - 2][0] + "Sem tarefa na semana " + (i + 1) + "\n"
                            + "Tarefa de alta dificuldade na semana " + (i + 2) + "\n"
                    : plano[0][1];
        }

        // O valor máximo alcançado no final de todas as semanas
        int valor_maximo = Math.max(dp[n - 1][0], dp[n - 1][1]);
        // O plano correspondente
        String plano_maximo = valor_maximo == dp[n - 1][0] ? plano[n - 1][0] : plano[n - 1][1];

        // Imprime o valor máximo e o plano correspondente
        System.out.println("Valor máximo: " + valor_maximo);
        System.out.println("Plano: \n" + plano_maximo);
    }

    public static void main(String[] args) {
        // l é um array que armazena os valores de baixa dificuldade por semana
        int[] l = { 10, 1, 10, 10 };
        // h é um array que armazena os valores de alta dificuldade por semana
        int[] h = { 5, 50, 5, 1 };
        // Chama a função para resolver o problema
        solveP1(l, h);
    }

}
