package com.leetcode2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/trapping-rain-water-ii/description/
 * My solution: Go from the outside wall, shrinking to the inside wall
 */
public class TrappingRainWater2 {

    public static void main(String[] args) {
        TrappingRainWater2 p = new TrappingRainWater2();

        /*
        int[][] a = {
                {1,4,3,1,3,2},
                {3,2,1,3,2,4},
                {2,3,3,2,3,1}
        };
        */
        int[][] a = {
                {1,3,5,6,4,7,2},
                {5,9,2,5,1,2,2},
                {1,1,5,1,1,2,3},
                {5,6,1,2,1,4,5}
        };
        BenchMark.run(() -> p.trapRainWater(a));
    }

    class MyCell {
        int i; int j; int h;
        MyCell(int i, int j, int h) {
            this.i = i;
            this.j = j;
            this.h = h;
        }
    }

    int M; int N;

    public int trapRainWater(int[][] a) {
        if (a.length <= 2 || a[0].length <= 2) return 0;

        M = a.length; N = a[0].length;

        PriorityQueue<MyCell> q = new PriorityQueue<>(Comparator.comparingInt(c -> c.h));

        // first put the border to a priority queue
        boolean[][] visited = new boolean[M][N];
        addBorderToQueue(a, q, visited);

        // from smallest visited cell / border, get the neighbor unvisited cell
        int water = 0;
        while (!q.isEmpty()) {
            MyCell c = q.poll();
            water += checkNeighbors(a, q, c, visited);
        }

        return water;
    }

    private int checkNeighbors(int[][] a, PriorityQueue<MyCell> q, MyCell c, boolean[][] visited) {
        int water = 0;
        List<MyCell> ns = getUnvisitedNeighbors(a, c, visited);
        for (MyCell nc: ns) {
            water += decideWater(nc, c);
            nc.h = decideHeight(nc, c);
            q.add(nc);
            visited[nc.i][nc.j] = true;
        }
        return water;
    }

    private int decideHeight(MyCell nc, MyCell c) {
        if (nc.h >= c.h) return nc.h;
        return c.h;
    }

    private int decideWater(MyCell nc, MyCell c) {
        if (nc.h >= c.h) return 0;
        return c.h - nc.h;
    }

    private List<MyCell> getUnvisitedNeighbors(int[][] a, MyCell c, boolean[][] visited) {
        List<MyCell> cs = new ArrayList<>();

        int[][] dis = {{-1, 0}, {+1, 0}, {0, -1}, {0, +1}};
        for (int[] di: dis) {
            int i = c.i + di[0];
            int j = c.j + di[1];
            if (0<=i && i<M && 0<=j && j<N && !visited[i][j]) cs.add(new MyCell(i, j, a[i][j]));
        }

        return cs;
    }

    private void addBorderToQueue(int[][] a, PriorityQueue<MyCell> q, boolean[][] visited) {
        for (int j=0; j<N; j++) {
            q.add(new MyCell(0, j, a[0][j]));
            visited[0][j] = true;

            q.add(new MyCell(M-1, j, a[M-1][j]));
            visited[M-1][j] = true;
        }

        for (int i=1; i<=M-2; i++) {
            q.add(new MyCell(i, 0, a[i][0]));
            visited[i][0] = true;

            q.add(new MyCell(i, N-1, a[i][N-1]));
            visited[i][N-1] = true;
        }
    }
}
