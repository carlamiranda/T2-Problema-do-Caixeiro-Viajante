import algs4.Point2D;

public class KdTree {

    private static class Node {
        Point2D point;
        Node left, right;
        boolean vertical;

        Node(Point2D p, boolean vertical) {
            this.point = p;
            this.vertical = vertical;
        }
    }

    private Node root;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, vertical);
        }

        if (node.point.equals(p)) return node;

        if (vertical) {
            if (p.x() < node.point.x())
                node.left = insert(node.left, p, !vertical);
            else
                node.right = insert(node.right, p, !vertical);
        } else {
            if (p.y() < node.point.y())
                node.left = insert(node.left, p, !vertical);
            else
                node.right = insert(node.right, p, !vertical);
        }

        return node;
    }

    public Point2D nearest(Point2D p) {
        if (root == null) return null;
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D p, Point2D best) {
        if (node == null) return best;

        if (p.distanceSquaredTo(node.point) < p.distanceSquaredTo(best))
            best = node.point;

        Node first, second;
        if ((node.vertical && p.x() < node.point.x()) ||
            (!node.vertical && p.y() < node.point.y())) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        best = nearest(first, p, best);

        double splitDist = node.vertical ?
                Math.pow(p.x() - node.point.x(), 2) :
                Math.pow(p.y() - node.point.y(), 2);

        if (p.distanceSquaredTo(best) > splitDist)
            best = nearest(second, p, best);

        return best;
    }
}
