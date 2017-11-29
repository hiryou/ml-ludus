package com.leetcode2;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/word-pattern/description/
 */
public class WordPattern {

    public static void main(String[] args) {
        WordPattern p = new WordPattern();

        String pa = "abba", s = "dog cat cat dog";
        System.out.println(p.wordPattern(pa, s));
    }


    public boolean wordPattern(String pattern, String str) {
        if (str.length() < pattern.length()) return false;
        if (pattern.isEmpty()) return false;

        String[] words = str.trim().split(" ");
        if (pattern.length() != words.length) return false;

        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        for (int i=0; i<pattern.length(); i++) {
            if (!canPut(map1, map2, pattern.charAt(i), words[i])) return false;
        }
        return true;
    }

    private boolean canPut(Map<Character, String> map1, Map<String, Character> map2, char c, String word) {
        boolean canPut = (!map1.containsKey(c) && !map2.containsKey(word))
                ||
                (map1.containsKey(c) && map1.get(c).equals(word)
                && map2.containsKey(word) && map2.get(word).equals(c));
        if (!canPut) return false;

        map1.put(c, word);
        map2.put(word, c);
        return true;
    }
}
