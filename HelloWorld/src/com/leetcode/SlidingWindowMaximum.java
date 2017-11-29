package com.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/sliding-window-maximum/description/
 * @Diary: Got stuck; Had to reference solution -> Found that a simple solution could be very elegant
 * The idea: When you combine 2 range [a->] and [<-b], all you need is to compare what was the max on the right of [a] vs
 * what was the max on the left side of [b] -> this forms the max of the combined range [a-><-b]
 *
 * -> Partition leftMax:  xxxx|xxxx|xxx
 * -> Partition rightMax; xxxx|xxxx|xxx
 * -> When sliding: max of range[a-><-b] = rightMax[a->] vs leftMax[<-b]
 */
public class SlidingWindowMaximum {

    public static void main(String[] args) {
        SlidingWindowMaximum p = new SlidingWindowMaximum();
        //int[] nums = {1,3,-1,-3,5,3,6,7}; int k = 3;
        int[] nums = {1, 2, 3}; int k = 3;
        System.out.println(prettyPrint(p.maxSlidingWindow(nums, k)));
    }

    private static List<Integer> prettyPrint(int[] ints) {
        List<Integer> nums = new ArrayList<>();
        for (int num: ints) {
            nums.add(num);
        }
        return nums;
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length==0 || k > nums.length) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // fill partition leftMax
        int lmax = nums[0];
        for (int i=0; i<n; i++) {
            if (i % k == 0) { // reset max
                lmax = nums[i];
            }
            lmax = Math.max(lmax, nums[i]);
            leftMax[i] = lmax;
        }
        // fill partition rightMax
        int rmax = nums[n-1];
        for (int i=n-1; i>=0; i--) {
            if (i == n-1 || (i+1) % k == 0) { // reset max
                rmax = nums[i];
            }
            rmax = Math.max(rmax, nums[i]);
            rightMax[i] = rmax;
        }

        // now slide window and easily compare
        for (int i=0; i<result.length; i++) {
            result[i] = Math.max(rightMax[i], leftMax[i+k-1]);
        }

        return result;
    }
}
