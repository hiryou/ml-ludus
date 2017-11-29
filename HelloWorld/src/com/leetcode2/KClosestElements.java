package com.leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/find-k-closest-elements/description/
 */
public class KClosestElements {

    public static void main(String[] args) {
        KClosestElements p = new KClosestElements();

        int[] a = {1,2,3,4,5}; int k = 4, x = 3;
        //int[] a = {1,2,3,4,5}; int k = 4, x = 10;
        //int[] a = {}; int k = 0, x = -1;
        System.out.println(p.findClosestElements(a, k, x));
    }



    /**
     * Solution: First of all binary search the array to find the index i so that a[i] is closest to x. Then from [i]
     * slowly expand either left or right 1 cell at a time to get the next closest element. Stop when k elements found
     */
    public List<Integer> findClosestElements(int[] a, int k, int x) {
        if (k==0 || a.length==0) return new ArrayList<>();

        //return mySolution(a, k, x);
        return otherSolution(a, k, x);
    }

    /**
     * Other solution on leetcode + My explanation:
     * Final answer to this problem is a subarray[lo, lo+k) that contains x and is closest to x. Your job is to find
     * low, then the range automatically found.
     * - Range for low: low has to be anywhere in [0...n-k]
     * - Justification for why we choose low: dist(a[low]) <= dist[a[low+k]]
     * => So, we can binary search for low in [0..[n-k]], set low = 0, hi = n-k, mid = (hi + low) / 2
     *      + if dist(a[mid]) <= dist[a[mid+k]], it's sure that low should be in lower range
     *      + Otherwise, low should be in the higher range
     *
     * Intuitively thinking, this is correct: Let say x is somewhere close to the middle of the array. When you first start,
     * lo = 0 and lo+k is <= mid of the array. It's easy to see that dist(a[low+k]) is better than dist(a[low]), causing
     * the binary search to move up. Vice versa, it moves down.
     */
    private List<Integer> otherSolution(int[] arr, int k, int x) {
        int low = 0, hi = arr.length - k;
        while (low < hi) {
            int mid = (low + hi) / 2;
            if (getDist(x, arr[mid]) <= getDist(x, arr[mid+k]))
                hi = mid;
            else
                low = mid + 1;
        }
        return buildResult(Arrays.copyOfRange(arr, low, low+k));
    }

    private List<Integer> mySolution(int[] a, int k, int x) {
        int closestIdx = binSearch(a, x);
        // now expand from this closest index
        int count = 1;
        int left = closestIdx;
        int right = closestIdx;
        while (count < k) {
            if (left == 0) {
                return buildResult(Arrays.copyOfRange(a, left, left + k));
            } else if (right == a.length-1) {
                return buildResult(Arrays.copyOfRange(a, right - k + 1, right+1));
            }

            // prefer smaller element for same dist
            if (getDist(x, a[left-1]) <= getDist(x, a[right+1])) {
                --left;
            } else {
                ++right;
            }
            ++count;
        }

        // later
        return buildResult(Arrays.copyOfRange(a, left, right+1));
    }

    private List<Integer> buildResult(int[] a) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<a.length; i++) {
            result.add(a[i]);
        }
        return result;
    }

    // binary search for the first element in a that is closest to x
    private int binSearch(int[] a, int x) {
        if (x <= a[0]) return 0;
        if (a[a.length-1] <= x) return a.length-1;

        int s = 0, e = a.length-1;

        while (s < e) {
            int mid = (s + e)/2;
            if (x < a[mid]) {
                e = mid;
            } else if (x > a[mid]) {
                s = mid;
            } else { // exact equal
                return mid;
            }

            if (e - s == 1) break;
        }

        if (s == e) return e;

        // for same dist prefer smaller element
        return getDist(x, a[s]) <= getDist(x, a[e]) ?s :e ;
    }

    private int getDist(int x, int y) {
        return Math.abs(x - y);
    }
}
