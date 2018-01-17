package problem;

import java.util.ArrayList;
import java.util.List;

public class PermutationGenerator {

    public List<String> getPermutation(String input) {
        if( input == null ) {
            throw new NullPointerException("input==null");
        }

        if( input.isEmpty() ) {
            return new ArrayList<>(0);
        }

        final List<String> permutations = new ArrayList<>();
        fillPermutations(input.toCharArray(), 0, input.length() - 1, permutations);
        return permutations;
    }


    private static void fillPermutations(char[] chars, int startIndex, int endIndex, List<String> permutations) {
        if (startIndex == endIndex) {
            permutations.add(new String(chars));
        } else {
            for (int x = startIndex; x <= endIndex; x++) {
                swap(chars, startIndex, x); // Swap
                fillPermutations(chars, startIndex + 1, endIndex, permutations);
                swap(chars, startIndex, x); // Swap back
            }
        }
    }

    private static void swap(char[] a, int i, int x) {
        if( i == x ) {
            return;
        }

        char t = a[i];
        a[i] = a[x];
        a[x] = t;
    }
}
