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


    // solution of in-place: reflect a cell to the top edge and left edge
    public void setZeroes(int[][] s) {
        if (s==null || s.length==0 || s[0].length==0) return;

        // first check if top row and left col contains any zero or not
        boolean top = false;
        for (int j=0; j<s[0].length; j++) if (s[0][j]==0) {
            top = true;
            break;
        }
        boolean left = false;
        for (int i=0; i<s.length; i++) if (s[i][0]==0) {
            left = true;
            break;
        }

        // now do reflect
        for (int i=1; i<s.length; i++) for (int j=1; j<s[0].length; j++) if (s[i][j]==0) {
            s[i][0] = 0;
            s[0][j] = 0;
        }
        // now set
        for (int i=1; i<s.length; i++) if (s[i][0]==0) setRow(s, i);
        for (int j=1; j<s[0].length; j++) if (s[0][j]==0) setCol(s, j);
        // special cases: row 0 and col 0
        if (s[0][0]==0) {
            setRow(s, 0);
            setCol(s, 0);
        } else {
            if (top) setRow(s, 0);
            if (left) setCol(s, 0);
        }
    }
    void setRow(int[][] s, int row) {
        for (int j=0; j<s[0].length; j++) s[row][j] = 0;
    }
    void setCol(int[][] s, int col) {
        for (int i=0; i<s.length; i++) s[i][col] = 0;
    }
}
