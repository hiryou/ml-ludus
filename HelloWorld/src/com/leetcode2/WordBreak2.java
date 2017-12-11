package com.leetcode2;

import java.util.*;

/**
 * https://leetcode.com/problems/word-break-ii/description/
 */
public class WordBreak2 {

    public static void main(String[] args) {
        WordBreak2 p = new WordBreak2();

        String s = "catsanddog"; List<String> dict = Arrays.asList("cat", "cats", "and", "sand", "dog");
        //String s = "dogdog"; List<String> dict = Arrays.asList("dog");
        BenchMark.run(() -> p.wordBreak(s, dict));
    }


    public List<String> wordBreak(String s, List<String> dict) {
        return recursion(s, 0, dict);
    }

    Map<Integer, List<String>> idxToRes = new HashMap<>();

    List<String> recursion(String s, int idx, List<String> dict) {
        if (idxToRes.containsKey(idx)) return idxToRes.get(idx);

        // stop condition
        if (idx == s.length()) {
            List<String> res = new LinkedList<>();
            res.add("");
            idxToRes.put(idx, res);
            return res;
        }

        List<String> res = new LinkedList<>();
        for (String d: dict) if (canMatch(s, idx, d)) {
            List<String> posfixes = recursion(s, idx + d.length(), dict);
            for (String posf: posfixes) {
                res.add((d + " "  + posf).trim());
            }
        }
        idxToRes.put(idx, res);

        return res;
    }

    boolean canMatch(String s, int idx, String d) {
        if (s.length() - idx < d.length()) return false;
        int i = idx, j = 0;
        while (j < d.length() && s.charAt(i) == d.charAt(j)) {
            ++i;
            ++j;
        }
        return j == d.length();
    }
}
