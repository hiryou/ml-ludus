package com.leetcode3;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/super-washing-machines/description/
 * @Diary: Had to reference solution, it's a DP problem. Solution is simple: It's about balancing them out which can
 * virtually be done by going 1-pass left to right
 */
public class SuperWashingMachines {

    public int findMinMoves(int[] a) {
        if (a==null || a.length <= 1) return 0;
        int N = a.length;

        int totalSum = Arrays.stream(a).reduce((x, y) -> x + y).getAsInt();
        if (totalSum % N != 0) return -1;
        int eachCount = totalSum / N;

        int[] diff = new int[N];
        int max = Integer.MIN_VALUE;
        for (int i=0; i<diff.length; i++) {
            diff[i] = a[i] - eachCount;
            max = Math.abs(diff[i]) > max ?Math.abs(diff[i]) :max ;
        }

        for (int i=1; i<diff.length; i++) {
            // in order to make left machine go to zero, left machine has to transfer all its loads to this one
            diff[i] += diff[i-1];
            //diff[i-1] = 0;
            max = Math.abs(diff[i]) > max ?Math.abs(diff[i]) :max ;
        }

        return max;
    }
}
