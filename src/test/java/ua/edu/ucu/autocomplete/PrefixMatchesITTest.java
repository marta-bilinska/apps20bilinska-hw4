
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test( expected = IllegalArgumentException.class)
    public void testWordsWithPrefixException() {
        String pref = "a";
        int k = 3;
        Iterable<String> result = pm.wordsWithPrefix(pref, k);

    }

    @Test
    public void testDeleteAndContains() {
        PrefixMatches p = new PrefixMatches(new RWayTrie());
        p.load("abc", "abce", "abcd", "abcde", "abcdef");
        assertTrue(p.contains("abc"));
        assertTrue(p.contains("abce"));
        assertTrue(p.contains("abcd"));
        assertTrue(p.contains("abcde"));
        assertTrue(p.contains("abcdef"));
        p.delete("abc");
        p.delete("abce");
        p.delete("abcd");
        p.delete("abcde");
        p.delete("abcdef");
        assertFalse(p.contains("abc"));
        assertFalse(p.contains("abce"));
        assertFalse(p.contains("abcd"));
        assertFalse(p.contains("abcde"));
        assertFalse(p.contains("abcdef"));
    }

    @Test
    public void test() {
        PrefixMatches p = new PrefixMatches(new RWayTrie());
        p.load("abc", "abce", "abcd", "abcde", "abcdef");
        assertTrue(p.contains("abc"));
        assertTrue(p.contains("abce"));
        assertTrue(p.contains("abcd"));
    }
}
