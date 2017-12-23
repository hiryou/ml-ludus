package com.leetcode3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/all-oone-data-structure/description/
 */
public class AllOoneDataStructure {

    public static void main(String[] args) {
        AllOoneDataStructure p = new AllOoneDataStructure();
        p.inc("hello");
        p.inc("hello");
        p.getMaxKey();
        p.getMinKey();
        p.inc("leet");
        p.getMaxKey();
        p.getMinKey();
    }



    final class Node {
        final Set<String> keys = new HashSet<>();
        final int val;
        Node next;
        Node prev;
        Node(int val) {
            this.val = val;
        }
    }


    final Map<String, Node> map = new HashMap<>();
    Node head = null;
    Node tail = null;

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if (!map.containsKey(key)) {
            Node node = addNewNodeValOne(key) ;
            map.put(key, node);
        } else {
            Node node = map.get(key);
            moveDownValOne(node, key);
        }
    }
    Node addNewNodeValOne(String key) {
        if (head != null && head.val==1) {
            head.keys.add(key);
            return head;
        }

        Node node = new Node(1); // this is the new head now
        node.keys.add(key);
        if (head==null) {
            head = tail = node;
            return head;
        }

        head.prev = node;
        node.next = head;
        return head = node;
    }
    void moveDownValOne(Node node, String key) {
        node.keys.remove(key);

        if (node.next != null && node.next.val == node.val+1) {
            node.next.keys.add(key);
            map.put(key, node.next);
        } else {
            boolean nodeIsTail = node == tail;
            Node next = new Node(node.val + 1);
            next.keys.add(key);
            map.put(key, next);
            if (node.next == null) {
                node.next = next;
                next.prev = node;
                if (nodeIsTail) tail = next;
            } else {
                next.next = node.next;
                next.prev = node;
                node.next.prev = next;
                node.next = next;
            }
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if (!map.containsKey(key)) return;

        Node node = map.get(key);
        if (node.val==1) {
            node.keys.remove(key);
            map.remove(key);
        } else {
            moveUpValOne(node, key);
        }
    }
    void moveUpValOne(Node node, String key) {
        node.keys.remove(key);

        if (node.prev != null && node.prev.val == node.val-1) {
            node.prev.keys.add(key);
            map.put(key, node.prev);
        } else {
            boolean nodeIsHead = node == head;
            Node prev = new Node(node.val - 1);
            prev.keys.add(key);
            map.put(key, prev);
            if (node.prev == null) {
                node.prev = prev;
                prev.next = node;
                if (nodeIsHead) head = prev;
            } else {
                prev.prev = node.prev;
                prev.next = node;
                node.prev.next = prev;
                node.prev = prev;
            }
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if (tail==null) return "";
        Node cur = tail;
        while (cur != null && cur.keys.isEmpty()) cur = cur.prev;

        if (cur==null) return "";
        return cur.keys.iterator().next();
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (head==null) return "";
        Node cur = head;
        while (cur != null && cur.keys.isEmpty()) cur = cur.next;

        if (cur==null) return "";
        return cur.keys.iterator().next();
    }
}
