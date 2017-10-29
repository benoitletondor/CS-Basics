import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implementation of ArrayList
 *
 * @param <E> type of object
 */
public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;

// --------------------------------------->

    private Object[] elements;
    private int size;

// --------------------------------------->

    public ArrayList(int initialCapacity) {
        if( initialCapacity < 0 ) {
            throw new IllegalArgumentException("initialCapacity should be >= 0");
        }

        elements = new Object[initialCapacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

// --------------------------------------->

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if( index >= size ) {
                    return null;
                }

                final E element = (E) elements[index];
                index++;
                return element;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        for(int i = 0; i<size; i++) {
            array[i] = elements[i];
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) toArray();
    }

    @Override
    public boolean add(E e) {
        ensureSize(size+1);
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if( o == null ) {
            for(int i = 0; i<size; i++) {
                if( elements[i] == null ) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for(int i = 0; i<size; i++) {
                if( o.equals(elements[i]) ) {
                    remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c) {
            if( !contains(o) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        final Object[] objectsToAdd = c.toArray();

        ensureSize(size + objectsToAdd.length);
        for(int i=0; i<objectsToAdd.length; i++) {
            elements[size + i] = objectsToAdd[i];
        }

        size += objectsToAdd.length;

        return objectsToAdd.length > 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        final Object[] objectsToAdd = c.toArray();
        ensureSize(size + objectsToAdd.length );

        for(int i = index; i<size; i++) {
            elements[i+objectsToAdd.length] = elements[i];
        }

        for(int i = 0; i<=objectsToAdd.length; i++) {
            elements[index+i] = objectsToAdd[i];
        }

        size+=objectsToAdd.length;

        return objectsToAdd.length > 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final int currentSize = size;

        for(Object o : c) {
            remove(o);
        }

        return size < currentSize;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false; // TODO
    }

    @Override
    public void clear() {
        elements = new Object[elements.length];
        size = 0;
    }

    @Override
    public E get(int index) {
        return (E) elements[index];
    }

    @Override
    public E set(int index, E element) {
        final E currentElement = (E) elements[index];
        elements[index] = element;
        return currentElement;
    }

    @Override
    public void add(int index, E element) {
        ensureSize(size +1 );

        for(int i = size; i>index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        final E element = (E) elements[index];

        for(int i = index; i< size-1; i++) {
            elements[i] = elements[i+1];
        }

        // Working but not optimal, O(n) space complexity
        /*Object[] newElements = new Object[elements.length];
        int newElementsIndex = 0;

        for(int i = 0; i<size; i++) {
            if( i == index ) {
                continue;
            }

            newElements[newElementsIndex] = elements[i];
            newElementsIndex++;
        }

        elements = newElements;*/

        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        if( o == null ) {
            for(int i=0; i<size; i++) {
                if( elements[i] == null ) {
                    return i;
                }
            }
        } else {
            for(int i=0; i<size; i++) {
                if( o.equals(elements[i]) ) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if( o == null ) {
            for(int i=size-1; i>= 0; i--) {
                if( elements[i] == null ) {
                    return i;
                }
            }
        } else {
            for(int i=size-1; i>= 0; i--) {
                if( o.equals(elements[i]) ) {
                    return i;
                }
            }
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
    public ArrayList<E> subList(int fromIndex, int toIndex) {
        final ArrayList<E> subList = new ArrayList<>(toIndex - fromIndex);

        for(int i = fromIndex; i<=fromIndex; i++) {
            subList.add(get(i));
        }

        return subList;
    }

// --------------------------------------->

    private void ensureSize(int minSize) {
        int oldCapacity = elements.length;
        if( oldCapacity >= minSize ) {
            return;
        }

        int newSize = oldCapacity * GROW_FACTOR;
        if( newSize < minSize ) {
            newSize = minSize;
        }

        Object[] newElements = new Object[newSize];
        for(int i=0; i<size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }
}
