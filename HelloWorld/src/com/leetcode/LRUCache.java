package com.leetcode;

import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * LRU Cache
 * https://leetcode.com/problems/lru-cache/description/
 */
public class LRUCache {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache( 2 /* capacity */ );

        cache.put(2, 1);
        cache.put(3, 2);
        cache.get(3);
        cache.get(2);
        cache.put(4, 3);
        cache.get(2);
        cache.get(3);
        cache.get(4);
    }


    @RequiredArgsConstructor
    private static class Node {
        final int key;

        int value;
        Node prev = null;
        Node next = null;
    }

    private final int capacity;

    private final Map<Integer, Node> keyToNode = new HashMap<>();
    private Node head = null;
    private Node tail = null;

    public LRUCache(int capacity) {
        if (capacity <= 0) throw new RuntimeException("Capacity must be > 0");
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key)) {
            System.out.println("returns " + -1 + " (not found)");
            return -1;
        }

        Node node = keyToNode.get(key);
        int value = node.value;
        moveExistingNodeToHead(node);

        System.out.println("returns " + value);
        return value;
    }

    public void put(int key, int value) {
        if (keyToNode.containsKey(key)) {
            Node node = keyToNode.get(key);
            node.value = value;
            moveExistingNodeToHead(node);
            return;
        }

        Node newNode = new Node(key);
        newNode.value = value;
        addNewNodeToHead(newNode);
        keyToNode.put(key, newNode);

        if (keyToNode.size() > capacity) {
            Node tailNode = removeTailAndGet();
            keyToNode.remove(tailNode.key);
            System.out.println("evicts key " + tailNode.value);
        }
    }

    /**
     * Add a new node to the head of the linked list
     */
    private Node addNewNodeToHead(Node node) {
        if ((head==null) != (tail==null)) throw new RuntimeException("(head==null) != (tail==null): should not happen!");

        if (head == null && tail == null) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }

        return head;
    }

    private Node removeTailAndGet() {
        if (head == null && tail == null) return null;
        if (head==null ^ tail==null) throw new RuntimeException("head==null ^ tail==null: shouldn't happen!");

        Node tailNode = tail;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        // remove access of old tail to the linked list just for safety
        tailNode.prev = null;
        return tailNode;
    }

    /**
     * Move an existing node to the head of the list
     */
    private void moveExistingNodeToHead(Node node) {
        if (head==null || tail==null) throw new RuntimeException("(head==null) or (tail==null): shouldn't happen!");

        if (head != tail) {
            if (node == head) return;

            if (node == tail) {
                Node tailNode = tail;
                tail.prev.next = null;
                tail = tail.prev;

                tailNode.prev = null;
                tailNode.next = head;
                head.prev = tailNode;
                head = tailNode;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;

                node.prev = null;
                node.next = head;
                head.prev = node;
                head = node;
            }
        }
    }
}
