package com.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/minimum-window-substring/description/
 */
public class MinimumWindowSubstring {

    public static void main(String[] args) {
        MinimumWindowSubstring program = new MinimumWindowSubstring();

        //String s = "ADOBECODEBANC";
        //String t = "ABC";

        String s = "cabwefgewcwaefgcf";
        String t = "cae";

        //String s ="a";
        //String t = "a";

        String window = program.minWindow(s, t);
        System.out.println(window);
    }



    class TrackingCount {
        int completeCount = 0;
        int count = 0;

        public boolean hasEnough() {
            return count >= completeCount;
        }

        public boolean hasMoreThanEnough() {
            return count > completeCount;
        }

        public boolean hasLessThanEnough() {
            return count < completeCount;
        }
    }

    class TrackingWindow {
        Map<Character, TrackingCount> charToCount = new HashMap<>();
        TreeMap<Integer, Character> indexToChar = new TreeMap<>();

        Set<Character> completeChars = new HashSet<>();
        int startIdx, lastIdx;

        public TrackingWindow(int minWindowLength) {
            startIdx = 0;
            lastIdx = minWindowLength - 1;
        }

        public void addTrackingCount(char c) {
            if (!charToCount.containsKey(c)) {
                charToCount.put(c, new TrackingCount());
            }
            charToCount.get(c).completeCount++;
        }

        public boolean containsChar(char c) {
            return charToCount.containsKey(c);
        }

        public void addCountAndOptimize(int idx, char c) {
            if (!containsChar(c)) throw new RuntimeException("should not happen");

            indexToChar.put(idx, c);
            charToCount.get(c).count++;
            if (charToCount.get(c).hasMoreThanEnough()) {
                tryOptimizeFromLeft();
            }

            if (charToCount.get(c).hasEnough()) {
                completeChars.add(c);
            }
            if (completeChars.size() == charToCount.size()) {
                updateMinWindowIfNeeded();
            }
        }

        private void updateMinWindowIfNeeded() {
            int newStartIdx = indexToChar.firstKey();
            int newEndIdx = indexToChar.lastKey();

            if (newEndIdx - newStartIdx < lastIdx - startIdx) {
                startIdx = newStartIdx;
                lastIdx = newEndIdx;
            }
        }

        private void tryOptimizeFromLeft() {
            boolean canContinue = true;
            while (canContinue) {
                canContinue = false;

                int leftIdx = indexToChar.firstEntry().getKey();
                char leftC = indexToChar.firstEntry().getValue();
                if (charToCount.get(leftC).hasMoreThanEnough()) {
                    canContinue = true;
                    indexToChar.remove(leftIdx);
                    charToCount.get(leftC).count--;
                }
            }
        }

        public String getWindow(String s) {
            if (completeChars.size() < charToCount.size()) {
                return "";
            }

            return s.substring(startIdx, lastIdx + 1);
        }
    }

    public String minWindow(String s, String t) {
        TrackingWindow tw = new TrackingWindow(s.length());

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            tw.addTrackingCount(c);
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (tw.containsChar(c)) {
                tw.addCountAndOptimize(i, c);
            }
        }

        return tw.getWindow(s);
    }
}
