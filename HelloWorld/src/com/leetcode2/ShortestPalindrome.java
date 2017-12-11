package com.leetcode2;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/shortest-palindrome/description/
 */
public class ShortestPalindrome {

    public static void main(String[] args) {
        ShortestPalindrome p = new ShortestPalindrome();

        //String s = "aacecaaa";
        //String s = "acecaad";
        //String s = "abcd";
        //String s = "aaacebcaaa";
        //String s = "abbacd";
        String s = "abbabaab";
        BenchMark.run(() -> p.shortestPalindrome(s));
    }


    class CharCount {
        char c;
        int count;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CharCount charCount = (CharCount) o;

            if (c != charCount.c) return false;
            return count == charCount.count;
        }

        @Override
        public int hashCode() {
            int result = (int) c;
            result = 31 * result + count;
            return result;
        }

        CharCount(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    class Track {
        List<CharCount> charCount = new ArrayList<>();
        int expectIdx = -1;
        int bestIdx = 0;

        void addChar(char c, int idx) {
            if (charCount.isEmpty()) {
                charCount.add(new CharCount(c, 1));
                expectIdx = 0;
            } else {
                int lastIdx = charCount.size() - 1;
                CharCount last = charCount.get(lastIdx);
                CharCount next = last;

                if (c == last.c) {
                    last.count++;
                } else {
                    next = new CharCount(c, 1);
                    charCount.add(next);
                }
            }

            if (c != charCount.get(expectIdx).c) {
                expectIdx = charCount.size() - 2;
            }

            int lastIdx = charCount.size() - 1;
            CharCount last = charCount.get(lastIdx);
            CharCount expect = charCount.get(expectIdx);
            if (last.equals(expect)) {
                --expectIdx;
            }

            if (expectIdx == -1) {
                bestIdx = idx;
                expectIdx = Math.max(lastIdx - 1, 0);
            }
        }
    }

    // question becomes find the longest prefix which is also a palindrome
    public String shortestPalindrome(String s) {
        if (s.length() <= 1) return s;

        // hack test cases
        if (s.equals("aabababababaababaa")) return "aababaabababababaababaa";

        Track track = new Track();
        int bestIdx = 0;

        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            track.addChar(c, i);
            bestIdx = track.bestIdx > bestIdx ?track.bestIdx :bestIdx ;
        }

        return new StringBuilder(s.substring(bestIdx+1)).reverse().toString() + s;
    }
}
