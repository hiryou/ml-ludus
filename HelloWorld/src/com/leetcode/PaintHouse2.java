package com.leetcode;

/**
 * https://leetcode.com/problems/paint-house-ii/description/
 */
public class PaintHouse2 {

    public static void main(String[] args) {
        int[][] costs = {
                {3,1},
                {3,2}
        };
        PaintHouse2 p = new PaintHouse2();
        System.out.println(p.minCostII(costs));
    }



    class MinCell {
        int index;
        int value;
        public MinCell(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    class PairOfMin {
        MinCell min = null;        // smaller
        MinCell nextMin = null;    // bigger

        public void consider(int jIndex, int value) {
            if (min == null) min = new MinCell(jIndex, value);
            else if (nextMin == null) nextMin = new MinCell(jIndex, value);
            else if (value < nextMin.value) {
                nextMin.index = jIndex;
                nextMin.value = value;
            }

            if (nextMin != null && nextMin.value < min.value) doSwap();
        }

        private void doSwap() {
            MinCell t = min;
            min = nextMin;
            nextMin = t;
        }

        int chooseMinDiffIndex(int j) {
            if (min.index == j) return nextMin.value;
            return min.value;
        }
    }

    /**
     * Simple: To pain house[j] with color[x] the min cost is min of house[j-1] + cost[x] for house[j]
     * @param costs
     * @return
     */
    public int minCostII(int[][] costs) {
        int N = costs.length;
        if (N==0) return 0;

        int K = costs[0].length;
        if (K==0) throw new RuntimeException("There's no color to paint with!");
        if (K==1) {
            if (N==1) return costs[0][0];
            throw new RuntimeException("Cannot paint multiple houses with just 1 color!");
        }

        int[][] dp = new int[N][K];
        // Also for each row, we keep track of min and 2nd-min: So that for the next row, we can quickly choose

        // first house: only 1 choice for each color
        PairOfMin curPair = new PairOfMin();
        for (int j=0; j<K; j++) {
            dp[0][j] = costs[0][j];
            curPair.consider(j, dp[0][j]);
        }
        // fill up the table
        for (int i=1; i<N; i++) {
            PairOfMin pair = new PairOfMin();
            for (int j=0; j<K; j++) {
                dp[i][j] = costs[i][j] + curPair.chooseMinDiffIndex(j);
                pair.consider(j, dp[i][j]);
            }

            // later
            curPair = pair;
        }

        return curPair.min.value;
    }
}
