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
        copy.left = left.clone();
        copy.right = left.clone();
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
}
