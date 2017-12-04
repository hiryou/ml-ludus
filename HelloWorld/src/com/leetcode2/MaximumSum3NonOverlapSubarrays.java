package com.leetcode2;

import java.util.Arrays;

public class MaximumSum3NonOverlapSubarrays {

    public static void main(String[] args) {
        MaximumSum3NonOverlapSubarrays p = new MaximumSum3NonOverlapSubarrays();

        //int[] a = {1,2,1,2,6,7,5,1}; int k = 2;
        int[] a = {17,7,19,11,1,19,17,6,13,18,2,7,12,16,16,18,9,3,19,5}; int k = 6;
        BenchMark.run( () -> print(p.maxSumOfThreeSubarrays(a, k)) );
    }

    private static String print(int[] a) {
        return Arrays.toString(a);
    }


    /**
     * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/description/
     *
     * Solution: It becomes easier when you first focus on the middle array, the finding out what 2 arrays on the 2 sides
     * should be.
     *
     * Reasoning: For size n subarray size k, we have total of n-k+1 overlapping windows. Now looping through all these windows,
     * decide what should be the best on left and best on right. To do this we need a way to quickly tell by examining the
     * left-right borders of the middle array. Meaning at any index i, what was the best k-window on left of i and what was the
     * best k-window on right of i:
     * 1. Loop 1 time left -> right, logging the best k-window left-side up to index i
     * 2. Loop 1 time right->left, logging the best k-window ride-side up to index i
     * 3. Loop through n-k+1 windows, we have the boundary -> easily sum with data from the 2 above to find out the local best.
     * Lexicographical order is guaranteed because we are choosing middle window by going left -> right and pick the first found best
     *
     * TODO Submit to leetcode when online
     *
     */
    public int[] maxSumOfThreeSubarrays(int[] a, int k) {
        if (a.length < 3) return new int[] {-1, -1, -1};
        int n = a.length;

        int[] left = new int[n];
        int[] leftIdx = new int[n];  // storing the starting idx of the best window
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i=0; i<n; i++) {
            sum += a[i];
            if (i < k-1) {
                left[i] = -1;  // not enough window
                leftIdx[i] = -1;
            } else if (i == k-1) {
                left[i] = sum; // first window
                max = sum;
                leftIdx[i] = 0;
            } else { // i >= k
                // next windows
                sum -= a[i-k];
                if (sum > max) {  // new best window found
                    max = sum;
                    leftIdx[i] = i-k+1;
                } else {  // keep previous best window
                    leftIdx[i] = leftIdx[i-1];
                }
                left[i] = max;
            }
        }

        int[] right = new int[n];
        int[] rightIdx = new int[n];  // storing the starting idx of the best window
        sum = 0;
        max = Integer.MIN_VALUE;
        for (int i=n-1; i>=0; i--) {
            sum += a[i];
            if (i > n-k) {
                right[i] = -1;  // not enough window
                rightIdx[i] = -1;
            } else if (i == n-k) {
                right[i] = sum; // first window
                max = sum;
                rightIdx[i] = n-k;
            } else { // i < n-k
                // next windows
                sum -= a[i+k];
                if (sum >= max) {  // new best window found, == because we want the smallest lexicographical window
                    max = sum;
                    rightIdx[i] = i;
                } else {  // keep previous best window
                    rightIdx[i] = rightIdx[i+1];
                }
                right[i] = max;
            }
        }

        // start with 1st middle window
        sum = 0;
        for (int i=k; i<2*k; i++) sum += a[i];
        max = sum + left[k-1] + right[2*k];
        int lidx = leftIdx[k-1], midx = k, ridx = rightIdx[2*k];
        // now sliding the middle window
        for (int i=k+1; i<n-k; i++) {
            sum += a[i+k-1];
            sum -= a[i-1];
            int val = sum + left[i-1] + right[i+k];
            if (val > max) {
                max = val;
                lidx = leftIdx[i-1];
                midx = i;
                ridx = rightIdx[i+k];
            }
        }

        System.out.println(max);
        return new int[] {lidx, midx, ridx};
    }
}
