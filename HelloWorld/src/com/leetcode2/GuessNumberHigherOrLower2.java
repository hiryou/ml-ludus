package com.leetcode2;

/**
 * https://leetcode.com/problems/guess-number-higher-or-lower-ii/description/
 * This problem is similar to the academic problem of choosing the best way to do matrix calculation so that we have
 * the minimum number of operations. Similar in this question, the problem is: the cost of guessing the whole range
 * a[i..k] is cost[mid] + Max(cost of a[i..mid-1], cost of a[mid+1..k]). Stop condition: when there's 1 element, the cost
 * is 0; 2 elements: cost = the smaller number
 */
public class GuessNumberHigherOrLower2 {

    public static void main(String[] args) {
        GuessNumberHigherOrLower2 p = new GuessNumberHigherOrLower2();

        int n = 235;
        BenchMark.run(() -> p.getMoneyAmount(n));
    }



    public int getMoneyAmount(int n) {
        if (n==1) return 0;
        if (n==2) return 1;

        // memo[i][j] stores the minimum we already found in range [i..j] both ends inclusive
        int[][] memo = new int[n+1][n+1];
        return recursiveCost(memo, 1, n);
    }

    // Time complexity O(n^3); Space complexity O(n^2) for memo[i][j] + O(n) for recursion of depth n is used
    // Without memo, time increases to O(n!) because we call this function n times, each reduces the range of search by 1
    private int recursiveCost(int[][] memo, int i, int j) {
        if (j <= i) return 0;
        if (memo[i][j] != 0) {
            //System.out.println(String.format("Found in memo [%d,%d]", i, j));
            return memo[i][j];
        }

        int min = -1;
        //for (int k=i; k<=j; k++) {
        for (int k=(i+j)/2; k<=j; k++) {
            int cost = k + Math.max(recursiveCost(memo, i, k-1), recursiveCost(memo, k+1, j));
            if (min == -1 || cost < min) min = cost;
        }
        memo[i][j] = min;

        return min;
    }
}
