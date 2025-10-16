import algs4.Point2D;

public class KdTree {

    private static class Node {
        Point2D point;   
        Tour.Node tourNode;
        Node left, right;
        boolean vertical;

        Node(Point2D p, Tour.Node tNode, boolean vertical) {
            this.point = p;
            this.tourNode = tNode;
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

    public void insert(Point2D p, Tour.Node tNode) {
        root = insert(root, p, tNode, true);
    }

    private Node insert(Node node, Point2D p, Tour.Node tNode, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, tNode, vertical);
        }

        if (node.point.equals(p)) return node;

        if (vertical) {
            if (p.x() < node.point.x())
                node.left = insert(node.left, p, tNode, !vertical);
            else
                node.right = insert(node.right, p, tNode, !vertical);
        } else {
            if (p.y() < node.point.y())
                node.left = insert(node.left, p, tNode, !vertical);
            else
                node.right = insert(node.right, p, tNode, !vertical);
        }

        return node;
    }

    public Tour.Node nearestNode(Point2D query) {
        if (root == null) return null;
        return nearestNode(root, query, root.tourNode);
    }

    private Tour.Node nearestNode(Node node, Point2D query, Tour.Node bestTourNode) {
        if (node == null) return bestTourNode;

        double bestDist = query.distanceSquaredTo(node.point);
        Point2D bestPoint = new Point2D(bestTourNode.point.x(), bestTourNode.point.y());
        if (bestDist < query.distanceSquaredTo(bestPoint))
            bestTourNode = node.tourNode;

        Node first, second;
        if ((node.vertical && query.x() < node.point.x()) ||
            (!node.vertical && query.y() < node.point.y())) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        bestTourNode = nearestNode(first, query, bestTourNode);
        bestPoint = new Point2D(bestTourNode.point.x(), bestTourNode.point.y());

        double splitDist = node.vertical ?
            Math.pow(query.x() - node.point.x(), 2) :
            Math.pow(query.y() - node.point.y(), 2);

        if (query.distanceSquaredTo(bestPoint) > splitDist)
            bestTourNode = nearestNode(second, query, bestTourNode);

        return bestTourNode;
    }
}
