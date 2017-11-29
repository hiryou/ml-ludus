package com.leetcode2;

import java.util.Stack;

/**
 * https://leetcode.com/problems/symmetric-tree/description/
 */
public class SymmetricTree {
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * Solution: use 2 stack: left pointing left->right, right pointing left<-right, popping and checking
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        if (!AreEqual(root.left, root.right)) return false;
        Stack<TreeNode> left = new Stack<>();
        if (root.left != null) {
            left.push(root.left);
        }
        Stack<TreeNode> right = new Stack<>();
        if (root.right != null) {
            right.push(root.right);
        }

        while (!left.isEmpty()) {
            // pop assumes equal, add checks for equal before adding
            TreeNode a = left.pop();
            TreeNode b = right.pop();

            if (!AreEqual(a.right, b.left)) return false;
            if (a.right != null) {
                left.push(a.right);
                right.push(b.left);
            }

            if (!AreEqual(a.left, b.right)) return false;
            if (a.left != null) {
                left.push(a.left);
                right.push(b.right);
            }
        }

        return true;
    }

    private boolean AreEqual(TreeNode a, TreeNode b) {
        if ((a==null) != (b==null)) return false;
        if ((a==null) && (b==null)) return true;
        return a.val == b.val;
    }
}
