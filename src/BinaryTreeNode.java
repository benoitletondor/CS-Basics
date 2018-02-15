import java.util.Iterator;
import java.util.List;

public class BinaryTreeNode<V> implements Iterable<V>, Cloneable {

    private V value;
    private BinaryTreeNode<V> left;
    private BinaryTreeNode<V> right;

// --------------------------------------->

    public BinaryTreeNode(V value) {
        this.value = value;
    }

// --------------------------------------->

    public void setLeft(BinaryTreeNode<V> node) {
        this.left = node;
    }

    public void setRight(BinaryTreeNode<V> node) {
        this.right = node;
    }

    public BinaryTreeNode<V> getLeft() {
        return left;
    }

    public BinaryTreeNode<V> getRight() {
        return right;
    }

    public V getValue() {
        return value;
    }

// --------------------------------------->

    public List<V> asList() {
        List<V> values = new ArrayList<>();

        if( left != null ) {
            values.addAll(left.asList());
        }

        values.add(value);

        if( right != null ) {
            values.addAll(right.asList());
        }

        return values;
    }

    @Override
    public Iterator<V> iterator() {
        return asList().iterator();
    }

    @Override
    protected BinaryTreeNode<V> clone() throws CloneNotSupportedException {
        final BinaryTreeNode<V> copy = new BinaryTreeNode<V>(value);

        if (left != null) {
            copy.left = left.clone();
        }

        if( right != null ) {
            copy.right = left.clone();
        }

        return copy;
    }

    public int getHeight() {
        if( left == null && right == null ) {
            return 0;
        }

        int leftHeight = left == null ? 0 : left.getHeight();
        int rightHeight = right == null ? 0 : right.getHeight();

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Compute the sum of the subtree with the maximum sum value.
     *
     * @return the sum of the biggest subtree
     * @throws IllegalArgumentException if the binary tree values aren't integers
     */
    public int getMaxSumSubtree() {
        final MaxSumResult result = new MaxSumResult();

        if( !(value instanceof Integer) ) {
            throw new IllegalArgumentException("Only works with integer trees");
        }

        //noinspection ResultOfMethodCallIgnored
        computeMaxSumSubtree((BinaryTreeNode<Integer>) this, result);

        return result.value;
    }

    private int computeMaxSumSubtree(BinaryTreeNode<Integer> node, MaxSumResult result) {
        if( node == null ) {
            return 0;
        }

        int sumLeftSubtree = computeMaxSumSubtree(node.left, result);
        int sumRightSubtree = computeMaxSumSubtree(node.right, result);
        int sum = node.value + Math.max(0, sumLeftSubtree) + Math.max(0, sumRightSubtree);

        if( sum > result.value ) {
            result.value = sum;
        }

        return sum;
    }

    /**
     * Wrapper for the {@link #getMaxSumSubtree()} result to make it mutable
     */
    private static class MaxSumResult {
        int value = Integer.MIN_VALUE;
    }
}
