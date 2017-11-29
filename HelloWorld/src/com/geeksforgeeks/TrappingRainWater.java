package com.geeksforgeeks;

/**
 * http://www.geeksforgeeks.org/trapping-rain-water/
 * https://leetcode.com/problems/trapping-rain-water/description/
 */
public class TrappingRainWater {

    public static void main(String[] args) {
        TrappingRainWater p = new TrappingRainWater();

        //int[] ar = {2, 0, 2};
        //int[] ar = {3, 0, 0, 2, 0, 4};
        int[] ar = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        //int[] ar = {3, 1, 3};
        BenchMark.run(() -> p.solveTrappingRainWater(ar));
    }

    // linear solution using 2 pointers 2 side left right approaching each other
    int solveTrappingRainWater(int[] a) {
        if (a.length <= 2) return 0;
        int lmax = 0; int rmax = 0;
        int lo = 0, hi = a.length-1;

        int water = 0;
        while (lo <= hi) {
            if (lmax <= rmax) {
                int right = Math.max(rmax, a[lo+1]);
                int waterLevel = Math.min(lmax, right);
                if (waterLevel > a[lo]) {
                    water += waterLevel - a[lo];
                }
                if (a[lo] > lmax) lmax = a[lo];
                // move lo ->
                lo++;
            } else {
                int left = Math.max(lmax, a[hi-1]);
                int waterLevel = Math.min(left, rmax);
                if (waterLevel > a[hi]) {
                    water += waterLevel - a[hi];
                }
                if (a[hi] > rmax) rmax = a[hi];
                // move hi <-
                hi--;
            }
        }
        return water;
    }

    // linear solution tracking rmax[i] and lmax[i]
    int solveTrappingRainWater2(int[] a) {
        if (a.length <= 2) return 0;
        int[] lmax = new int[a.length]; // lmax[i] is left max of a[i]
        int[] rmax = new int[a.length]; // rmax[i] is right max of a[i]

        int max = a[0];
        for (int i=0; i<a.length; i++) {
            max = (a[i] > max) ?a[i] :max ;
            lmax[i] = max;
        }

        max = a[a.length-1];
        for (int i=a.length-1; i>=0; i--) {
            max = (a[i] > max) ?a[i] :max ;
            rmax[i] = max;
        }

        int water = 0;
        for (int i=0; i<a.length; i++) {
            water += Math.max(0, Math.min(lmax[i], rmax[i]) - a[i]);
        }
        return water;
    }
}
