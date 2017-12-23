package com.leetcode3;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * https://leetcode.com/problems/walls-and-gates/description/
 * TODO Correct all answers
 */
public class WallsAndGates {

    class Cell {
        int i = -1; int j = -1;
        Cell fromId(int id) {
            i = id / N;
            j = id % N;
            return this;
        }
    }

    int M, N;
    Cell cell = new Cell();

    public void wallsAndGates(int[][] a) {
        if (a==null || a.length==0 | a[0].length==0) return;
        M = a.length;
        N = a[0].length;

        boolean[] visited = new boolean[M*N];
        for (int i=0; i<M; i++) for (int j=0; j<N; j++) if (a[i][j]==0) {
            doBfs(a, i, j, visited);
            Arrays.fill(visited, false);
        }
    }

    void doBfs(int[][] a, int x, int y, boolean[] visited) {
        Deque<Integer> q = new ArrayDeque<>();
        q.add(cellId(x, y));

        final int[][] di = {{-1,0}, {+1,0}, {0,-1}, {0,+1}};
        while (!q.isEmpty()) {
            int cellId = q.removeFirst();
            cell.fromId(cellId);
            for (int k=0; k<4; k++) {
                int i = cell.i + di[k][0];
                int j = cell.j + di[k][1];
                if (inBound(i, j) && isEmptyRoom(a, i, j) && !visited[cellId(i, j)]) {
                    a[i][j] = Math.min(a[i][j], a[cell.i][cell.j] + 1);
                }
                q.addLast(cellId(i, j));
            }

            // later
            visited[cellId] = true;
        }
    }

    boolean isEmptyRoom(int[][] a, int i, int j) {
        return a[i][i] != 0 && a[i][j] != -1;
    }

    int cellId(int i, int j) {
        return i * N + j;
    }

    boolean inBound(int i, int j) {
        return 0 <= i && i < M && 0 <= j && j < N;
    }
}
