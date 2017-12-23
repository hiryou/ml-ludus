package com.leetcode3;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
 * TODO This priority queue solutions actually runs much slower than simly DFS from every cell
 */
public class LongestIncreasingPathMatrix {

    int M, N;

    class Node {
        final int i;
        final int j;
        int score = 1;
        Node (int i, int j) {
            this.i = i;
            this.j = j;
        }
        int cellId() {
            return i * N + j;
        }
    }

    public int longestIncreasingPath(int[][] a) {
        if (a==null || a.length==0 || a[0].length==0) return 0;
        M = a.length; N = a[0].length;

        // map from cellId -> actual node
        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(x -> a[x.i][x.j]));
        Node[] map  = new Node[M*N];
        for (int i=0; i<M; i++) for (int j=0; j<N; j++) {
            Node node = new Node(i, j);
            q.add(node);
            map[node.cellId()] = node;
        }

        int[][] dirs = {{-1,0}, {+1,0}, {0,-1}, {0,+1}};
        int max = 1;
        boolean[] visited = new boolean[M*N];
        while (!q.isEmpty()) {
            Node top = q.remove();
            max = top.score > max ?top.score :max ;

            for (int[] dir: dirs) {
                int x = top.i + dir[0];
                int y = top.j + dir[1];
                int nextId = getCellId(x, y);
                if (nextId != -1 && !visited[nextId]) {
                    Node next = map[nextId];
                    if (a[next.i][next.j] > a[top.i][top.j]) {
                        next.score = Math.max(next.score, top.score + 1);
                    }
                }
            }

            visited[top.cellId()] = true;
        }

        return max;
    }

    int getCellId(int i, int j) {
        if (i < 0 || i >= M || j < 0 || j >= N) return -1;
        return i * N + j;
    }
}
