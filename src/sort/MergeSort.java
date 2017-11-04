package sort;

public class MergeSort<T extends Comparable<T>> implements ArraySort<T> {

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

        mergesort(input, new Comparable[input.length], 0, input.length -1);
        System.out.println("Done");
    }

    private void mergesort(T[] input, Comparable[] buffer, int start, int end) {
        if( start < end ){
            System.out.println("mergesort "+start+ " -> "+end);

            int middle = (start + end) / 2;
            mergesort(input, buffer, start, middle);
            mergesort(input, buffer, middle + 1, end);

            System.out.println("Merging : "+start+" -> "+middle+" -> "+end);
            for(int i = start; i<=end; i++) {
                System.out.println(i + " : "+input[i]);
            }

            merge(input, buffer, start, middle, end);

            System.out.println("Merge done");
            for(int i = start; i<=end; i++) {
                System.out.println(i + " : "+input[i]);
            }
        }
    }

    private void merge(T[] input, Comparable[] buffer, int start, int middle, int end) {
        for(int i = start; i<=end; i++) {
            buffer[i] = input[i];
        }

        int bufferLeft = start;
        int bufferRight = middle + 1;
        int current = start;

        while (bufferLeft <= middle && bufferRight <= end) {
            if( buffer[bufferLeft].compareTo(buffer[bufferRight]) <= 0 ) {
                input[current] = (T) buffer[bufferLeft];
                bufferLeft ++;
            } else {
                input[current] = (T) buffer[bufferRight];
                bufferRight++;
            }

            current++;
        }

        int remaining = middle - bufferLeft;
        for(int i = 0; i<= remaining; i++) {
            input[current + i] = (T) buffer[bufferLeft + i];
        }
    }

}
