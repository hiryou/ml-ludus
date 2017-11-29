package com.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/find-k-pairs-with-smallest-sums/description/
 */
public class KPairsSmallestSums {

    public static void main(String[] args) {
        KPairsSmallestSums p = new KPairsSmallestSums();

        //int[] nums1 = {1,7,11}; int[] nums2 = {2,4,6}; int k = 4;
        //int[] nums1 = {1,1,2}; int[] nums2 = {1,2,3}; int k = 10;
        int[] nums1 = {-13,23,44,117,900,990}; int[] nums2 = {-15,20,35,118,223,500,663,717,789,813}; int k = 10;
        System.out.println(prettyPrint(p.kSmallestPairs(nums1, nums2, k)));
    }

    private static String prettyPrint(List<int[]> pairs) {
        StringBuilder b = new StringBuilder();
        for (int i=0; i<pairs.size(); i++)
            b.append("[" + pairs.get(i)[0] + "," + pairs.get(i)[1] + "],");
        return b.toString();
    }




    class Node implements Comparable<Node> {
        final int a;
        final int b;

        public Node (int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getSum() {
            return a + b;
        }

        @Override
        public int compareTo(Node o) {
            // this is used for max heap, so...
            return o.getSum() - this.getSum();
        }
    }

    class MyMaxHeap {
        final int maxSize;
        final PriorityQueue<Node> nodes = new PriorityQueue<>();

        public MyMaxHeap(int maxSize) {
            this.maxSize = maxSize;
        }

        public void checkAndAdd(int a, int b) {
            if (maxSize == 0) return;

            Node node = new Node(a, b);
            if (nodes.size() < maxSize) {
                nodes.add(node);
                return;
            } else if (node.getSum() < nodes.peek().getSum()) {
                nodes.remove();
                nodes.add(node);
            }
        }

        public boolean willBeQualified(int na, int nb) {
            return (na + nb) < nodes.peek().getSum();
        }

        // Bad designed question makes me have to print from smallest pair to biggest pair
        public List<int[]> getResultList() {
            List<Node> collect = new ArrayList<>(nodes);
            java.util.Collections.sort(collect, Comparator.comparing((Node node) -> node.getSum()));
            return collect.stream().map(node -> new int[] {node.a, node.b}).collect(Collectors.toList());
        }
    }

    /**
     * Algorithm: Use a k-fixed size max heap to store k smallest pairs so far. Also we smartly figure a range nusm1[0..a]
     * and nums2[0..b] such that k smallest pairs are most likely to be in this range. This algorithm is a mix of
     * heuristic and heap. The pre-step of heuristic help makes the least possible number of time we have to swap out the
     * top of the max heap
     */
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> result = new ArrayList<>();
        int m = nums1.length;
        int n = nums2.length;

        if (m == 0 || n == 0 || k == 0) return result;
        if (k >= n * m) {
            result.addAll(allPairs(nums1, nums2));
            return result;
        }

        // smartly figure the conservative range nums1[0..x] and nums2[0..y]
        int x = 0;
        int y = 0;
        int diff = nums1[x] - nums2[y];
        // conservatively move x and y up until x*y > k
        while ((x + 1) * (y + 1) < k) {
            if (x == nums1.length - 1) ++y;
            else if (y == nums2.length - 1) ++x;
            else {
                if (Math.abs(diff + nums1[x + 1]) <= Math.abs(diff - nums2[y + 1])) {
                    // move x up
                    diff += nums1[x + 1];
                    ++x;
                } else {
                    // move y up
                    diff -= nums2[y + 1];
                    ++y;

                }
            }
        }

        // now nums1[0..x] and nums2[0..y] have more dense number of smallest pairs (or, heuristically, most likely)
        MyMaxHeap heap = new MyMaxHeap(k);
        for (int i = 0; i <= x; i++)
            for (int j = 0; j <= y; j++) {
                heap.checkAndAdd(nums1[i], nums2[j]);
            }

        // now for the remaining multiplicity nums1[0..x]*nums2[y+1..] and nums1[x+1..]*nums2[0..y]
        checkRemainRange(heap, nums1, x, nums2, y, false);
        // same loop for other
        checkRemainRange(heap, nums2, y, nums1, x, true);

        return heap.getResultList();
    }

    // 0 to x multiplicity with y+1 to end
    private void checkRemainRange(MyMaxHeap heap, int[] nums1, int x, int[] nums2, int y, boolean swapOrder) {
        for (int i=0; i<=x; i++) {
            for (int j=y+1; j<nums2.length; j++) {
                if (!heap.willBeQualified(nums1[i], nums2[j])) break;
                if (swapOrder) {
                    heap.checkAndAdd(nums2[j], nums1[i]);
                } else {
                    heap.checkAndAdd(nums1[i], nums2[j]);
                }
            }
        }
    }

    private List<int[]> allPairs(int[] nums1, int[] nums2) {
        List<int[]> result = new ArrayList<>();
        for (int i=0; i<nums1.length; i++) for (int j=0; j<nums2.length; j++) {
            result.add(new int[] {nums1[i], nums2[j]});
        }
        return result;
    }
}
