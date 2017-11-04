package search;

public class BinarySearch<T extends Comparable<T>> implements ArraySearch<T> {

    @Override
    public int getIndex(T value, T[] array) {
        if( array == null ) {
            throw new NullPointerException("array==null");
        }

        if( array.length == 0 ) {
            return -1;
        }

        if( array.length == 1 ) {
            return array[0].compareTo(value) == 0 ? 0 : -1;
        }

        int start = 0;
        int end = array.length - 1;
        int mid;

        while(start <= end) {
            mid = (start + end) / 2;

            int compare = array[mid].compareTo(value);

            System.out.println("Searching from "+start+" to "+end+". mid = "+mid+", compare = "+compare);
            if( compare < 0 ) {
                start = mid + 1;
            } else if( compare > 0 ) {
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
