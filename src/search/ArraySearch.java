package search;

public interface ArraySearch<T extends Comparable<T>> {
    int getIndex(T value, T[] array);
}
