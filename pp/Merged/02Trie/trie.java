import java.util.*;

public class trie {
    
    // leetcode 208. https://leetcode.com/problems/implement-trie-prefix-tree/
    public static class Trie {

        private class Node {
            Node[] children;
            boolean isEnd;
            Node() {
                this.children = new Node[26];
                this.isEnd = false;
            }
        }

        private Node root = null;

        /** Initialize your data structure here. */
        public Trie() {
            root = new Node();    
        }
    
        /** Inserts a word into the trie. */
        public void insert(String word) {
            Node ptr = root;
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(ptr.children[ch - 'a'] == null) {
                    Node nn = new Node();
                    ptr.children[ch - 'a'] = nn;
                }
                ptr = ptr.children[ch - 'a'];
            }
            ptr.isEnd = true;
        }
    
        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            Node ptr = root;
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(ptr.children[ch - 'a'] == null) {
                    return false;
                }
                ptr = ptr.children[ch - 'a'];
            }
            return ptr.isEnd;
        }
    
        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String word) {
            Node ptr = root;
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(ptr.children[ch - 'a'] == null) {
                    return false;
                }
                ptr = ptr.children[ch - 'a'];
            }
            return true;
        }
    }
    
    // leetcode 211, https://leetcode.com/problems/design-add-and-search-words-data-structure/
    public static class WordDictionary {

        private class Node {
            Node[] children;
            boolean isEnd;

            Node() {
                this.children = new Node[26];
                this.isEnd = false;
            }
        }
        
        private Node root = null;

        public WordDictionary() {
            root = new Node();
        }
    
        /** Adds a word into the data structure. */
        public void addWord(String word) {
            Node temp = root;
            
            for(int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if(temp.children[ch - 'a'] == null) {
                    Node nn = new Node();
                    temp.children[ch - 'a'] = nn;
                }
                temp = temp.children[ch - 'a'];
            }
            temp.isEnd = true;
        }
    
        /**
         * Returns if the word is in the data structure. A word could contain the dot
         * character '.' to represent any one letter.
         */

        private boolean find(Node node, String word, int indx) {
            if(indx == word.length()) {
                return node.isEnd;
            }

            char ch = word.charAt(indx);
            if(ch == '.') {
                for(int i = 0; i < 26; i++) {
                    Node child = node.children[i];
                    if(child != null && find(child, word, indx + 1) == true) {
                        return true;
                    }
                }
            } else if(node.children[ch - 'a'] != null){
                return find(node.children[ch - 'a'], word, indx + 1);
            }
            return false;
        }

        public boolean search(String word) {
            return find(root, word, 0);
        }
    }

    // leetcode 212, https://leetcode.com/problems/word-search-ii/

    private class Node {
        Node[] children;
        boolean isEnd;
        int freq;

        Node() {
            this.children = new Node[26];
            this.isEnd = false;
            this.freq = 0;
        }
    }

    private void insert(String word, Node root) {
        for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if(root.children[ch - 'a'] == null) {
                root.children[ch - 'a'] = new Node();
            }
            root = root.children[ch - 'a'];
        }
        root.isEnd = true;
    }

    private int[] xdir = {-1, 0, 1, 0};
    private int[] ydir = {0, -1, 0, 1};

    private void travelAndAdd(char[][] board, int i, int j, boolean[][] vis,
         Node root, List<String> res, StringBuilder str) {
        char ch = board[i][j];
        if(root.children[ch - 'a'] == null)
            return;
        
        str.append(ch);
        vis[i][j] = true;
        root = root.children[ch - 'a'];
        if(root.isEnd == true) {
            res.add(str.toString());
            root.isEnd = false;
        }
        for(int d = 0; d < 4; d++) {
            int r = i + xdir[d];
            int c = j + ydir[d];

            if(r >= 0 && r < board.length && c >= 0 && c < board[0].length && vis[r][c] == false) {
                travelAndAdd(board, r, c, vis, root, res, str);
            }
        }
        str.deleteCharAt(str.length() - 1);
        vis[i][j] = false;
    }

    public List<String> findWords(char[][] board, String[] words) {
        // make a TRIE and add word in it
        Node root = new Node();
        for(String word : words) {
            insert(word, root);
        }
        // travel in cell and find similar words from dictionary
        boolean[][] vis = new boolean[board.length][board[0].length];
        List<String> res = new ArrayList<>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                StringBuilder str = new StringBuilder();
                travelAndAdd(board, i, j, vis, root, res, str);
            }
        }
        return res;
    }

    
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();   
    }
}