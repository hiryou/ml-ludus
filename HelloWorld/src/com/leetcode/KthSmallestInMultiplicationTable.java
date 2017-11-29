package com.leetcode;

/**
 * https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/description/
 */
public class KthSmallestInMultiplicationTable {

    public static void main(String[] args) {
        KthSmallestInMultiplicationTable p = new KthSmallestInMultiplicationTable();
        //int m = 3, n = 3, k = 5;
        //int m = 10_000, n = 10_000, k = 9_563;
        //int m = 3, n = 1, k = 2;
        //int m = 45, n = 12, k = 471;
        int m = 27_354, n = 29_476, k = 17_409;
        System.out.println(p.findKthNumber(m, n, k));
    }


    class EarlyCount {
        int count = 1;
    }

    /**
     * Algorithm: Binary search from 1 -> m*n with a smart way to quickly calculate how many multiplications are <=
     * a given result x
     */
    public int findKthNumber(int m, int n, int k) {
        // easy cases
        if (k==1) return 1;
        if (k==m*n) return m*n;
        if (m==1 || n==1) return k;

        if (m > n) return findKthNumber(n, m, k);

        int low = 1; int high = m*n;
        EarlyCount earlyResult = new EarlyCount();
        while (low < high) {
            int mid = low + (high - low)/2;
            // calculate the number of pairs whose multiplication >= mid
            int lessOrEqualCount = calculateLessOrEqualPairCount(m, n, mid, earlyResult) ;

            if (lessOrEqualCount == k) return earlyResult.count;
            else if (lessOrEqualCount > k) high = mid;
            else low = mid + 1;
        }

        return low;
    }

    private int calculateLessOrEqualPairCount(int m, int n, int mid, EarlyCount earlyResult) {
        // in the form of left/a * right/b with left/a <= right/b, starting with a = 1
        int a = 1;
        int b = mid;
        int count = 0;
        int maxMulti = a;
        while (a <= m && a <= b) {
            // make sure b stays within range
            b = b > n ?n :b;
            maxMulti = a*b > maxMulti ?a*b :maxMulti ;
            // a * [a <- b] <= mid
            count += b - a + 1;
            // swap: [a <- b] *  a also <= mid
            count += (b > m ?m :b) - a + 1;
            // 1 common number duplicate count at top-left corner, hence - by 1
            count -= 1;

            // next
            a = a + 1;
            b = mid / a;
        }
        earlyResult.count = maxMulti;
        return count;
    }
}
