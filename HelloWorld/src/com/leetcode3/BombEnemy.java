package com.leetcode3;

import com.leetcode2.BenchMark;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/bomb-enemy/discuss/
 * TODO Convert to DP solution. My solution runs slow!
 */
public class BombEnemy {

    public static void main(String[] args) {
        char[][] a = {{'0','E','0','0'},{'E','0','W','E'},{'0','E','0','0'}};
        BombEnemy p = new BombEnemy();
        BenchMark.run(() -> p.maxKilledEnemies(a));
    }


    // solution: DP track max segment row, track max segment col
    public int maxKilledEnemies(char[][] a) {
        if (a==null || a.length==0 || a[0].length==0) return 0;

        int M = a.length;
        int N = a[0].length;

        int[][] rows = new int[M][N];
        for (int i=0; i<M; i++) {
            int count = 0;
            for (int j=0; j<N; j++) {
                char c = a[i][j];
                if (c=='E') ++count;
                if (j==N-1 || a[i][j]=='W') {
                    if (a[i][j]=='W') {
                        rows[i][j] = 0;
                        rows[i][j-1] = count;
                    }
                    else rows[i][j] = count;
                    count = 0;
                }
            }
            // loop back
            for (int j=N-2; j>=0; j--) if (a[i][j] != 'W') {
                rows[i][j] = Math.max(rows[i][j], rows[i][j+1]);
            }
        }

        int[][] cols = new int[M][N];
        for (int j=0; j<N; j++) {
            int count = 0;
            for (int i=0; i<M; i++) {
                char c = a[i][j];
                if (c=='E') ++count;
                if (i==M-1 || a[i][j]=='W') {
                    if (a[i][j]=='W') {
                        cols[i][j] = 0;
                        cols[i-1][j] = count;
                    }
                    else cols[i][j] = count;
                    count = 0;
                }
            }
            // loop back
            for (int i=M-2; i>=0; i--) if (a[i][j] != 'W') {
                cols[i][j] = Math.max(cols[i][j], cols[i+1][j]);
            }
        }

        int max = 0;
        for (int i=0; i<M; i++) for (int j=0; j<N; j++) if (a[i][j] == '0') {
            int val = rows[i][j] + cols[i][j];
            max = val > max ?val :max ;
        }
        return max;
    }
}
