package com;

import com.leetcode2.BenchMark;

import java.util.HashSet;
import java.util.Set;

public class QuickEasy {

    public static void main(String[] args) {
        QuickEasy p = new QuickEasy();

        int[] a = {1,3,100};
        //BenchMark.run(() -> p.maximumGap(a));
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

    public class ListNode {
        public int val;
        public ListNode right;
        public ListNode next;
        public ListNode(int x) { val = x; }
    }


    public int minTotalDistance(int[][] grid) {
        if (grid.length==0 || grid[0].length==0) return 0;

        long sumx = 0, sumy = 0;
        long count = 0;
        for (int i=0; i<grid.length; i++) for (int j=0; j<grid[0].length; j++) if (grid[i][j]==1) {
            sumx += i;
            sumy += j;
            ++count;
        }
        int x = (int)(sumx/count);
        int y = (int)(sumy/count);
    }
}
