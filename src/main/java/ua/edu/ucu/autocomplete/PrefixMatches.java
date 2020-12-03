package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 * @author andrii
 */
public class PrefixMatches {

    private final Trie trie;

    public PrefixMatches() {
        trie = new RWayTrie();
    }

    public PrefixMatches(Trie newTrie) {
        trie = newTrie;
    }

    public int load(String... strings) {
        for (String string : strings) {
            StringBuilder newStringBuilder = new StringBuilder();
            int len = string.length();
            for (int i = 0; i < len; i++) {
                char currentChar = string.charAt(i);
                if (string.charAt(i) != ' ') {
                    newStringBuilder.append(currentChar);
                } else {
                    int wordLength = newStringBuilder.length();
                    if (wordLength >= 2) {
                        trie.add(new Tuple(newStringBuilder.toString(),
                                wordLength));
                    }
                    newStringBuilder = new StringBuilder();
                }
            }
            trie.add(new Tuple(newStringBuilder.toString(),
                    newStringBuilder.toString().length()));
        }
        return 0;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        checkPrefix(pref);
        return trie.wordsWithPrefix(pref);
    }

    public void checkPrefix(String prefix) {
        if (prefix.length() < 2) {
            throw new IllegalArgumentException();
        }
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        checkPrefix(pref);
        int startIndex = pref.length();
        if (startIndex == 2) {
            startIndex++;
        }
        int shift = startIndex + k - 1;
        Iterable<String> allWordsWithPrefixes = trie.wordsWithPrefix(pref);
        ArrayList<String> result = new ArrayList<>();
        for (String item : allWordsWithPrefixes) {
            if (item.length() <= shift && item.length() >= startIndex) {
                result.add(item);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
