package com.leetcode;

import com.geeksforgeeks.BenchMark;

import java.util.Stack;

/**
 * https://leetcode.com/problems/maximal-rectangle/description/
 *
 * @Diary: had to reference suggestion. Turn out: can be solved like biggest rectangle in histogram
 * How to see it: go down row by row, see each row as a base line for the histogram of '1' above it. Reset height to 0
 * if seeing a value==0
 */
public class MaximalRectangle {

    public static void main(String[] args) {
        MaximalRectangle p = new MaximalRectangle();

        char[][] a = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'0', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };

        BenchMark.run(() -> p.maximalRectangle(a));
    }


    // DP solution by tracking height; left as most conservative left; right as most conservative right
    public int maximalRectangle(char[][] a) {
        if (a.length == 0 || a[0].length == 0) return 0;
        int m = a.length, n = a[0].length;

        int[] left = new int[n];
        int[] right = new int[n];
        int[] height = new int[n];

        int max = 0;
        for (int i = 0; i < m; i++) {
            char[] row = a[i];
            char[] prevRow = i > 0 ?a[i-1] : new char[n];
            int leftj = -1;
            int rightj = -1;

            // height & left
            for (int j = 0; j < n; j++) if (row[j]=='1') {
                // height
                height[j]++;

                // left
                if (leftj == -1) {
                    leftj = j;
                }
                int val = leftj;

                if (i > 0 && prevRow[j]=='1') { // from second row
                    val = Math.max(left[j], val);
                }
                left[j] = val;
            } else {
                height[j] = 0;
                leftj = -1;
            }

            // right
            for (int j = n - 1; j >= 0; j--) if (row[j]=='1') {
                if (rightj == -1) {
                    rightj = j;
                }
                int val = rightj;

                if (i > 0 && prevRow[j]=='1') { // from second row
                    val = Math.min(right[j], val);
                }
                right[j] = val;
            } else {
                rightj = -1;
            }

            // calculate rects lying on this current row
            for (int j = 0; j < n; j++) if (row[j] == '1') {
                int val = (right[j] - left[j] + 1) * height[j];
                max = val > max ? val : max;
            }
        }

        return max;
    }

}
