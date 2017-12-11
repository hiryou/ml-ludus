package com.leetcode2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/jump-game-ii/description/
 */
public class JumpGame2 {

    public int jump(int[] a) {
        if (a.length <= 1) return 0;

        int startIdx = 0 + 1;
        int endIdx = 0 + a[0];
        boolean reached = false;
        int count = 0;
        while (!reached) {
            ++count;
            int nextEndIdx = startIdx+1;
            for (int i=endIdx; i>=startIdx; i--) if (i < a.length-1) {
                int idx = i + a[i];
                nextEndIdx = idx > nextEndIdx ?idx :nextEndIdx ;
            } else {
                reached = true;
                break;
            }

            startIdx = endIdx + 1;
            endIdx = nextEndIdx;
        }

        return count;
    }
}
