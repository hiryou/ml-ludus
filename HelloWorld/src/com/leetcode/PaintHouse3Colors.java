package com.leetcode;

/**
 * https://leetcode.com/problems/paint-house-ii/description/
 */
public class PaintHouse3Colors {

    public static void main(String[] args) {
        int[][] costs = {
                {1,4,6},
                {7,8,4},
                {6,6,2},
                {8,9,7},
                {4,2,3},
                {1,5,9}
        };
        PaintHouse3Colors p = new PaintHouse3Colors();
        System.out.println(p.minCost(costs));
    }



    /**
     * Simple: To pain house[j] with color[x] the min cost is min of house[j-1] + cost[x] for house[j]
     * @param costs
     * @return
     */
    public int minCost(int[][] costs) {
        int N = costs.length;
        if (N==0) return 0;

        int K = costs[0].length;

        int[][] dp = new int[N][K];
        int curMin = -1;
        // first house: only 1 choice for each color
        for (int j=0; j<K; j++) {
            dp[0][j] = costs[0][j];
            if (curMin < 0 || dp[0][j] < curMin) {
                curMin = dp[0][j];
            }
        }
        // fill up the table
        for (int i=1; i<N; i++) {
            int rowMin = -1;
            for (int j=0; j<K; j++) {
                dp[i][j] = costs[i][j] + chooseMinDiffIndex(j, dp[i-1]);
                if (rowMin < 0 || dp[i][j] < rowMin) {
                    rowMin = dp[i][j];
                }
            }
            curMin = rowMin;
        }

        return curMin;
    }

    private int chooseMinDiffIndex(int jIndex, int[] row) {
        int a = 0, b = 0;
        if (jIndex==0) {
            a = row[1]; b = row[2];
        }
        else if (jIndex==1) {
            a = row[0]; b = row[2];
        }
        else if (jIndex==2) {
            a = row[0]; b = row[1];
        }
        return Math.min(a, b);
    }
}
