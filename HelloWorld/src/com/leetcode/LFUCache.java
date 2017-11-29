package com.leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * LFU Cache
 * https://leetcode.com/problems/lfu-cache/description/
 */
public class LFUCache {

    public static void main(String[] args) {
        LFUCache cache = new LFUCache( 2 /* capacity */ );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.get(3);       // returns 3.
        cache.put(4, 4);    // evicts key 1.
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
    }



    class Node {
        int freq;
        final Set<Integer> keys = new LinkedHashSet<>();

        Node prev = null;
        Node next = null;

        public Node moveKeyToNextNode(int key) {
            // first of all this freq node no longer contains this key
            this.keys.remove(key);
            // if this now contains no key, remove it
            removeMySelfIfEmptyKeys();

            // try look up the next freq + 1 node if exists or not
            if (this.next != null && this.next.freq == this.freq+1) {
                this.next.keys.add(key);
                return this.next;
            }

            // or else: has to create new node following this node
            Node node = new Node();
            node.freq = this.freq + 1;
            node.keys.add(key);
            // linked list operations
            node.next = this.next; node.prev = this;
            if (this.next != null) {
                this.next.prev = node;
            }
            this.next = node;
            return node;
        }

        private void removeMySelfIfEmptyKeys() {
            if (this.keys.isEmpty()) {
                if (this.prev != null) {
                    this.prev.next = this.next;
                }
                if (this.next != null) {
                    this.next.prev = this.prev;
                }
            }
        }

        public int removeFrontKey() {
            int frontKey = this.keys.iterator().next();
            this.keys.remove(frontKey);
            removeMySelfIfEmptyKeys();
            return frontKey;
        }

        // add this new key, frequency = 1
        public Node checkAndAddNewKey(int key) {
            if (this.freq == 1) {
                this.keys.add(key);
                return this;
            }

            // else: create new prev node with freq 1
            Node prevNode = new Node();
            prevNode.freq = 1;
            prevNode.keys.add(key);
            prevNode.next = this;
            this.prev = prevNode;
            return prevNode;
        }
    }

    Node head = null;  // Node head contains lowest frequency, e.g. 1 -> 2 -> 5 -> ...
    final int capacity;

    final Map<Integer, Node> keyToNode = new HashMap<>();
    final Map<Integer, Integer> keyToVal = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (keyToVal.containsKey(key)) {
            Node node = keyToNode.get(key);
            keyToNode.remove(key);
            Node nextNode = node.moveKeyToNextNode(key);
            if (head == node && head.keys.isEmpty()) {
                head = nextNode;
            }
            keyToNode.put(key, nextNode);
            System.out.println("returns " + keyToVal.get(key));
            return keyToVal.get(key);
        }

        System.out.println("returns -1");
        return -1;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value);
            Node node = keyToNode.get(key);
            keyToNode.remove(key);
            Node nextNode = node.moveKeyToNextNode(key);
            if (head == node && head.keys.isEmpty()) {
                head = nextNode;
            }
            keyToNode.put(key, nextNode);
            return;
        }

        // if a new key, remove if capacity is about to be reached
        if (keyToVal.size() == capacity) {
            int frontKey = head.removeFrontKey();
            if (head.keys.isEmpty()) {
                head = head.next;
            }
            keyToVal.remove(frontKey);
            keyToNode.remove(frontKey);
            System.out.println("evicts key " + frontKey);
        }

        // add a new key / node
        keyToVal.put(key, value);
        Node node = checkAndAddNewKey(key);
        if (node != head) { // signal of a new head
            head = node;
        }
        keyToNode.put(key, node);
    }

    private Node checkAndAddNewKey(int key) {
        if (head != null) {
            Node node = head.checkAndAddNewKey(key);
            return node;
        }
        // if head is null / first time
        head = new Node();
        head.freq = 1;
        head.keys.add(key);
        return head;
    }
}
