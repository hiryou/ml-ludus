package com.leetcode3;

/**
 * https://leetcode.com/problems/sentence-screen-fitting/description/
 * TODO Fine-tune to get all correct answers
 */
public class SentenceScreenFitting {

    class Pair {
        int count;
        int nextIdx;
        Pair(int count, int nextIdx) {
            this.count = count;
            this.nextIdx = nextIdx;
        }
    }

    // solution: fitting sentence to screen <=> trying to see how many line can we use to loop through this sentence
    public int wordsTyping(String[] ws, int rows, int cols) {
        String s = String.join(" ", ws).trim() + " "; // trick to end s with space making sure sentences are space separated
        int fromIdx = 0; // next idx in string s

        int count = 0;
        for (int row=0; row<rows; row++) {
            Pair p = fitOnRow(s, fromIdx, cols);
            count += p.count;
            fromIdx = p.nextIdx;
        }

        return count;
    }

    Pair fitOnRow(String s, int fromIdx, int cols) {
        int count = 0;
        if (s.length() - fromIdx <= cols+1) count++; // cols+1 because s has an ending space (trick)

        if (count==0) {
            int sidx = fromIdx + cols - 1;
            if (s.charAt(sidx+1) == ' ') return new Pair(0, (sidx+2) % s.length());

            while (s.charAt(sidx) != ' ' && sidx > 0) --sidx;
            if (sidx==0) return new Pair(0, 0);
            return new Pair(0, (sidx+1) % s.length());
        }

        // for the case of already having count = 1
        cols -= (s.length() - fromIdx);
        count += (cols+1) / s.length();
        int nextIdx = (cols+1) % s.length();
        // treat nextIdx
        if (s.charAt(nextIdx+1) == ' ') nextIdx = (nextIdx+2)%s.length();
        else {
            while (s.charAt(nextIdx) != ' ' && nextIdx > 0) --nextIdx;
            if (nextIdx != 0) nextIdx += 1;
        }

        return new Pair(count, nextIdx);
    }
}
