package kdtree;

import java.util.Iterator;
import java.util.LinkedList;

import kdtree.KDNode.Orientation;

public class KDTree implements Iterable<Point> {

    protected KDNode root;
    protected int depth;

    public KDTree() {
        root = null;
    }

    /**
     * Insert a new point into the tree
     * @param pt point to insert
     * @return if the point could be added (duplicates are not allowed)
     */
    public boolean insert(Point pt) {
        if (root == null) {
            root = new KDNode(pt, Orientation.VERTICAL);
            return true;
        }

        return insert(pt, root);
    }

    //helper method//
    private boolean insert(Point pt, KDNode node) {
        // Compare the point to the current node
        int c = node.compareTo(pt);

        if (c == -1) { // The point is below n
            if (node.below == null) node.setBelow(pt); // We are done
            else return insert(pt, node.below);

        } else if (c == 1) { // The point is above n
            if (node.above == null) node.setAbove(pt);
            else return insert(pt, node.above);

        } else { // The point exists in the tree
            return false;
        }

        return true;
    }

    /**
     * Determines if a point is in this instance of KDTree
     *
     * @param pt point of interest
     * @return true if tree contains pt
     */
    public boolean contains(Point pt) {
        return contains(pt, root);
    }

    //helper method//
    private boolean contains(Point pt, KDNode node) {
        if (node == null) return false; // Base case

        // Compare the point to the current node
        int c = node.compareTo(pt);

        if (c == 0) 		return true;
        else if (c == -1) 	return contains(pt, node.below);
        else 				return contains(pt, node.above);

    }

    /**
     * Return the node associated with point pt
     *
     * @param pt point of interest
     * @return Instance of KDNode
     */
    public KDNode get(Point pt) {
        depth = 0;
        return get(pt, root);
    }

    //helper method//
    private KDNode get(Point pt, KDNode node) {
        if (node == null) return null; // Base case

        // Compare the point to the current node
        int c = node.compareTo(pt);
        depth++;

        if (c == 0) 		return node;
        else if (c == -1) 	return get(pt, node.below);
        else 				return get(pt, node.above);
    }

    /**
     * Return a list of all the nodes in order (left to right)
     *
     * @return Linked List of Points
     */
    public LinkedList<Point> inOrder() {
        return inOrder(root);
    }

    //helper method//
    private LinkedList<Point> inOrder(KDNode n) {
        LinkedList<Point> list = new LinkedList<>();
        if (n.below != null) {
            list.addAll(inOrder(n.below));
        }

        list.add(n.getPoint());

        if (n.above != null) {
            list.addAll(inOrder(n.above));
        }

        return list;
    }

    @Override
    public Iterator<Point> iterator() {
        return this.inOrder().iterator();
    }

}
