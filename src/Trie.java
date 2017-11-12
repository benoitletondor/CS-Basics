import java.util.HashMap;
import java.util.Map;

public class Trie {
    private TrieNode root = new TrieNode();

    public void insert(String word) {
        if( word == null ){
            throw new NullPointerException("word==null");
        }

        char[] chars = word.toCharArray();
        TrieNode node = root;

        for(char c : chars) {
            TrieNode charNode = node.children.get(c);
            if( charNode == null ) {
                charNode = new TrieNode();
                node.children.put(c, charNode);
            }

            node = charNode;
        }

        if( !node.containsWordEndChild() ) {
            node.addWordEndChild();
        }
    }

    public boolean exists(String word) {
        if( word == null ){
            throw new NullPointerException("word==null");
        }

        char[] chars = word.toCharArray();
        TrieNode node = root;

        for(char c : chars) {
            TrieNode charNode = node.children.get(c);

            if( charNode == null ) {
                return false;
            }

            node = charNode;
        }

        return node.containsWordEndChild();
    }

    public boolean startsWith(String prefix) {
        if( prefix == null ){
            throw new NullPointerException("prefix==null");
        }

        char[] chars = prefix.toCharArray();
        TrieNode node = root;

        for(char c : chars) {
            TrieNode charNode = node.children.get(c);

            if( charNode == null ) {
                return false;
            }

            node = charNode;
        }

        return true;
    }

    private static class TrieNode {
        private Map<Character, TrieNode> children = new HashMap<>();

        private boolean containsWordEndChild() {
            return children.containsKey(END_CHAR);
        }

        private void addWordEndChild() {
            children.put(END_CHAR, WORD_END);
        }
    }

    private static final Character END_CHAR = '*';
    private static final TrieNode WORD_END = new TrieNode();
}