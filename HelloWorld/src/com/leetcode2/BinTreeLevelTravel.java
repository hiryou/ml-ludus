package com.leetcode2;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BinTreeLevelTravel {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new LinkedList<>();

        List<List<TreeNode>> result = new LinkedList<>();

        LinkedList<TreeNode> first = new LinkedList<>();
        result.add(first);
        first.add(root);

        LinkedList<TreeNode> cur = first;
        while (!cur.isEmpty()) {
            LinkedList next = new LinkedList<>();
            for (TreeNode node: cur) {
                if (node.left != null) next.add(node.left);
                if (node.right != null) next.add(node.right);
            }

            if (!next.isEmpty()) result.add(next);
            cur = next;
        }

        return result.stream()
                .map(listNode -> listNode.stream().map(
                        node -> node.val)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
