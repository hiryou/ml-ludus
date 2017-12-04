package com;

import com.geeksforgeeks.BenchMark;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class QuickEasy {

    public static void main(String[] args) {
        QuickEasy p = new QuickEasy();

        int[] a = {1,1,2147483647};
        //BenchMark.run(() -> p.minMoves(a));
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public class ListNode {
        int val;
        ListNode right;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public int maxProfit(int[] a) {
        if (a.length <= 1) return 0;
        int n = a.length;

        // loop left to right
        // also track for the global max in case 1 transaction is better
        int[] l = new int[n];
        int minp = a[0];
        int max1 = Integer.MIN_VALUE;
        for (int i=0; i<a.length; i++) {
            int val = a[i] - minp;
            max1 = val > max1 ?val :max1 ;
            l[i] = max1;
            minp = a[i] < minp ?a[i] :minp ;
        }

        // loop right to left
        int[] r = new int[n];
        int maxp = a[n-1];
        int max2 = Integer.MIN_VALUE;
        for (int i=n-1; i>=0; i--) {
            int val = maxp - a[i];
            max2 = val > max2 ?val :max2 ;
            r[i] = max2;
            maxp = a[i] > maxp ?a[i] :maxp ;
        }

        // if 2 transactions
        int _2trans = Integer.MIN_VALUE;
        for (int i=1; i<=n-3; i++) {
            int val = l[i] + r[i+1];
            _2trans = val > _2trans ?val :_2trans ;
        }

        // if 1 transaction
        int _1trans = l[n-1];

        return _2trans > _1trans ?_2trans :_1trans ;
    }
}
