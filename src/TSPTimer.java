/* *****************************************************************************
 *  VOCÊ NÃO PRECISA MODIFICAR ESTE ARQUIVO
 *
 *  Compilação:  javac TSPTimer.java
 *  Execução:    java -Xint TSPTimer n
 *  Dependências: Tour.java Point.java Stopwatch.java StdOut.java
 *
 *  Mede o tempo da heurística do vizinho mais próximo gerando instâncias aleatórias de tamanho n.
 *
 *  Exemplo de execução:
 *  % java -Xint TSPTimer 1000
 *
 *  Observação: os arquivos de entrada (caso utilizados) devem estar na pasta data/.
 *
 **************************************************************************** */

 import algs4.StdRandom;
import algs4.Stopwatch;
import algs4.StdOut;

public class TSPTimer {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdRandom.setSeed(123456789L); 

        // Gera n pontos aleatórios para usar em ambos os testes
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniformDouble(0.0, 1000.0);
            double y = StdRandom.uniformDouble(0.0, 1000.0);
            points[i] = new Point(x, y);
        }


        
        // // --- 1. Teste da abordagem ingênua (sem KdTree) ---
        // Stopwatch timerNaive = new Stopwatch();
        // Tour tourNaive = new Tour(false); 
        // for (Point p : points) {
        //     tourNaive.insertNearest(p);
        // }
        // double timeNaive = timerNaive.elapsedTime();
        
        // StdOut.printf("Para n = %d\n", n);
        // StdOut.println("-------------------------------------------------");
        // StdOut.printf("Tempo (s) ingênuo:             %.4f\n", timeNaive);




        // --- 2. Teste da abordagem com KdTree ---
        Stopwatch timerKdTree = new Stopwatch();
        Tour tourKdTree = new Tour(true); // O 'true' ativa a KdTree
        for (Point p : points) {
            tourKdTree.insertNearest(p);
        }
        double timeKdTree = timerKdTree.elapsedTime();

        StdOut.printf("Tempo (s) com KdTree:          %.4f\n", timeKdTree);
        StdOut.println(); 
    }
}