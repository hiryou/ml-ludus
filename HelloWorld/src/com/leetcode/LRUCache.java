package com.leetcode;

import lombok.RequiredArgsConstructor;

import java.util.*;

public class LRUCache {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }


    @RequiredArgsConstructor
    private static class Node {
        final int key;

        int value;
        Node prev = null;
        Node next = null;
    }

    private final int capacity;

    private final Map<Integer, Node> cache = new HashMap<>();
    private Node head = null;
    private Node tail = null;

    public LRUCache(int capacity) {
        if (capacity <= 0) throw new RuntimeException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            System.out.println("returns " + -1 + " (not found)");
            return -1;
        }

        Node node = cache.get(key);
        int value = node.value;
        moveNodeToHead(node);

        System.out.println("returns " + value);
        return value;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            cache.get(key).value = value;
        } else {
            Node newNode = addNewNodeToTail(key, value);
            cache.put(key, newNode);
        }
        moveNodeToHead(cache.get(key));

        if (cache.size() > capacity) {
            Node tailNode = removeTailAndGet();
            cache.remove(tailNode.key);
            System.out.println("evicts key " + tailNode.value);
        }
    }

    /**
     * Add a new node to the end of the linked list
     */
    private Node addNewNodeToTail(int key, int value) {
        Node node = new Node(key);
        node.value = value;

        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;

        return tail;
    }

    private Node removeTailAndGet() {
        if (head==null || tail==null) throw new RuntimeException("head==null || tail==null: shouldn't happen!");

        Node tailNode = tail;
        if (head == tail) {
            head = tail = null;
        } else {
            tail.prev.next = null;
            tail = tail.prev;
        }

        return tailNode;
    }

    /**
     * Move this node to the head of the list
     */
    private void moveNodeToHead(Node node) {
        if ((head==null) != (tail==null)) throw new RuntimeException("(head==null) != (tail==null): shouldn't happen!");

        if (head == null && tail == null) {
            head = tail = node;
        } else if (head != tail) {
            Node tailNode = tail;
            tail.prev.next = null;
            tail = tail.prev;

            tailNode.prev = null;
            tailNode.next = head;
            head.prev = tailNode;
            head = tailNode;
        }
    }
}
