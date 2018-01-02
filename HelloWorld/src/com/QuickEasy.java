package com;

import com.leetcode2.BenchMark;

import java.util.*;
import java.util.stream.Collectors;

public class QuickEasy {

    public static void main(String[] args) {
        QuickEasy p = new QuickEasy();

        String s = "hit";
        String e = "cog";
        List<String> ws = Arrays.asList("hot","dot","dog","lot","log","cog");
        String ss = "abcabcabcabc";
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
        for (int x: a) {
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
        public TreeNode(int x) { val = x; }

        static int maxGap = Integer.MIN_VALUE;
        static Integer lastNum = null;

        void addNew(int x) {
            if (x==val) return;

            if (x < val) {
                if (left==null) {
                    left = new TreeNode(x);
                    return;
                }
                left.addNew(x);
                return;
            }

            // when right
            if (right==null) {
                right = new TreeNode(x);
                return;
            }
            right.addNew(x);
        }

        void inOrder() {
            if (left != null) left.inOrder();

            if (lastNum != null) {
                maxGap = val - lastNum > maxGap ?val - lastNum :maxGap ;
            }
            lastNum = val;

            if (right != null) right.inOrder();
        }
    }

    public static class ListNode {
        public int val;
        public ListNode right;
        public ListNode next;
        public ListNode(int x) { val = x; }
    }

    static class RandomListNode {
        int label;
        RandomListNode next, random;
        RandomListNode(int x) { this.label = x; }
    }

    static public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }


    public int findPairs(int[] a, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int x: a) if (!map.containsKey(x) || k==0) {
            if (k==0 && (!map.containsKey(x) || map.get(x) <= 1 )) {
                map.put(x, map.getOrDefault(x, 0) + 1);
                if (map.get(x) >= 2) ++count;
            } else if (k > 0) {
                if (map.containsKey(x-k)) ++count;
                if (map.containsKey(x+k)) ++count;
                map.put(x, 1);
            }

        }
        return count;
    }

}
