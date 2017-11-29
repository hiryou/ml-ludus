package com.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/n-queens-ii/description/
 */
public class NQueens {

    public static void main(String[] args) {
        int N = 8;
        int count = new NQueens().totalNQueens(N);
        System.out.println(count);
    }

    class TotalCount {
        int count = 0;
        void increment() {
            ++count;
        }
    }

    public int totalNQueens(int n) {
        Set<Integer> mainDiag = new HashSet<>();
        Set<Integer> otherDiag = new HashSet<>();

        Map<Integer, Boolean> trackCols = new HashMap<>();
        for (int i=0; i<n; i++) trackCols.put(i, true);

        TotalCount totalCount = new TotalCount();
        recursivelyPlaceQueens(0, n, trackCols,mainDiag, otherDiag, totalCount);

        return totalCount.count;
    }

    private void recursivelyPlaceQueens(
            int row, int chessSize,
            Map<Integer, Boolean> trackCols,
            Set<Integer> mainDiag, Set<Integer> otherDiag,
            TotalCount totalCount) {
        if (row == chessSize) {
            totalCount.increment();
            return;
        }

        for (int col: trackCols.keySet()) if (trackCols.get(col)) {
            if (!mainDiag.contains(row - col) && !otherDiag.contains(row + col)) {
                // place it
                trackCols.put(col, false);
                mainDiag.add(row - col);
                otherDiag.add(row + col);

                // continue to next row
                recursivelyPlaceQueens(row+1, chessSize, trackCols, mainDiag, otherDiag, totalCount);

                // remove it
                otherDiag.remove(row + col);
                mainDiag.remove(row - col);
                trackCols.put(col, true);
            }
        }
    }
}
