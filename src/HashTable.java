import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implementation of an HashTable (or HashMap).
 * <br />
 * This implementation does not handle null values correctly and isn't optimal since the position in the data array
 * is not computed using {@code hash & (SIZE -1)} but a modulo since the size is not a power of 2
 *
 * @param <K> key type
 * @param <V> value type
 */
public class HashTable<K, V> implements Map<K, V> {

    private static float LOAD_FACTOR = 0.75f;
    private static int GROW_FACTOR = 2;
    private static int INITIAL_CAPACITY = 4;

// --------------------------------------->

    private LinkedList<Entry<K, V>>[] data;
    private int size = 0;

// --------------------------------------->

    public HashTable() {
        data = new LinkedList[INITIAL_CAPACITY];
    }

// --------------------------------------->

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean containsKey(Object key) {
        final int position = getPosition(key, data.length);
        return data[position] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        final int position = getPosition(key, data.length);
        final LinkedList<Entry<K, V>> node = data[position];

        for(Entry<K, V> entry: node) {
            if( entry.getKey().equals(key) ) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        if( ++size > data.length * ( 1 / LOAD_FACTOR ) ) {
            resize(GROW_FACTOR * data.length);
        }

        final int position = getPosition(key, data.length);
        return putEntry(data, (Entry<K, V>) new MapEntry(key, value), position);
    }

    @Override
    public V remove(Object key) {
        final int position = getPosition(key, data.length);

        LinkedList<Entry<K, V>> node = data[position];
        if( node == null ) {
            return null;
        }

        Entry<K, V> foundEntry = null;
        for(Entry<K, V> entry : node) {
            if( entry.getKey().equals(key) ) {
                foundEntry = entry;
                break;
            }
        }

        if( foundEntry != null ) {
            node.remove(foundEntry);
            size--;
            return foundEntry.getValue();
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }

        size += m.size();
    }

    @Override
    public void clear() {
        data = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        final Set<K> keys = new HashSet<>(size);

        for(int i = 0; i<data.length; i++) {
            LinkedList<Entry<K, V>> node = data[i];
            if( node != null ) {
                for(Entry<K, V> entry : node) {
                    keys.add(entry.getKey());
                }
            }
        }

        return keys;
    }

    @Override
    public Collection<V> values() {
        final List<V> values = new ArrayList<>(size);

        for(int i = 0; i<data.length; i++) {
            LinkedList<Entry<K, V>> node = data[i];
            if( node != null ) {
                for(Entry<K, V> entry : node) {
                    values.add(entry.getValue());
                }
            }
        }

        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        final Set<Entry<K, V>> entries = new HashSet<>(size);

        for(int i = 0; i<data.length; i++) {
            LinkedList<Entry<K, V>> node = data[i];
            if( node != null ) {
                for(Entry<K, V> entry : node) {
                    entries.add(entry);
                }
            }
        }

        return entries;
    }

// --------------------------------------->

    private int getPosition(Object key, int dataLength) {
        return Math.abs(key.hashCode()) % dataLength;
    }

    private V putEntry(LinkedList<Entry<K, V>>[] entries, Entry<K, V> entry, int position) {
        LinkedList<Entry<K, V>> node = entries[position];
        if( node == null ) {
            node = new LinkedList<>();
            entries[position] = node;
        }

        V existingValue = null;
        for(Entry<K, V> existingEntry : node) {
            if( existingEntry.getKey().equals(entry.getKey()) ) {
                existingValue = existingEntry.getValue();
                existingEntry.setValue(entry.getValue());
            }
        }

        if( existingValue == null ) {
            node.add(entry);
        }

        return existingValue;
    }

    private void resize(int newSize) {
        LinkedList<Entry<K, V>>[] newData = new LinkedList[newSize];

        for(Entry<K, V> entry : entrySet()) {
            final int position = getPosition(entry.getKey(), newSize);
            putEntry(newData, entry, position);
        }

        data = newData;
    }

    private static class MapEntry<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        private MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            final V currentValue = this.value;
            this.value = value;
            return currentValue;
        }
    }
}
