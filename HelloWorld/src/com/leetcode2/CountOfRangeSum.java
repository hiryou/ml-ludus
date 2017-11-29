package com.leetcode2;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/count-of-range-sum/description/
 *
 * My solution: Convert range to binary tree
 * @Diary turned out that converting in such manner to a BIN tree is also n^2. I had to reference solution
 */
public class CountOfRangeSum {

    public static void main(String[] args) {
        CountOfRangeSum p = new CountOfRangeSum();

        //int[] a = {-2, 5, -1}; int lower = -2, upper = 2;
        int[] a = {1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10,1,2,3,4,5,6,7,8,9,10}; int lower = 5, upper = 29;
        BenchMark.run(() -> p.countRangeSum(a, lower, upper));
    }


    @RequiredArgsConstructor
    @EqualsAndHashCode
    class MyPair {
        final int i;
        final int j;
    }
    Set<MyPair> set = new HashSet<>();

    class Node {

        int[] a;
        int val;
        final MyPair pair;

        Node(int[] a, int s, int t, int val) {
            this.a = a;
            this.val = val;
            this.pair = new MyPair(s, t);
        }

        public int splitAndCount(int lower, int upper) {
            int me = 0;
            if (!set.contains(pair)) {
                me = (lower <= this.val && this.val <= upper) ?1 :0;
                set.add(pair);
            }
            if (!canSplit()) return me;

            Node left = new Node(a, pair.i, pair.j-1, val - a[pair.j]);
            Node right = new Node(a, pair.i+1, pair.j, val - a[pair.i]);
            return me + left.splitAndCount(lower, upper) + right.splitAndCount(lower, upper);
        }

        private boolean canSplit() {
            return pair.i < pair.j;
        }
    }

    public int countRangeSum(int[] a, int lower, int upper) {
        if (a.length==0) return 0;

        // calculate 1 time sum of whole array
        int sum = Arrays.stream(a).reduce((i, j) -> i+j).getAsInt();

        // root node is whole range
        Node node = new Node(a, 0, a.length-1, sum);
        return node.splitAndCount(lower, upper);
    }
}
