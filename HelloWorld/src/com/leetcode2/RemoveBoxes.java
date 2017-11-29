package com.leetcode2;

/**
 * https://leetcode.com/problems/remove-boxes/description/
 * Very similar to balloon burst problem
 *
 * @Diary: had to reference solution
 */
public class RemoveBoxes {

    public static void main(String[] args) {
        RemoveBoxes p = new RemoveBoxes();

        int[] a = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        BenchMark.run(() -> p.removeBoxes(a));
    }


    public int removeBoxes(int[] boxes) {
        int[][] memo = new int[boxes.length][boxes.length];
        return recursion(memo, boxes, 0, boxes.length-1);
    }

    private int recursion(int[][] memo, int[] a, int i, int j) {
        if (j < i) return 0;
        // stop condition: if a[i..j] is either a range or 2 cells
        if (isOneRange(a, i, j)) return (j-i+1) * (j-i+1);
        // if is 2 range, bursting either range first is fine
        int isTwoRange = isTwoRange(a, i, j);
        if (isTwoRange > 0) return isTwoRange;

        // cached result
        if (memo[i][j] > 0) return memo[i][j];

        // if a[k] was the last one to be removed, it was either with left half or right half. Choose the one yields higher score
        int max = 0;
        for (int k=i+1; k<j; k++) if (a[k] != a[k-1]) {
            int kRight = getKRightIdx(a, k);
            int withLeft = recursion(memo, a, i, kRight) + recursion(memo, a, kRight+1, j);
            int withRight = recursion(memo, a, i, k-1) + recursion(memo, a, k, j);
            max = (withLeft > max) ?withLeft :max ;
            max = (withRight > max) ?withRight :max ;
        }
        memo[i][j] = max;

        return memo[i][j];
    }

    // return 0 for false, score for if exactly 2 ranges
    private int isTwoRange(int[] a, int i, int j) {
        if (i==j) return 0;
        int change = 0;
        int mid = 0;
        for (int k=i+1; k<=j; k++) {
            if (a[k] != a[k-1]) ++change;
            if (change > 1) return 0;
            mid = k-1;
        }
        if (change != 1) return 0;

        return (mid-i+1)*(mid-i+1) + (j-mid)*(j-mid);
    }

    private int getKRightIdx(int[] a, int k) {
        if (k==a.length-1) return k;
        while (k < a.length && a[k] == a[k+1]) ++k;
        return k;
    }

    private boolean isOneRange(int[] a, int i, int j) {
        if (i==j) return true;
        for (int k=i+1; k<=j; k++) if (a[i] != a[k]) return false;
        return true;
    }
}
