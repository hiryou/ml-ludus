package com.leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/description/
 * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/description/
 */
public class LongestSubstringAtMostKDistinctChars {

    public static void main(String[] args) {
        LongestSubstringAtMostKDistinctChars p = new LongestSubstringAtMostKDistinctChars();

        String s = "bacc"; int k = 2;
        //String s = "a"; int k = 2;
        System.out.println(p.lengthOfLongestSubstringKDistinct(s, k));
    }




    public int lengthOfLongestSubstringTwoDistinct(String s) {
        return lengthOfLongestSubstringKDistinct(s, 2);
    }



    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s.isEmpty()) return 0;
        if (k <= 0) return 0;

        //return formalSolutionUsing2HashMap(s, k);
        return solutionUsingArrayAsHashMap(s, k);
    }

    /**
     * Maintain a smart array acting as hashmap tracking from char c -> its count; Scanning st ring left to right
     */
    private int solutionUsingArrayAsHashMap(String s, int k) {
        CharToCount charToCount = new CharToCount();
        int max = 0; int distint = 0;
        int leftIdx = 0;

        for (int i=0; i<=s.length(); i++) {
            if (i < s.length()) {
                char c = s.charAt(i);
                distint += charToCount.get(c) == 0 ?1 :0 ;
                charToCount.incre(c);
            }

            if (distint > k || i==s.length()) {
                // this last char caused an exceed, so it should not be count
                int newMax = i - leftIdx;
                max = newMax > max ?newMax :max ;
                while (distint > k) {
                    char leftChar = s.charAt(leftIdx);
                    charToCount.decre(leftChar);
                    if (charToCount.get(leftChar) == 0)  --distint;
                    ++leftIdx;
                }
            }
        }

        return max;
    }

    private class CharToCount {
        int[] charToCount = new int[256]; // latest ASCII table has 256 characters
        public int get(char c) {
            return charToCount[(int)c];
        }
        public void incre(char c) {
            charToCount[(int)c]++;
        }
        public void decre(char c) {
            charToCount[(int)c]--;
        }
    }




    /**
     * Use a linked hashMap to map from last index (so far) of a char to the char
     * Use another hashMap to map from the char (so far) back to the last index
     * -> Linear time O(n)
     * @param s
     * @return
     */
    private int formalSolutionUsing2HashMap(String s, int k) {
        Map<Character, Integer> charToLastIdx = new HashMap<>();
        LinkedHashMap<Integer, Character> lastIdxToChar = new LinkedHashMap<>();
        int max = 0;

        int sIdx = 0;
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            // if need to remove
            if (!charToLastIdx.containsKey(c) && charToLastIdx.size() == k) {
                sIdx = removeLastIdxAndReturnNextIdx(charToLastIdx, lastIdxToChar);
            }

            // acknowledge this char
            if (!charToLastIdx.containsKey(c)) {
                charToLastIdx.put(c, i);
                lastIdxToChar.put(i, c);
            } else {
                // already contains it, we need to update the last idx
                int lastIdx = charToLastIdx.get(c);
                charToLastIdx.put(c, i);
                lastIdxToChar.remove(lastIdx);
                lastIdxToChar.put(i, c);
            }

            // update max if needed
            int newMax = i - sIdx + 1;
            max = newMax > max ?newMax :max ;
        }

        return max;
    }

    private int removeLastIdxAndReturnNextIdx(
            Map<Character, Integer> charToLastIdx, LinkedHashMap<Integer, Character> lastIdxToChar) {

        Map.Entry<Integer, Character> headEntry = lastIdxToChar.entrySet().iterator().next();
        int lastIdx = headEntry.getKey();
        char lastChar = headEntry.getValue();

        lastIdxToChar.remove(lastIdx);
        charToLastIdx.remove(lastChar);

        return lastIdx+1;
    }
}
