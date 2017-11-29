package com.leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/***
 * https://leetcode.com/problems/smallest-range/description/
 * @Diary: had to reference solution
 *
 * Solution: Use a min heap, start with tracking 1 element from each array. Start by the min element of each array
 * -> The heap: from min->max is 1 candidate of a range. This is the idea
 * - Whenever a min heap item is popped, move the cursor to the next item in the owning array and add such item to the
 *      heap. So at anytime, the heap always wholes a true range that covers at least 1 element from each array.
 *
 * Great deal of learning from this question: a heap can be used to effectively track a running range
 */
public class SmallestRange {

    public static void main(String[] args) {
        SmallestRange p = new SmallestRange();

        List<List<Integer>> ras = new ArrayList<List<Integer>>(){{
            add(Arrays.asList(4,10,15,24,26));
            add(Arrays.asList(0,9,12,20));
            add(Arrays.asList(5,18,22,30));
        }};
        BenchMark.run(() -> print(p.smallestRange(ras)));
    }

    private static void print(int[] ints) {
        System.out.println(String.format("[%d, %d]", ints[0], ints[1]));
    }



    class Node implements Comparable<Node> {
        final List<Integer> ref;
        final int idx; // idx of this node in the owning list

        public Node(List<Integer> ref, int idx) {
            this.ref = ref;
            this.idx = idx;
        }

        Node nextNode() {
            return new Node(ref, idx+1);
        }

        boolean isLastItem() {
            return idx == ref.size()-1;
        }

        int val() {
            return ref.get(idx);
        }

        @Override
        public int compareTo(Node o) {
            if (this.val() > o.val()) return +1;
            if (this.val() < o.val()) return -1;
            return 0;
        }
    }

    class MyHeap {
        final PriorityQueue<Node> q = new PriorityQueue<>();
        int max = Integer.MIN_VALUE;

        int start() {
            return q.peek().val();
        }

        int end() {
            return max;
        }

        void add(Node node) {
            max = (node.val() > max) ?node.val() :max ;
            q.add(node);
        }

        boolean moveNext() {
            while (!canMove()) return false;
            Node node = q.remove();
            this.add(node.nextNode());
            return true;
        }

        private boolean canMove() {
            if (q.isEmpty()) return false;
            return !q.peek().isLastItem();
        }

        int range() {
            return start() - end();
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        MyHeap heap = new MyHeap();

        // start with min element in each list
        for (List<Integer> l: nums) {
            heap.add(new Node(l, 0));
        }
        int s = heap.start(); int t = heap.end();

        while (heap.moveNext()) {
            if (heap.range() < (t - s)) {
                s = heap.start();
                t = heap.end();
            }
        }

        return new int[] {s, t};
    }
}
