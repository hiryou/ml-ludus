package com.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/description/
 */
public class ConnectedComponentsUndirectedGraph {

    public static void main(String[] args) {
        ConnectedComponentsUndirectedGraph p = new ConnectedComponentsUndirectedGraph();

        int n = 5; int[][] edges = {{0,1}, {1,2}, {3,4}};
        //int n = 5; int[][] edges = {{0,1}, {1,2}, {2,3}, {3,4}, {0,3}, {1,3}, {1,4}};
        System.out.println(p.countComponents(n, edges));
    }


    public int countComponents(int n, int[][] edges) {
        if (n == 0) return 0;
        if (edges.length == 0) return n;

        //return dfsSolution(n, edges);
        return islandUnionSolution(n, edges);
    }

    private int islandUnionSolution(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i=0; i<n; i++) parent[i] = i;

        int count = n;
        for (int i=0; i<edges.length; i++) {
            int a = edges[i][0], b = edges[i][1];
            int ra = findRoot(a, parent);
            int rb = findRoot(b, parent);
            if (ra != rb) {
                --count;
                parent[rb] = ra;
            }
        }
        return count;
    }

    private int findRoot(int a, int[] parent) {
        while (parent[a] != a) {
            a = parent[a];
        }
        return a;
    }


    /**
     * Simple: DFS
     */
    private int dfsSolution(int n, int[][] edges) {
        Map<Integer, Set<Integer>> adj = buildAdj(n, edges);

        int[] visited = new int[n]; // default value = 0 == not visited yet
        int count = 0;
        for (int i=0; i<visited.length; i++) if (visited[i] == 0) {
            ++count;
            doDFS(i, adj, visited);
        }

        return count;
    }

    private void doDFS(int i, Map<Integer, Set<Integer>> adj, int[] visited) {
        if (visited[i] == 1) return;
        visited[i] = 1;
        for (int j: adj.get(i)) {
            doDFS(j, adj, visited);
        }
    }

    private Map<Integer,Set<Integer>> buildAdj(int n, int[][] edges) {
        Map<Integer,Set<Integer>> result = new HashMap<>();
        for (int i=0; i<n; i++) {
            result.put(i, new HashSet<>());
        }
        for (int i=0; i<edges.length; i++) {
            int a = edges[i][0], b = edges[i][1];
            result.get(a).add(b);
            result.get(b).add(a);
        }
        return result;
    }
}
