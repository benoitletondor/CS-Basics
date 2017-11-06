package sort;

public class QuickSort<T extends Comparable<T>> implements ArraySort<T> {

    @Override
    public void sort(T[] input) {
        if( input == null ) {
            throw new NullPointerException("array==null");
        }

        if( input.length < 2 ) { // Already sorted since array is empty or 1 element
            return;
        }

        System.out.println("Starting");
        for(int i = 0; i<input.length; i++) {
            System.out.println(i + " : "+input[i]);
        }

        quicksort(input, 0, input.length - 1);

        System.out.println("Done");
        for(int i = 0; i<input.length; i++) {
            System.out.println(i + " : "+input[i]);
        }
    }

    private void quicksort(T[] input, int start, int end) {
        System.out.println("quicksort starting "+start+ " -> "+end);

        int index = partition(input, start, end);

        System.out.println("quicksort end "+start+ " -> "+index+" -> "+end);
        for(int i = start; i<=end; i++) {
            System.out.println(i + " : "+input[i]);
        }

        if( start < index - 1 ) {
            quicksort(input, start, index - 1);
        }

        if( index < end ) {
            quicksort(input, index, end);
        }
    }

    private int partition(T[] input, int start, int end) {
        T pivot = input[(start + end) / 2];

        System.out.println("Partitionning starting "+start+ " -> "+(start+end) / 2+" -> "+end);
        for(int i = start; i<=end; i++) {
            System.out.println(i + " : "+input[i]);
        }

        while (start <= end) {
            while (input[start].compareTo(pivot) < 0) {
                start++;
            }

            while (input[end].compareTo(pivot) > 0) {
                end--;
            }

            if( start <= end ) {
                System.out.println("Partitionning swap : "+start+" <-> "+end);
                T elemStart = input[start];
                input[start] = input[end];
                input[end] = elemStart;
                start++;
                end--;
            }
        }

        System.out.println("Partitionning end : "+start);

        return start;
    }
}
