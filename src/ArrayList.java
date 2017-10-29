import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of ArrayList (some methods are missing so it's not implementing {@link java.util.List})
 *
 * @param <E> type of object
 */
public class ArrayList<E> {

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

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

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

    public Object[] toArray() {
        Object[] array = new Object[size];

        for(int i = 0; i<size; i++) {
            array[i] = elements[i];
        }

        return array;
    }

    public boolean add(E e) {
        ensureSize(size+1);
        elements[size++] = e;
        return true;
    }

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

    public boolean containsAll(Collection<?> c) {
        for(Object o : c) {
            if( !contains(o) ) {
                return false;
            }
        }

        return true;
    }

    public boolean addAll(Collection<? extends E> c) {
        final Object[] objectsToAdd = c.toArray();

        ensureSize(size + objectsToAdd.length);
        for(int i=0; i<objectsToAdd.length; i++) {
            elements[size + i] = objectsToAdd[i];
        }

        size += objectsToAdd.length;

        return objectsToAdd.length > 0;
    }

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

    public boolean removeAll(Collection<?> c) {
        final int currentSize = size;

        for(Object o : c) {
            remove(o);
        }

        return size < currentSize;
    }

    public void clear() {
        elements = new Object[elements.length];
        size = 0;
    }

    public E get(int index) {
        return (E) elements[index];
    }

    public E set(int index, E element) {
        final E currentElement = (E) elements[index];
        elements[index] = element;
        return currentElement;
    }

    public void add(int index, E element) {
        ensureSize(size +1 );

        for(int i = size; i>index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

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
