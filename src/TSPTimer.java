import algs4.StdOut;
import algs4.StdRandom;
import algs4.Stopwatch;

public class TSPTimer {

    public static void main(String[] args) {
        double lo = 0.0;
        double hi = 600.0;
        int n = Integer.parseInt(args[0]);

        // INGENUA
        StdOut.println("Heuristica Ingenua (N=" + n + ")");
        StdRandom.setSeed(123456789L);
        Stopwatch timer1 = new Stopwatch();
        Tour tour1 = new Tour();
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniformDouble(lo, hi);
            double y = StdRandom.uniformDouble(lo, hi);
            Point p = new Point(x, y);
            tour1.insertNearest(p);
        }
        double length1 = tour1.length();
        double elapsed1 = timer1.elapsedTime();
        StdOut.println("Comprimento (Ingenua) = " + length1);
        StdOut.println("Tempo (s) ingenuo: " + elapsed1 + " segundos");

        // KDTREE
        StdOut.println("\nHeuristica com KdTree (N=" + n + ")");
        StdRandom.setSeed(123456789L);
        Stopwatch timer2 = new Stopwatch();
        Tour tour2 = new Tour(true);
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniformDouble(lo, hi);
            double y = StdRandom.uniformDouble(lo, hi);
            Point p = new Point(x, y);
            tour2.insertNearest(p);
        }
        double length2 = tour2.length();
        double elapsed2 = timer2.elapsedTime();
        StdOut.println("Comprimento (KdTree) = " + length2);
        StdOut.println("Tempo (s) com KdTree: " + elapsed2 + " segundos");
    }
}