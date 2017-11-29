package com.leetcode2;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/description/
 */
public class MergeKSortedLists {

    public static void main(String[] args) {
        MergeKSortedLists p = new MergeKSortedLists();

        //ListNode[] list = {linked(1,2,4), linked(3,4,5), linked(2,3,6)};
        ListNode[] list = {linked(1), linked(), linked()};
        BenchMark.run(() -> toString(p.mergeKLists(list)));
    }

    static String toString(ListNode node) {
        ListNode cur = node;
        StringBuilder b = new StringBuilder();
        while (cur != null) {
            b.append(cur.val + ", ");
            cur = cur.next;
        }
        return b.toString();
    }

    static ListNode linked(int... vals) {
        ListNode root = new ListNode(0);  // convenient node
        ListNode cur = root;
        for (int val: vals) {
            cur.next = new ListNode(val);
            cur = cur.next;
        }
        return root.next;
    }



    static public class ListNode {
        int val;
        ListNode next = null;
        ListNode(int x) { val = x; }
    }

    public ListNode mergeKLists(ListNode[] l) {
        PriorityQueue<ListNode> q = new PriorityQueue<>((n1, n2) -> {
            if (n1.val < n2.val) return -1;
            if (n1.val > n2.val) return +1;
            return 0;
        });  // min heap

        for (int i=0; i<l.length; i++) {
            if (l[i] != null)
                q.add(l[i]);
        }

        ListNode root = new ListNode(0);  // fake root node
        ListNode cur = root;
        while (!q.isEmpty()) {
            ListNode node = q.poll();
            if (node.next != null) q.add(node.next);
            cur.next = node;
            cur = node;
        }

        return root.next;
    }
}
