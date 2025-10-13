import algs4.Point2D;
import algs4.StdDraw;
import algs4.StdOut;

public class Tour {

    private static class Node {
        private Point point;
        private Node next;
    }

    private Node start;
    private int count;
    private final boolean useKdTree;
    private KdTree tree;


    public Tour() {
        this(false);
    }

    public Tour(boolean useKdTree) {
    this.useKdTree = useKdTree;
    this.start = null;
    this.count = 0;
    if (useKdTree) {
        this.tree = new KdTree();
    }
}

    public Tour(Point a, Point b, Point c, Point d) {
        this();
        insertNearestNaive(a);
        insertNearestNaive(b);
        insertNearestNaive(c);
        insertNearestNaive(d);
    }

    public int size() {
        return count;
    }

    public double length() {
        if (start == null || start.next == start)
            return 0.0;

        double total = 0.0;
        Node current = start;
        do {
            total += current.point.distanceTo(current.next.point);
            current = current.next;
        } while (current != start);
        return total;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (start == null)
            return "(Tour vazio)";

        Node current = start;
        do {
            sb.append(current.point.toString()).append("\n");
            current = current.next;
        } while (current != start);

        return sb.toString();
    }

    public void draw() {
        if (start == null || start.next == start)
            return;

        Node current = start;
        do {
            current.point.drawTo(current.next.point);
            current = current.next;
        } while (current != start);
    }

    public void insertNearest(Point p) {
        if (useKdTree) {
            insertNearestKd(p); // ainda não implementado
        } else {
            insertNearestNaive(p);
        }
    }

    public void insertNearestNaive(Point p) {
        // <<< implementação correta >>>
        if (start == null) {
            start = new Node();
            start.point = p;
            start.next = start; // circular
            count = 1;
            return;
        }

        Node nearest = start;
        double minDist = p.distanceTo(start.point);

        Node current = start.next;
        while (current != start) {
            double dist = p.distanceTo(current.point);
            if (dist < minDist) {
                minDist = dist;
                nearest = current;
            }
            current = current.next;
        }

        Node newNode = new Node();
        newNode.point = p;
        newNode.next = nearest.next;
        nearest.next = newNode;

        count++;
    }

    public void insertNearestKd(Point p) {
        if (start == null) {
            start = new Node();
            start.point = p;
            start.next = start; // circular
            count = 1;
            tree.insert(new Point2D(p.x(), p.y()));
            return;
        }

        // encontra o ponto mais próximo via KdTree
        Point2D nearest2D = tree.nearest(new Point2D(p.x(), p.y()));
        Point nearestPoint = new Point(nearest2D.x(), nearest2D.y());

        // percorre a lista circular para achar o Node correspondente
        Node current = start;
        while (!current.point.equals(nearestPoint)) {
            current = current.next;
        }

        // insere o novo ponto após o vizinho mais próximo
        Node newNode = new Node();
        newNode.point = p;
        newNode.next = current.next;
        current.next = newNode;

        count++;

        // adiciona o ponto na KdTree
        tree.insert(new Point2D(p.x(), p.y()));
    }


    // Método de teste (opcional)
    public static void main(String[] args) {
        Tour tour = new Tour();
        tour.insertNearest(new Point(1.0, 1.0));
        tour.insertNearest(new Point(1.0, 4.0));
        tour.insertNearest(new Point(4.0, 4.0));
        tour.insertNearest(new Point(4.0, 1.0));

        StdOut.println("# de pontos = " + tour.size());
        StdOut.println("Comprimento = " + tour.length());
        StdOut.println(tour);

        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        tour.draw();
    }
}
