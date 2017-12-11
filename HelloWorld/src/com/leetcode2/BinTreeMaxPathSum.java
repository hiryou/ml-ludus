package com.leetcode2;

import static com.QuickEasy.*;

/**
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
 */
public class BinTreeMaxPathSum {

    public static void main(String[] args) {
        BinTreeMaxPathSum p = new BinTreeMaxPathSum();

        TreeNode n = new TreeNode(1);
        n.left = new TreeNode(2);
        n.right = new TreeNode(3);

        BenchMark.run(() -> p.maxPathSum(n));
    }


    int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPath(root);
        return max;
    }

    // this returns the max path from node down its children
    int maxPath(TreeNode node) {
        // stop condition: null node
        if (node == null) return 0;

        int maxLeft = Math.max(0, maxPath(node.left));
        int maxRight = Math.max(0, maxPath(node.right));

        int myPath = maxLeft + node.val + maxRight;
        max = myPath > max ?myPath :max ;

        return Math.max(0, Math.max(maxLeft, maxRight)) + node.val;
    }
}
