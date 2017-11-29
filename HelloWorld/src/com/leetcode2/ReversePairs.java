package com.leetcode2;

public class ReversePairs {

    public static void main(String[] args) {
        ReversePairs p = new ReversePairs();

        //int[] a = {1,3,2,3,1};
        //int[] a = {2,4,3,5,1};
        //int[] a = {1,5,6,7,8,6,6,8,8,9,3,3,5,3,6,4};
        //int[] a = {4,1};
        //int[] a = {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};
        int[] a = {-2147483647,2147483647,-2147483647,2147483647,-2147483647,-2147483647};
        BenchMark.run(() -> p.reversePairs(a));
    }



    public int reversePairs(int[] a) {
        if (a.length <= 1) return 0;

        return sortAndCount(a, 0, a.length-1);
    }

    // sortAndCount is technically a merge sort, sorting a[lo..hi]
    private int sortAndCount(int[] a, int lo, int hi) {
        // stop condition
        if (hi - lo <= 0) return 0;

        int mid = (lo + hi) / 2;
        int count = sortAndCount(a, lo, mid)
                + sortAndCount(a, mid+1, hi)
                + getCount(a, lo, mid, hi);  // Note that this is called after merge sort of the 2 halves
        merge(a, lo, mid, hi);

        return count;
    }

    private void merge(int[] a, int lo, int mid, int hi) {
        if (lo >= hi) return;

        int i = lo, j = mid+1;
        int[] ar = new int[hi-lo+1]; int k = 0;
        while (i <= mid && j <= hi) {
            if (a[i] <= a[j]) ar[k++] = a[i++];
            else ar[k++] = a[j++];
        }

        // remnant
        while (i <= mid) ar[k++] = a[i++];
        while (j <= hi) ar[k++] = a[j++];

        // plug back to a
        for (k=0; k<ar.length; k++) {
            a[lo+k] = ar[k];
        }
    }

    // assume each half was already sorted
    private int getCount(int[] a, int lo, int mid, int hi) {
        int count = 0;
        int i = mid, j = hi;
        while (i >= lo) {
            while (j > mid && !satisfied(a, i, j)) --j;
            if (j == mid) break;
            count += (j - mid);
            // later
            --i;
        }
        return count;
    }

    // TODO 32-bit integer taking care of
    private boolean satisfied(int[] a, int i, int j) {
        return i < j && a[i]*1L > a[j]*2L;
    }
}
