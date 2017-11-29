package com.leetcode2;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/graph-valid-tree/description/
 */
public class GraphValidTree {

    public static void main(String[] args) {
        GraphValidTree p = new GraphValidTree();

        //int n = 5; int[][] edges = {{0,1}, {0,2}, {0,3}, {1,4}};
        //int n = 5; int[][] edges = {{0,1}, {1,2}, {2,3}, {1,3}, {1,4}};
        //int n = 2; int[][] edges = {};
        int n = 1; int[][] edges = {};
        System.out.println(p.validTree(n, edges));
    }



    /**
     * Algorithm: A tree is a 1-connected-component acyclic graph (contains no circle). So A DFS an be used effectively
     * here to detect if there's any circle: on a current visiting node, if you see another visited nodes (adj matrix)
     * that's not the node you came from -> There's a circle in the graph -> not a tree
     */
    public boolean validTree(int n, int[][] edges) {
        if (n==0) return false;
        if (edges.length==0 && n >= 2) return false;

        int[][] adj = buildMatrix(n, edges);
        int[] visited = new int[n]; // default value = 0 == not yet visited

        int count = 0;
        for (int i=0; i<n; i++) if (visited[i]==0) {
            ++count;
            if (count > 1) return false;
            visited[i] = 1;
            boolean cyclic = dfsContainsCircle(-1, i, adj, visited);
            if (cyclic) return false;
        }

        return true;
    }

    private boolean dfsContainsCircle(int prev, int i, int[][] adj, int[] visited) {
        // dfs from node
        for (int k=0; k<adj[i].length; k++) if (adj[i][k] != -1) {
            int j = adj[i][k];
            if (visited[j]==1 && j != prev) return true; // seeing a strange visited node -> signal of a circle
            // else keep visiting
            if (visited[j]==0) {
                visited[j] = 1;
                boolean cyclic = dfsContainsCircle(i, j, adj, visited);
                if (cyclic) return true;
            }
        } else break;

        return false;
    }

    private int[][] buildMatrix(int n, int[][] edges) {
        int[][] adj = new int[n][n];
        for (int i=0; i<n; i++) {
            Arrays.fill(adj[i], -1);
        }
        int[] idx = new int[n];
        for (int i=0; i<edges.length; i++) {
            int[] e = edges[i];
            adj[e[0]] [idx[e[0]]++] = e[1];
            adj[e[1]] [idx[e[1]]++] = e[0];
        }
        return adj;
    }
}
