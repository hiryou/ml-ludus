package com.leetcode2;

import java.util.*;

/**
 * https://leetcode.com/problems/remove-duplicate-letters/description/
 *
 * Reasoning: For all unique chars in s, make a map from char -> linkedlist of its indices in s
 * Loop s left->right, for a char c, check if it can be removed. It can be removed if: We can find a smaller char c' :
 * c' < c such that smallest index of c' <= biggest index of all remaining chars that need to be present. What this means:
 * we can still build smaller lexicographical string that covers complete chars. So it's ok to remove the current char c
 */
public class RemoveDupLetters {

    public static void main(String[] args) {
        RemoveDupLetters p = new RemoveDupLetters();

        //String s = "bbcaac";
        //String s = "cbccdabc";
        //String s = "lskjdfghkjslehajdhfgslkj";
        //String s = "abacb";
        String s = "ccacbaba";
        BenchMark.run(() -> p.removeDuplicateLetters(s));
    }


    Map<Character, List<Integer>> charIdx = new HashMap<>();
    //int lastIdx = -1;

    public String removeDuplicateLetters(String s) {
        if (s.length() <= 1) return s;
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            addCharIdx(c, i);
        }

        StringBuilder b = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (!charIdx.containsKey(c)) continue;

            if (canRemove(c)) {
                charIdx.get(c).remove(0);
            } else {
                b.append(c);
                //lastIdx = i;
                charIdx.remove(c);
            }
        }

        return b.toString();
    }

    boolean canRemove(char c) {
        if (charIdx.get(c).size() == 1) return false; // cannot remove the last one!

        // find smaller char than c which also has enough characters coming after it
        int goodIdx = Integer.MAX_VALUE;
        for ( char x = 'a'; x < c; x++ ) {
            if (charIdx.containsKey(x)) {
                List<Integer> idxes = charIdx.get(x);
                int candIdx = idxes.get(0);
                goodIdx = candIdx < goodIdx ?candIdx :goodIdx ;
            }
        }
        if (goodIdx == Integer.MAX_VALUE) return false;

        for (Map.Entry<Character, List<Integer>> e: charIdx.entrySet()) {
            List<Integer> idxes = e.getValue();
            int xIdx = idxes.get(idxes.size()-1);
            if (goodIdx > xIdx) return false;
        }

        return true;
    }

    void addCharIdx(char c, int idx) {
        if (!charIdx.containsKey(c)) charIdx.put(c, new ArrayList<>());
        charIdx.get(c).add(idx);
    }
}
