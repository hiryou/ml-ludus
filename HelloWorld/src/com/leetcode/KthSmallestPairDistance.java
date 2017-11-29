package com.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/
 * @Diary : had to reference & understand the very elegant derived binary search. Best program so far!
 */
public class KthSmallestPairDistance {

    public static void main(String[] args) {
        KthSmallestPairDistance p = new KthSmallestPairDistance();
        int[] nums = {1, 2, 1, 5}; int k = 6;
        System.out.println(p.smallestDistancePair(nums, k));
    }



    class Node implements Comparable<Node> {
        final int a;
        final int b;

        public Node (int a, int b) {
            // make sure to call with a <= b
            this.a = a;
            this.b = b;
        }

        public int getDistance() {
            return b - a;
        }

        @Override
        public int compareTo(Node o) {
            // this is used for max heap, so...
            return o.getDistance() - this.getDistance();
        }
    }

    class MyMaxHeap {
        final int maxSize;
        final PriorityQueue<Node> nodes = new PriorityQueue<>();

        public MyMaxHeap(int maxSize) {
            this.maxSize = maxSize;
        }

        // assume already: a <= b
        public void checkAndAdd(int a, int b) {
            if (maxSize == 0) return;

            Node node = new Node(a, b);
            if (nodes.size() < maxSize) {
                nodes.add(node);
                return;
            } else if (node.getDistance() < nodes.peek().getDistance()) {
                nodes.remove();
                nodes.add(node);
            }
        }

        public Node peek() {
            return this.nodes.peek();
        }
    }

    /**
     * My initial solution: First sort the array which takes O(nlog(n)). Then loop the array in a smart way such that
     * approximately pairs with smallest distance will be encountered first. At the same time we maintain a k-size max
     * heap that holds k smallest distance pairs. As we loop through all pairs we build up this special heap. At the end,
     * the heap's top is the answer
     */
    private int naiveSolutionWithKSizeMaxHeap(int[] nums, int k) {
        MyMaxHeap heap = new MyMaxHeap(k);
        for (int gap=1; gap<nums.length-1; gap++) {
            for (int i=0; i<nums.length-gap; i++) {
                int j = i + gap;
                heap.checkAndAdd(nums[i], nums[j]);
            }
        }
        return heap.peek().getDistance();
    }



    public int smallestDistancePair(int[] nums, int k) {
        if (k==0 || nums.length <= 1) return 0;
        Arrays.sort(nums);

        //return naiveSolutionWithKSizeMaxHeap(nums, k);
        return binSearchSolution(nums, k);
    }

    // nums is already sorted
    private int binSearchSolution(int[] nums, int k) {
        int loDist = 0;
        int hiDist = nums[nums.length-1] - nums[0];
        while (loDist < hiDist) {
            int miDist = loDist + (hiDist - loDist)/2;
            // now count number of pairs whose distances <= mid point distance
            int count = getLessOrEqualPairCount(miDist, nums);

            if (count >= k) {
                hiDist = miDist;
            } else {
                loDist = miDist + 1;
            }
        }

        return loDist;
    }

    // get count of pairs whose distances <= miDist
    private int getLessOrEqualPairCount(int miDist, int[] nums) {
        int count = 0;
        for (int j=nums.length-1; j>=1; j--) {
            int i = j - 1;
            while (i >= 0 && nums[j] - nums[i] <= miDist) {
                ++count;
                --i;
            }
        }
        return count;
    }
}



