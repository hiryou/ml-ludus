package com.leetcode2;

/**
 * https://leetcode.com/problems/target-sum/description/
 */
public class TargetSum {

    public static void main(String[] args) {
        //int[] nums = {1,1,1,1,1}; int S = 3;
        int[] nums = {1000}; int S = -1000;

        TargetSum p = new TargetSum();
        BenchMark.run(() -> p.findTargetSumWays(nums, S));
    }



    public int findTargetSumWays(int[] nums, int S) {
        if (nums.length==0) return S==0 ?1 :0;
        if (nums.length==1) return S==nums[0] || S==-nums[0] ?1 :0 ;

        return recursion(nums, 0, S, 0);
    }

    /**
     * Simple solution: Simple recursion
     */
    private int recursion(int[] nums, int idx, int target, int outcome) {
        // stop condition
        if (idx == nums.length) {
            if (outcome == target) return 1;
            return 0;
        }

        int count = 0;
        // +
        count += recursion(nums, idx+1, target, outcome + nums[idx]);
        // -
        count += recursion(nums, idx+1, target, outcome - nums[idx]);

        return count;
    }
}
