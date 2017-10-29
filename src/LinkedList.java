import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<E> implements List<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

// --------------------------------------->

    public LinkedList() {
        size = 0;
    }

// --------------------------------------->

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size > 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        final Object[] values = toArray();

        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @Override
            public E next() {
                final E value = (E) values[index];
                index++;
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {
        final Object[] array = new Object[size];

        if( size == 0 ) {
            return array;
        }

        int i = 0;
        for(Node<E> node = first; node != null; node = node.getNext()) {
            array[i] = node;
            i++;
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }

    @Override
    public boolean add(E e) {
        if( size == 0 ) {
            size++;
            first = new Node<>(e);
            last = first;
            return true;
        }

        final Node<E> newNode = new Node<E>(e);
        last.setNext(newNode);
        newNode.setPrevious(last);
        last = newNode;

        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if( size == 0 ) {
            return false;
        }

        for(Node<E> node = first; node != null; node = node.getNext()) {
            if( node.getValue().equals(o) ) {
                size--;

                if( node == first ) {
                    if( size > 1 ) {
                        node.getNext().setPrevious(null);
                        first = node.getNext();
                    } else {
                        first = null;
                    }
                } else if( node == last ) {
                    node.getPrevious().setNext(null);
                    last = node.getPrevious();
                } else {
                    node.getPrevious().setNext(node.getNext());
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c ) {
            if( !contains(o) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E o : c ) {
            add(o);
        }

        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        if( index >= size ) {
            throw new IllegalArgumentException("index should be < size");
        }

        if( index == size - 1 ) { // Last element
            add(element);
            return;
        }

        final Node<E> newNode = new Node<>(element);
        size++;

        if( index == 0 ) { // First element
            first.setPrevious(newNode);
            newNode.setNext(first);
            first = newNode;
            return;
        }

        Node<E> node = first;
        for(int i = 0; i<=index; i++) {
            node = node.getNext();
        }

        newNode.setPrevious(node.getPrevious());
        newNode.setNext(node);

        node.setPrevious(newNode);
    }

    @Override
    public E remove(int index) {
        if( index >= size ) {
            throw new IllegalArgumentException("index should be < size");
        }

        if( size == 1 ) {
            E value = first.value;
            first = last = null;
            size = 0;
            return value;
        }

        size --;

        if( index == size - 1 ) { // Last element
            E value = last.getValue();
            last = last.getPrevious();
            last.setNext(null);
            return value;
        }

        if( index == 0 ) { // First element
            E value = first.value;
            first = first.getNext();
            first.setPrevious(null);
            return value;
        }

        Node<E> node = first;
        for(int i = 0; i < index; i++) {
            node = node.getNext();
        }

        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());

        return node.value;
    }

    @Override
    public int indexOf(Object o) {
        if( size == 0 ) {
            return -1;
        }

        int i = 0;
        for(Node<E> node = first; node != null; node = node.getNext()) {
            if( node.getValue().equals(o) ) {
                return i;
            }

            i++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if( size == 0 ) {
            return -1;
        }

        int i = size - 1;
        for(Node<E> node = last; node != null; node = node.getPrevious()) {
            if( node.getValue().equals(o) ) {
                return i;
            }

            i--;
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null; // TODO
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null; // TODO
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null; // TODO
    }

// ----------------------------------------->

    private static class Node<E> {
        private final E value;
        private Node next = null;
        private Node prev = null;

        public Node(E value) {
            this.value = value;
        }


        public E getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrevious() {
            return prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrevious(Node<E> prev) {
            this.prev = prev;
        }
    }
}
