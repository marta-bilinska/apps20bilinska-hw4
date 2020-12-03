package ua.edu.ucu.tries;

import ua.edu.ucu.queue.Queue;

import java.util.ArrayList;
import java.util.Arrays;

public class RWayTrie implements Trie {
    private static final int BRANCH_NUM = 26;
    private TrieNode root = new TrieNode();
    private int size;

    private static class TrieNode {
        private TrieNode parent;
        private Object value;
        private final TrieNode[] next = new TrieNode[BRANCH_NUM];

        public String toString() {
            if (value != null) {
                return value.toString();
            }
            return "";
        }
    }

    @Override
    public void add(Tuple t) {
        if (contains(t.term)) {
            return;
        }
        TrieNode current = root;
        for (int i = 0; i < t.weight; i++) {
            int index = t.term.charAt(i) - 'a';
            if (!checkCharacter(index)) {
                return;
            }
            if (current.next[index] == null) {
                current.next[index] = new TrieNode();
            }
            TrieNode newCurrent = current.next[index];
            newCurrent.parent = current;
            current = newCurrent;

        }
        current.value = t.weight;
        size += 1;
    }

    @Override
    public boolean contains(String word) {
        TrieNode current = get(word);
        return current != null && current.value != null;
    }

    @Override
    public boolean delete(String string) {
        if (!contains(string)) {
            return false;
        }
        root = delete(root, string, 0);
        size -= 1;
        return true;
    }

    private TrieNode delete(TrieNode trieNode, String string, int depth) {
        if (trieNode == null) {
            return null;
        }
        if (depth == string.length()) {
            trieNode.value = null;
        } else {
            int c = string.charAt(depth) - 'a';
            trieNode.next[c] = delete(trieNode.next[c], string, depth + 1);
        }
        if (trieNode.value != null) {
            return trieNode;
        }
        for (char c = 'a'; c <= 'z'; c++)
            if (trieNode.next[c - 'a'] != null) {
                return trieNode;
            }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue queue = new Queue();
        findWords(get(root, s, 0), s, queue);
        Object[] x = queue.toArray();
        int len = x.length;
        String[] newArray = new String[len];
        for (int i = 0; i < len; i++) {
            if (x[i] == null) {
                newArray[i] = null;
            } else {
                newArray[i] = x[i].toString();
            }
        }
        return new ArrayList(Arrays.asList(newArray));
    }


    private void findWords(TrieNode trieNode, String pref, Queue q) {
        if (trieNode == null) {
            return;
        }
        if (trieNode.value != null) {
            q.enqueue(pref);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            findWords(trieNode.next[c - 'a'], pref + c, q);
        }
    }

    public TrieNode get(String key) {
        return get(root, key, 0);
    }

    private TrieNode get(TrieNode trieNode, String string, int depth) {
        if (trieNode == null) {
            return null;
        }
        if (depth == string.length()) {
            return trieNode;
        }
        int index = string.charAt(depth) - 'a';
        if (!checkCharacter(index)) {
            return null;
        }
        return get(trieNode.next[index], string, depth + 1);
    }

    public boolean checkCharacter(int c) {
        return c < BRANCH_NUM && c >= 0;
    }

    @Override
    public int size() {
        return size;
    }

}
