package com.leetcode2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * https://leetcode.com/problems/count-of-smaller-numbers-after-self/description/
 * My solution: using treeset looping right to left, distinct potential nodes with same value by using index to compare equals
 *
 * @Diary: My solution is TLE, I had to reference existing solutions; Turned out that BST-building solution was
 * brilliant and simple
 */
public class CountSmallerNumbersAfterSelf {

    public static void main(String[] args) {
        CountSmallerNumbersAfterSelf p = new CountSmallerNumbersAfterSelf();

        /*
        int[] aa = new int[10];
        int i =0, j = 0;
        while (i < aa.length) aa[i++] = j++;
        System.out.println(Arrays.toString(aa));
        */

        //int[] a = {1, 5, 2, 6, 2, 1, 3};
        //int[] a = {};
        int[] a = {26,78,27,100,33,67,90,23,66,5,38,7,35,23,52,22,83,51,98,69,81,32,78,28,94,13,2,97,3,76,99,51,9,21,84,66,65,36,100,41};
        BenchMark.run(() -> p.countSmaller(a));
        BenchMark.run(() -> p.countSmaller2(a));
    }

    class TNode {
        final int val;
        int count = 1; // count for dup values
        int leftCount = 0; // count of nodes - including dup, on my left

        TNode left = null;
        TNode right = null;

        TNode(int val) {
            this.val = val;
        }
    }
    class Tree {
        TNode root = null;

        // return small count found
        public int addVal(int x) {
            if (root == null) {
                root = new TNode(x);
                return 0;
            }

            // else travel the tree
            TNode cur = root;
            TNode insert = null;
            int smallerCount = 0;
            while (insert == null) {
                if (x < cur.val) {
                    cur.leftCount++;
                    if (cur.left != null) {
                        cur = cur.left;
                    } else {
                        cur.left = new TNode(x);
                        insert = cur.left;
                    }
                } else if (x > cur.val) {
                    smallerCount += cur.leftCount + cur.count;
                    if (cur.right != null) {
                        cur = cur.right;
                    } else {
                        cur.right = new TNode(x);
                        insert = cur.right;
                    }
                } else {
                    cur.count++;
                    smallerCount += cur.leftCount;
                    insert = cur;
                }
            }

            return smallerCount;
        }
    }

    // BST building solution
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();

        Tree t = new Tree();
        for (int i=nums.length-1; i>=0; i--) {
            int smallerCount = t.addVal(nums[i]);
            res.add(smallerCount);
        }

        Collections.reverse(res);
        return res;
    }


    class Node {
        int val;
        int idx;

        Node(int val, int idx) {
            this.val = val;
            this.idx = idx;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (val != node.val) return false;
            return idx == node.idx;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + idx;
            return result;
        }
    }

    public List<Integer> countSmaller2(int[] nums) {
        TreeSet<Node> t = new TreeSet<Node>((a, b) -> {
            if (a.val == b.val) return a.idx - b.idx;
            return a.val - b.val;
        });
        List<Integer> res = new ArrayList<>();

        for (int i=nums.length-1; i>=0; i--) {
            Node node = new Node(nums[i], i);
            t.add(node);
            Node test = new Node(nums[i]-1, nums.length);
            res.add(t.headSet(test).size());
        }

        Collections.reverse(res);
        return res;
    }
}
