import java.io.IOException;

public class StringBuilder implements Appendable, CharSequence {

    private static final int GROW_FACTOR = 2;
    private static final int DEFAULT_SIZE = 50;

// --------------------------------------->

    private int size;
    private char[] buffer;

// --------------------------------------->

    public StringBuilder() {
        size = 0;
        buffer = new char[DEFAULT_SIZE];
    }

    public StringBuilder(int initialSize) {
        size = 0;
        buffer = new char[initialSize];
    }

    public StringBuilder(CharSequence initialValue) {
        this(initialValue.length());

        try {
            append(initialValue);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

// --------------------------------------->

    @Override
    public Appendable append(CharSequence csq) throws IOException {
        if( csq == null || csq.length() == 0 ) {
            return this;
        }

        ensureSize(size + csq.length());
        for(int i = 0; i<csq.length(); i++) {
            buffer[size+i] = csq.charAt(i);
        }

        size += csq.length();
        return this;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        if( start < 0 ) {
            throw new IllegalArgumentException("start should be >= 0");
        }

        if( end >= size ) {
            throw new IllegalArgumentException("end should be < size");
        }

        if( end - start != csq.length() ) {
            throw new IllegalArgumentException("char sequence lenght should be equal to (end - start)");
        }

        ensureSize(size + (end - start));

        for(int i = start; i<=end; i++) {
            buffer[i+size] = buffer[i];
        }

        for (int i = 0; i < csq.length(); i++) {
            buffer[start+i] = csq.charAt(i);
        }

        size += (end - start);

        return this;
    }

    @Override
    public Appendable append(char c) throws IOException {
        ensureSize(size + 1);
        buffer[size++] = c;
        return this;
    }

    @Override
    public int length() {
        return size;
    }

    @Override
    public char charAt(int index) {
        return buffer[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if( start < 0 ) {
            throw new IllegalArgumentException("start should be >= 0");
        }

        if( end >= size ) {
            throw new IllegalArgumentException("end should be < size");
        }

        final StringBuilder result = new StringBuilder(end - start);
        for(int i = start; i<=end; i++) {
            try {
                result.append(buffer[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

// --------------------------------------->

    private void ensureSize(int minSize) {
        if( buffer.length >= minSize ) {
            return;
        }

        int newSize = buffer.length * GROW_FACTOR;
        if( newSize < minSize ) {
            newSize = minSize;
        }

        final char[] newBuffer = new char[newSize];
        for(int i = 0; i<buffer.length; i++) {
            newBuffer[i] = buffer[i];
        }

        buffer = newBuffer;
    }
}
