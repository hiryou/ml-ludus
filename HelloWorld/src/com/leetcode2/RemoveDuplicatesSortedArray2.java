package com.leetcode2;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/
 */
public class RemoveDuplicatesSortedArray2 {

    public static void main(String[] args) {
        RemoveDuplicatesSortedArray2 p = new RemoveDuplicatesSortedArray2();

        //int[] a = {1,1,1,2,2,3};
        int[] a = {1,1,1};
        BenchMark.run(() -> p.removeDuplicates(a));
    }


    public int removeDuplicates(int[] nums) {
        int k = 2;
        if (nums.length<=k) return nums.length;

        int len = nums.length;
        int i=0, j = 0;
        int last = nums[j], count = 1;
        for (i=1; (j+1)<nums.length; i++) {
            ++j;
            if (nums[j] != last) {
                last = nums[j];
                count = 1;
            } else {
                ++count;
                if (count > k) {
                    while (j+1 < nums.length && nums[j] == nums[j+1]) {
                        --len;
                        ++j;
                    }
                    j += 1;
                    --len;
                    if (j < nums.length) {
                        last = nums[j];
                        count = 1;
                    }
                }
            }
            if (j < nums.length) {
                nums[i] = nums[j];
            }
        }

        return len;
    }
}
