package com.leetcode;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * https://leetcode.com/problems/ones-and-zeroes/description/
 * TODO Review. 2 heap solution passed 59/63 test cases! This needs DP
 */
public class OnesAndZeroes {

    public static void main(String[] args) {
        //String[] strs = {"10", "0001", "111001", "1", "0"}; int m = 5, n = 3;
        //String[] strs = {"10", "1", "0"}; int m = 1, n = 1;
        //String[] strs = {"1100", "100000", "011111"}; int m = 6, n = 6;
        //String[] strs = {"011111", "001", "001"}; int m = 4, n = 5;
        String[] strs = {"111", "1000", "1000", "1000"}; int m = 9, n = 3;

        OnesAndZeroes p = new OnesAndZeroes();
        System.out.println(p.findMaxForm(strs, m, n));
    }



    class BinaryNum implements Comparable<BinaryNum> {
        // for equals & hashCode
        final String s;
        final int index;

        // for comparable
        final int zeroCount;
        final int oneCount;
        final int minCountDigit;

        public BinaryNum(String s, int index, int minCountDigit) {
            this.s = s;
            this.index = index;

            int one = 0, zero = 0;
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i) == '0') ++zero;
                else ++one;
            }
            zeroCount = zero;
            oneCount = one;
            this.minCountDigit = minCountDigit;
        }

        @Override
        public int compareTo(BinaryNum o) {
            // if '0' has less count
            if (minCountDigit == 0) {
                if (this.zeroCount == o.zeroCount) return this.oneCount - o.oneCount;
                return this.zeroCount - o.zeroCount;
            }
            // else: if '1' has less count
            if (this.oneCount == o.oneCount) return this.zeroCount - o.zeroCount;
            return this.oneCount - o.oneCount;
        }

        /**
         * @param m count of '0'
         * @param n count of 'n'
         * @return
         */
        public boolean isAffordableBy(int m, int n) {
            return zeroCount <= m && oneCount <= n;
        }

        public int getRemain(int m, int n) {
            return m - zeroCount + n - oneCount;
        }
    }

    /**
     * 1st idea: using heap, sorting by the number of
     * @param m count of '0'
     * @param n count of '1'
     */
    public int findMaxForm(String[] strs, int m, int n) {
        if (strs.length==0) return 0;

        PriorityQueue<BinaryNum> queueZero = new PriorityQueue<>();
        PriorityQueue<BinaryNum> queueOne = new PriorityQueue<>();
        //for (String s: strs) {
        for (int i=0; i< strs.length; i++) {
            String s = strs[i];
            queueZero.add(new BinaryNum(s, i, 0));
            queueOne.add(new BinaryNum(s, i, 1));
        }

        int count = 0;
        Set<Integer> usedIndices = new HashSet<>();
        while (canContinue(queueZero, queueOne, m, n)) {
            BinaryNum cur = conservativeRemoveFrom(queueZero, queueOne, m, n) ;
            if (cur.isAffordableBy(m, n) && !usedIndices.contains(cur.index)) {
                ++count;
                m -= cur.zeroCount;
                n -= cur.oneCount;
                usedIndices.add(cur.index);
            }
        }
        return count;
    }

    // remove so that we still have more '1' and '0' left o spend later
    private BinaryNum conservativeRemoveFrom(
            PriorityQueue<BinaryNum> queueZero, PriorityQueue<BinaryNum> queueOne, int m, int n) {
        if (queueZero.isEmpty() && queueOne.isEmpty()) return null;

        int zeroScore = (!queueZero.isEmpty()) ?queueZero.peek().getRemain(m, n) :Integer.MIN_VALUE ;
        int oneScore = (!queueOne.isEmpty()) ?queueOne.peek().getRemain(m, n) :Integer.MIN_VALUE ;

        return zeroScore > oneScore ?queueZero.remove() :queueOne.remove() ;
    }

    private boolean canContinue(PriorityQueue<BinaryNum> queueZero, PriorityQueue<BinaryNum> queueOne, int m, int n) {
        if (queueZero.isEmpty() && queueOne.isEmpty()) return false;
        int zeroCount = !queueZero.isEmpty() ?queueZero.peek().zeroCount :0 ;
        int oneCount = !queueOne.isEmpty() ?queueOne.peek().oneCount :0 ;
        return m >= zeroCount || n >= oneCount;
    }

    private BinaryNum removeFrom(PriorityQueue<BinaryNum> queue) {
        if (queue.isEmpty()) return null;
        return queue.remove();
    }
}
