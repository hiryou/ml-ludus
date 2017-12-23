package com.leetcode3;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/pacific-atlantic-water-flow/description/
 * TODO Correct all answers
 */
public class PacificAtlanticWaterFlow {

    int M; int N;

    public List<int[]> pacificAtlantic(int[][] a) {
        List<int[]> res = new ArrayList<>();
        if (a==null || a.length==0 || a[0].length==0) return res;

        M = a.length; N = a[0].length;
        byte[][] b = new byte[M][N];
        b[M-1][0] = 3;
        b[0][N-1] = 3;

        // Atlantic ocean first
        byte ocean = 2;
        for (int i=M-1; i>=0; i--) for (int j=N-1; j>=0; j--) {
            if (canFlow(a, b, i, j, ocean)) b[i][j] |= ocean;
        }
        // then Pacific
        ocean = 1;
        int bothOcean = 3;
        for (int i=0; i<M; i++) for (int j=0; j<N; j++) {
            if (canFlow(a, b, i, j, ocean)) b[i][j] |= ocean;
            if (b[i][j] == bothOcean) res.add(new int[] {i, j});
        }

        return res;
    }

    boolean canFlow(int[][] a, byte[][] b, int i, int j, byte ocean) {
        if ( ((b[i][j]) & ocean) > 0 ) return true;
        if (ocean==2 && (i==M-1 || j==N-1)) return true;
        if (ocean==1 && (i==0 || j==0)) return true;

        int[][] dirs = {{-1,0}, {+1,0}, {0,-1}, {0,+1}};

        for (int[] dir: dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            if (inBound(x, y) && a[x][y] <= a[i][j] && (b[x][y] & ocean) > 0) return true;
        }

        return false;
    }

    boolean inBound(int x, int y) {
        return 0 <= x && x < M && 0 <= y && y < N;
    }
}
