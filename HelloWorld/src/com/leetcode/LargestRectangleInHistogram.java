package com.leetcode;

import java.util.Stack;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/description/
 * Diary: Initial attempt failed, spent more than 30min trying, finally referenced solution
 *
 * Algorithm explanation: http://www.geeksforgeeks.org/largest-rectangle-under-histogram/
 * My own explanation: Scanning the histogram from left -> right, from a granular perspective, the histogram is basically
 * a list of "ascending triangles". Your job is to find the largest rectangle in each/all of these triangles
 *                                  /| __/x| /|
 * History granular perspective: /|/x|/ x x|/x|
 *
 * This explains why we should maintain a stack to add elements from left -> right and only add if current element is
 * bigger than the stack's top, why? Because only such will maintain ascending triangular shapes
 */
public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        LargestRectangleInHistogram program = new LargestRectangleInHistogram();
        int[] hist = new int[] {2, 1, 5, 6, 3, 3};
        System.out.println(program.largestRectangleArea(hist));
    }



    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0) return 0;

        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        for (int idx=0; idx<heights.length; idx++) {
            if (canAddValueTo(stack, heights, heights[idx])) {
                stack.push(idx);
            } else {
                int bestArea = popStackUntilCanAddAgain(stack, heights, idx, heights[idx]);
                maxArea = (bestArea > maxArea) ?bestArea :maxArea ;
            }
        }

        // now process the last "ascending area" left in the stack
        int bestArea = processLastAreaInStack(stack, heights);
        maxArea = (bestArea > maxArea) ?bestArea :maxArea ;

        return maxArea;
    }

    private int processLastAreaInStack(Stack<Integer> stack, int[] heights) {
        int fakeValue = 0;
        int bestArea = popStackUntilCanAddAgain(stack, heights, heights.length, fakeValue);
        return bestArea;
    }

    private int popStackUntilCanAddAgain(Stack<Integer> stack, int[] heights, int idx, int value) {
        int maxArea = 0;

        int rightMostIdx = stack.peek();
        while (!canAddValueTo(stack, heights, value)) {
            int popIdx = stack.pop();
            int histHeight = heights[popIdx];
            int rightLength = rightMostIdx - popIdx;
            int leftLengthInclusive = popIdx - (!stack.isEmpty() ?stack.peek() :-1);
            int area = histHeight * (leftLengthInclusive + rightLength);
            maxArea = (area > maxArea) ?area :maxArea ;
        }

        // make sure again before adding
        if (canAddValueTo(stack, heights, value)) {
            stack.push(idx);
        }

        return maxArea;
    }

    private boolean canAddValueTo(Stack<Integer> stack, int[] heights, int value) {
        if (stack.isEmpty()) return true;
        int topIdx = stack.peek();
        return value > heights[topIdx];
    }
}
