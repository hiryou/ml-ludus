package com.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-tree-postorder-traversal/description/
 */
public class BinTreePostOrder {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        TreeNode _1 = new TreeNode(1);
        TreeNode _2 = new TreeNode(2);
        TreeNode _3 = new TreeNode(3);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);
        TreeNode _6 = new TreeNode(6);
        TreeNode _7 = new TreeNode(7);

        _1.left = _2;
        _1.right = _3;

        _2.left = _4;
        _2.right = _5;

        _3.left = _6;
        _3.right = _7;

        System.out.println(new BinTreePostOrder().postorderTraversal(_1));
    }

    // using LinkedList
    class Node {
        final TreeNode treeNode;
        final Node parent;

        Node prev = null;
        Node next = null;

        public Node(TreeNode tNode, Node parent) {
            this.treeNode = tNode;
            this.parent = parent;
        }
    }

    class MyList {
        Node head = null;
        Node tail = null;

        MyList(Node first) {
            head = first;
            tail = first;
        }

        List<Integer> asIntegerList() {
            List<Integer> result = new ArrayList<>();
            Node cur = head;
            while (cur != null) {
                result.add(cur.treeNode.val);
                cur = cur.next;
            }

            return result;
        }

        // add this new node to the immediate left side of parent
        void addToTheLeft(Node node, Node parent) {
            if (head == tail) {
                if (head != parent) throw new RuntimeException("head == tail != parent: should not happen");

                head = node;
                head.next = tail;
                tail.prev = head;
            }
            else {
                if (parent == head) {
                    node.next = head;
                    head.prev = node;
                    head = node;
                } else {
                    Node prevNode = parent.prev;

                    prevNode.next = node;
                    node.next = parent;
                    parent.prev = node;
                    node.prev = prevNode;
                }
            }
        }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<>();

        Node rootNode = new Node(root, null);
        MyList myList = new MyList(rootNode);

        List<Node> nowLevel = new ArrayList<>();
        if (root.left != null) nowLevel.add(new Node(root.left, rootNode));
        if (root.right != null) nowLevel.add(new Node(root.right, rootNode));

        while (nowLevel.size() > 0) {
            List<Node> nextLevel = new ArrayList<>();

            for (Node node: nowLevel) {
                myList.addToTheLeft(node, node.parent);

                if (node.treeNode.left != null) nextLevel.add(new Node(node.treeNode.left, node));
                if (node.treeNode.right != null) nextLevel.add(new Node(node.treeNode.right, node));
            }

            // later
            nowLevel = nextLevel;
        }

        // the myList contains the correct post-order
        return myList.asIntegerList();
    }
}
