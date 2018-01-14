package problem;

public class StringPatternMatching {

    /**
     * Does the given input match the pattern
     * @param input a string
     * @param pattern a pattern, that can contains '?' for 1 char or '*' for 1 to many chars
     * @return true if the input matches the pattern, false otherwise
     */
    public boolean matches(String input, String pattern) {
        char[] inputChars = input.toCharArray();
        char[] patternChars = pattern.toCharArray();
        int inputIndex = 0;
        int patternIndex = 0;

        int tempInputIndex = 0;
        int tempPatternIndex = -1;

        while(inputIndex < inputChars.length) {
            if( patternIndex < patternChars.length && (inputChars[inputIndex] == patternChars[patternIndex] || patternChars[patternIndex] == '?') ) {
                inputIndex++;
                patternIndex++;
            } else if( patternIndex < patternChars.length && patternChars[patternIndex] == '*' ) {
                tempPatternIndex = patternIndex;
                tempInputIndex = inputIndex;
                patternIndex++;
            } else if( tempPatternIndex >= 0 ) {
                tempInputIndex++;
                inputIndex = tempInputIndex;
                patternIndex = tempPatternIndex;
            } else {
                return false;
            }
        }

        while (patternIndex < patternChars.length && patternChars[patternIndex] == '*') {
            patternIndex++;
        }

        return patternIndex == patternChars.length && inputIndex == inputChars.length;
    }
}
