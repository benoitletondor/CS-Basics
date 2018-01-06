import java.util.List;

public class PermutationGenerator {

    List<String> getPermutation(String input) {
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

    /*
    Less optimal solution that uses lot of string concatenation
    private static void permutation(String prefix, String str, List<String> permutations) {
        int n = str.length();
        if (n == 0) {
            permutations.add(prefix);
        }
        else {
            for (int i = 0; i < n; i++) {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n), permutations);
            }
        }
    }
    */
}
