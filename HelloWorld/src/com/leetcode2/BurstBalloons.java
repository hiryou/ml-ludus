package com.leetcode2;

/**
 * @Diary: Had to reference solution and had a fucking hard time to understand DP reverse thinking.
 * TODO Need more practice & reading to understand DP reverse thinking approach
 */
public class BurstBalloons {

    public static void main(String[] args) {
        BurstBalloons p = new BurstBalloons();

        int[] nums = {3, 1, 5, 8};
        System.out.println(p.maxCoins(nums));
    }


    public long maxCoins(int[] anums) {
        // fill 1 to left and right, skipp all ballon with 0
        int[] nums = new int[anums.length+2];
        nums[0] = 1;
        int idx = 1;
        for (int i=0; i<anums.length; i++) {
            if (anums[i] != 0) nums[idx++] = anums[i];
        }
        nums[idx++] = 1;

        if (nums.length == 2) return 0;

        long[][] memo = new long[nums.length][nums.length];
        return burst(memo, nums, 0, nums.length-1);
    }

    private long burst(long[][] memo, int[] nums, int i, int j) {
        if (j - i == 1) return 0;
        if (memo[i][j] > 0) return memo[i][j];

        long max = 0;
        for (int k=i+1; k<j; k++) {
            // what if k was the last balloon to burst in range [i, j] -> its score was in [i,k,j]
            long score = burst(memo, nums, i, k) + burst(memo, nums, k, j) + (nums[i] * nums[k] * nums[j]);
            if (score > max) max = score;
        }
        memo[i][j] = max;

        return max;
    }
}
