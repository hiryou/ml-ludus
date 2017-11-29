package com.leetcode;

import com.leetcode2.BenchMark;

import java.util.Arrays;

/**
 * Median of Two Sorted Arrays
 * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
 * @Diary: I referenced hints & solution suggestion
 * The use of simple Math helps here: https://leetcode.com/problems/median-of-two-sorted-arrays/solution/
 */
class MedianOf2SortedArrays {

    public static void main(String[] args) {
        MedianOf2SortedArrays p = new MedianOf2SortedArrays();

        //int[] a = {1, 3}, b = {2};
        //int[] a = {1, 2, 2, 2}, b = {2, 2, 4, 5};
        //int[] a = {1, 3}, b = {2, 4};
        //int[] a = {1}, b = {2, 3, 4};
        //int[] a = {1, 5}, b = {2, 3};
        int[] a = {4, 5}, b = {1, 2, 3};
        BenchMark.run(() -> p.findMedianSortedArrays(a, b));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length==0 && nums2.length==0) return 0;
        if (nums1.length==0) return midValueOf(nums2);
        if (nums2.length==0) return midValueOf(nums1);

        // now both of them have length > 0
        return advancedBinSearch(nums1, nums2);
    }

    private static double midValueOf(int[] nums) {
        int midIdx = nums.length / 2;
        if (nums.length % 2 == 1) return nums[midIdx];
        return (nums[midIdx-1] + nums[midIdx]) / 2.0;
    }

    // Bin search in small array, from it derives the cut in bigger array
    private double advancedBinSearch(int[] a, int[] b) {
        if (a.length > b.length) return advancedBinSearch(b, a);

        int m = a.length;
        int n = b.length;

        // find a cut in a, cut i is inclusive of a[i]; cut 0 makes empty left half; cut a.length makes empty right half
        int Ai = a[0], Bj = b[0], Ain = a[m-1], Bjn = b[n-1];
        int cutA = 0, cutB = 0;
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (hi + lo) / 2;
            cutA = mid;               // count of proposed left half elements in A
            cutB = (m + n)/2 - cutA;  // derived count of proposed left half elements in B

            Ai  = cutA-1 >= 0 ?a[cutA-1] :Integer.MIN_VALUE;
            Bj  = cutB-1 >= 0 ?b[cutB-1] : Integer.MIN_VALUE;
            Ain = cutA < a.length ?a[cutA] :Integer.MAX_VALUE;
            Bjn = cutB < b.length ?b[cutB] : Integer.MAX_VALUE;
            // A0...Ai | Ain...An-1 . Should be that Ai <= Bin
            // B0...Bj | Bjn...Bn-1 . And should be: Bj <= Ain

            if (Bjn < Ai) hi = mid;     // need to move left in A for better cut
            else if (Ain < Bj) lo = mid + 1; // need to move right in A for better cut
            else lo = hi = mid;  // if lucky, early found
        }

        // in case A is too small array, a final correction will be needed
        cutA = lo;
        cutB = (m+n)/2 - cutA;
        Ai  = cutA-1 >= 0 ?a[cutA-1] :Integer.MIN_VALUE;
        Bj  = cutB-1 >= 0 ?b[cutB-1] : Integer.MIN_VALUE;
        Ain = cutA < a.length ?a[cutA] :Integer.MAX_VALUE;
        Bjn = cutB < b.length ?b[cutB] : Integer.MAX_VALUE;

        int[] res = new int[4];
        res[0] = Ai;
        res[1] = Ain;
        res[2] = Bj;
        res[3] = Bjn;
        Arrays.sort(res);

        int leftCount = cutA + cutB;
        if ((m+n) % 2 == 1) {
            int rightCount = m+n - leftCount;
            if (leftCount > rightCount) return res[1];
            return res[2];
        }

        return (res[1] + res[2]) / 2.0;
    }
}
