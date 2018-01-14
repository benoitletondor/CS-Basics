package problem;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<V> {
    private int maxSize;

    private DoublyLinkedListNode<V> first;
    private DoublyLinkedListNode<V> last;
    private Map<String, DoublyLinkedListNode<V>> values = new HashMap<>();

// --------------------------------------->

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
    }

// --------------------------------------->

    public V get(String key) {
        if (key == null)  {
            throw new NullPointerException("key == null");
        }

        DoublyLinkedListNode<V> item = values.get(key);
        if( item == null ){
            return null;
        }

        if( item != first ) {
            removeNode(item);
            putAsFirst(item);
        }

        return item.value;
    }

    public V remove(String key) {
        if (key == null)  {
            throw new NullPointerException("key == null");
        }

        DoublyLinkedListNode<V> node = values.get(key);
        if( node == null ) {
            return null;
        }

        removeNode(node);
        values.remove(key);

        return node.value;
    }

    public void put(String key, V value) {
        if( key == null || value == null ) {
            throw new NullPointerException("key == null || value == null");
        }

        remove(key);

        if( values.size() >= maxSize && last != null ) {
            remove(last.key);
        }

        DoublyLinkedListNode<V> node = new DoublyLinkedListNode<>(key, value);
        putAsFirst(node);
        values.put(key, node);
    }

// --------------------------------------->

    private void removeNode(DoublyLinkedListNode<V> node) {
        if( node == null ) {
            return;
        }

        if( node.previous != null ) {
            node.previous.next = node.next;
        }

        if( node.next != null ) {
            node.next.previous = node.previous;
        }

        if( node == first ) {
            first = node.next;
        }

        if( node == last ) {
            last = node.previous;
        }
    }

    private void putAsFirst(DoublyLinkedListNode<V> node) {
        if( first == null ) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.previous = node;
            first = node;
        }
    }

// --------------------------------------->

    private static class DoublyLinkedListNode<V> {
        private DoublyLinkedListNode previous;
        private DoublyLinkedListNode next;
        private V value;
        private String key;

        private DoublyLinkedListNode(String key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
