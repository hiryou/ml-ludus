package com;

import com.leetcode2.BenchMark;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class QuickEasy {

    public static void main(String[] args) {
        QuickEasy p = new QuickEasy();

        String s = "hit";
        String e = "cog";
        String ss = "abcabcabcabc";
        List<String> ws = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        //BenchMark.run(() -> p.myAtoi("  -0012a42"));

        /*
        int x = -8;
        System.out.println(x >> 0);
        System.out.println(x >> 1);
        System.out.println(x >> 2);
        System.out.println(x >> 3);

        System.out.println((x >> 10) & 1);
        System.out.println(x & (1 << 31));
        */


    }

    private static RandomListNode[] createLinkedList(int[] a) {
        RandomListNode[] result = new RandomListNode[a.length];
        int idx = 0;

        RandomListNode res = new RandomListNode(-1);
        RandomListNode cur = res;
        for (int x : a) {
            cur.next = new RandomListNode(x);
            result[idx++] = cur.next;
            cur = cur.next;
        }

        return result;
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }

        static int maxGap = Integer.MIN_VALUE;
        static Integer lastNum = null;

        void addNew(int x) {
            if (x == val) return;

            if (x < val) {
                if (left == null) {
                    left = new TreeNode(x);
                    return;
                }
                left.addNew(x);
                return;
            }

            // when right
            if (right == null) {
                right = new TreeNode(x);
                return;
            }
            right.addNew(x);
        }

        void inOrder() {
            if (left != null) left.inOrder();

            if (lastNum != null) {
                maxGap = val - lastNum > maxGap ? val - lastNum : maxGap;
            }
            lastNum = val;

            if (right != null) right.inOrder();
        }
    }

    public static class ListNode {
        public int val;
        public ListNode right;
        public ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }

    static class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int x) {
            this.label = x;
        }
    }

    static public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }




    // solution: dynamic programming
    public int scheduleCourse(int[][] cs) {
        if (cs==null) return 0;
        int n = cs.length;
        if (n==0) return 0;

        // first sort all course by deadline date - time-spent day count
        Arrays.sort( cs, Comparator.comparing((int[] c) -> c[1]) );

        // 2 auxiliary arrays
        int[] st = new int[n];
        int[] count = new int[n];
        int max = 1;
        for (int i=0; i<n; i++) {
            st[i] += cs[i][0]; // if the course is just taken by itself alone
            count[i] = 1;
            for (int j=0; j<i; j++) if (st[j]+cs[i][0] <= cs[i][1]) { // if stack-able courses
                st[j] += cs[i][0];
                count[j] += 1;
                max = count[j] > max ?count[j] :max ;
            }
        }

        return max;
    }



}
