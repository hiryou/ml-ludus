package com.leetcode2;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/count-of-range-sum/description/
 *
 * TODO Fill in Math reasoning later
 */
public class CountRangeSum {

    public static void main(String[] args) {
        CountRangeSum p = new CountRangeSum();

        //int[] a = {-2, 5, -1}; int lower = -2, upper = 2;
        //int[] a = {1, 2, 4, 5, 5, 6, 8, 7, 5, 7, 5, 5, 3, 5, 6, 7}; int lower = -10, upper = 15;
        //int[] a = {0}; int lower = 0, upper = 0;
        int[] a = {-2147483647, 0, -2147483647, 2147483647}; int lower = -564, upper = 3864;
        BenchMark.run(() -> p.countRangeSum(a, lower, upper));
    }



    // This is someone else's solution, not mine
    public int countRangeSum2(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1) return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower) k++;
            while (j < end && sums[j] - sums[i] <= upper) j++;
            while (t < end && sums[t] < sums[i]) cache[r++] = sums[t++];
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }



    // this is my solution
    public int countRangeSum(int[] a, int lower, int upper) {
        if (a.length <= 0) return 0;

        // prefix sum
        long[] s = new long[a.length+1];
        for (int i=0; i<a.length; i++)
            s[i+1] = s[i] + a[i];
        return mergeSortAndCount(s, 0, s.length-1, lower, upper);
    }

    private int mergeSortAndCount(long[] s, int lo, int hi, int lower, int upper) {
        // stop condition for merge sort
        if (hi <= lo) return 0;

        int mid = (lo + hi) / 2;
        return mergeSortAndCount(s, lo, mid, lower, upper)
                + mergeSortAndCount(s, mid+1, hi, lower, upper)
                + countThenMerge(s, lo, mid, hi, lower, upper);
    }

    // this is the core function
    private int countThenMerge(long[] a, int lo, int mid, int hi, int lower, int upper) {
        if (hi <= lo) return 0;
        int count = getCount(a, lo, mid, hi, lower, upper);

        long[] b = new long[hi - lo + 1];

        int i = lo, j = mid+1, k = 0;
        while (i <= mid && j <=hi) {
            if (a[i] <= a[j]) b[k++] = a[i++];
            else b[k++] = a[j++];
        }

        // remnant
        while (i <= mid) b[k++] = a[i++];
        while (j <= hi) b[k++] = a[j++];

        // copy array b over to a
        for (i=0; i<b.length; i++) {
            a[i + lo] = b[i];
        }

        return count;
    }

    private int getCount(long[] a, int lo, int mid, int hi, int lower, int upper) {
        // first search for satisfied condition 1: lower
        int[] res1 = new int[mid - lo + 1];  // res1[i] = first found j index: s[i] <= s[j] - lower
        Arrays.fill(res1, -1);
        int i = lo; int j = mid+1;
        while (i <= mid && j <= hi) {
            while (j <= hi && !satisfiedLo(a, i, j, lower)) j++;
            if (j <= hi) {
                res1[i - lo] = j;
                ++i;
            }
        }

        // second search for satisfied condition 2: upper
        int[] res2 = new int[mid - lo + 1];  // res2[i] = first found j index: s[i] >= s[j] - upper
        Arrays.fill(res2, -1);
        i = mid; j = hi;
        while (i >= lo && j >= mid+1) {
            while (j >= mid+1 && !satisfiedUp(a, i, j, upper)) j--;
            if (j >= mid+1) {
                res2[i - lo] = j;
                --i;
            }
        }

        int count = 0;
        for (i=0; i<res1.length; i++) {
            if (res1[i] != -1 && res2[i] != -1)
                count += res2[i] - res1[i] + 1;
        }
        return count;
    }

    boolean satisfiedLo(long[] s, int i, int j, int lower) {
        if (!inBound(s, i)) return false;
        if (!inBound(s, j)) return false;
        return i < j && s[i] <= s[j] - lower;
    }

    boolean satisfiedUp(long[] s, int i, int j, int upper) {
        if (!inBound(s, i)) return false;
        if (!inBound(s, j)) return false;
        return i < j && s[i] >= s[j] - upper;
    }

    boolean inBound(long[] s, int i) {
        return i >= 0 && i < s.length;
    }
}
