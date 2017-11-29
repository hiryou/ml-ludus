package com.leetcode;

import com.sun.xml.internal.ws.api.message.HeaderList;

/**
 * https://leetcode.com/problems/dungeon-game/description/
 * TODO Need to review, did not pass all test cases
 */
public class DungeonGame {

    public static void main(String[] args) {
        /*
        int[][] dungeon = {
                {-2, -3, 3},
                {-5, -10, 1},
                {10, 30, -5}
        };
        */

        int[][] dungeon = {
                {1, -3, 3},
                {0, -2, 0},
                {-3, -3, -3}
        };

        DungeonGame p = new DungeonGame();
        System.out.println(p.calculateMinimumHP(dungeon));
    }


    /**
     * Use 2 matrix:
     * health[i][j]: current health when you step on cell [i][j]
     * best[i][j]: highest critical health level on some path up until cell [i][j]
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int M = dungeon.length, N = dungeon[0].length;
        if (M==0 || N==0) return 0;

        int[][] health = new int[M][N];
        int[][] best = new int[M][N];

        // top left cell
        health[0][0] = dungeon[0][0];
        best[0][0] = dungeon[0][0];
        // first row: can only step rightward
        for (int j=1; j<N; j++) {
            health[0][j] = health[0][j-1] + dungeon[0][j];
            best[0][j] = Math.min(health[0][j], best[0][j-1]);
        }
        // first col: can only step downward
        for (int i=1; i<M; i++) {
            health[i][0] = health[i-1][0] + dungeon[i][0];
            best[i][0] = Math.min(health[i][0], best[i-1][0]);
        }

        // now fill up the table
        for (int i=1; i<M; i++) {
            for (int j=1; j<N; j++) {
                // You can arrive at this cell either from above cell or left cell. You should choose to come here from
                // the cell that has higher critical health level
                if (best[i-1][j] > best[i][j-1]) {  // above cell
                    health[i][j] = health[i-1][j] + dungeon[i][j];
                    best[i][j] = Math.min(health[i][j], best[i-1][j]);
                } else {  // left cell
                    health[i][j] = health[i][j-1] + dungeon[i][j];
                    best[i][j] = Math.min(health[i][j], best[i][j-1]);
                }
            }
        }

        // if bottom right cell is -a, return (a+1)
        int bestCritical = best[M-1][N-1];
        int initHealth = bestCritical <= 0 ?Math.abs(bestCritical)+1 :1;

        return Math.max(1, initHealth);
    }
}
