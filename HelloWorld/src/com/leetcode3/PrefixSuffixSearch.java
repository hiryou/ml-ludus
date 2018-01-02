package com.leetcode3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/prefix-and-suffix-search/description/
 */
public class PrefixSuffixSearch { static class WordFilter {

    public static void main(String[] args) {
        WordFilter p = new WordFilter(new String[] {"apple"});
        p.f("a", "e");
        p.f("b", "");
    }



    final class Trie {
        Trie[] child = new Trie[26];
        List<Integer> ws = new LinkedList<>();  // higher weight is at the front of the list

        void add(char[] cs, int idx, int incOrDec, int weight) {
            this.ws.add(weight);

            // stop condition
            if ((incOrDec<0 && idx==-1) || (incOrDec>0 && idx==cs.length)) return;

            char c = cs[idx];
            if (child[c - 'a'] == null) child[c - 'a'] = new Trie();
            child[c - 'a'].add(cs, idx + incOrDec, incOrDec, weight);
        }

        List<Integer> get(char[] cs, int idx, int incOrDec) {
            // stop condition
            if ((incOrDec<0 && idx==-1) || (incOrDec>0 && idx==cs.length)) return this.ws;

            char c = cs[idx];
            if (child[c - 'a'] == null) return Arrays.asList();
            return child[c - 'a'].get(cs, idx + incOrDec, incOrDec);
        }
    }

    final String[] words;
    final Trie prefRoot = new Trie();
    final Trie suffRoot = new Trie();

    public WordFilter(String[] words) {
        this.words = words;
        for (int i=words.length-1; i>=0; i--) {
            char[] cs = words[i].toCharArray();
            prefRoot.add(cs, 0, +1, i);
            suffRoot.add(cs, cs.length-1, -1, i);
        }
    }

    public int f(String prefix, String suffix) {
        char[] cspref = prefix.toCharArray();
        List<Integer> pref = prefRoot.get(cspref, 0, +1);

        char[] cssuff = suffix.toCharArray();
        List<Integer> suff = suffRoot.get(cssuff, cssuff.length-1, -1);

        if (pref.isEmpty() || suff.isEmpty()) return -1;

        int i = 0; int j = 0;
        if (pref.get(i) == suff.get(j)) return pref.get(i);

        while (pref.get(i) != suff.get(j) && i<pref.size() && j<suff.size()) {
            if (pref.get(i) > suff.get(j)) ++i;
            else if (pref.get(i) < suff.get(j)) ++j;

            if (pref.get(i) == suff.get(j)) return pref.get(i);
        }
        return -1;
    }
}}
