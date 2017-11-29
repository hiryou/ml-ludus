package com.leetcode2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SudokuSolver {

    public static void main(String[] args) {
        SudokuSolver p = new SudokuSolver();

        //int a = Integer.parseInt("100000100000", 2);
        //BenchMark.run(() -> p.isSingleOneBit(a));

        char[][] board = {
                {'.', '.', '9', '7', '4', '8', '.', '.', '.'},
                {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
                {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
                {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
                {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
                {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
                {'.', '.', '.', '2', '7', '5', '9', '.', '.'}};
        BenchMark.run(() -> p.solveSudoku(board));
        //p.solveSudoku(board);

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                System.out.print(board[i][j] + ((j+1)%3 == 0 ?"|" :","));
            }
            System.out.println();
            if ((i+1) % 3 == 0) {
                System.out.println("------------------");
            }
        }
    }



    public void solveSudoku(char[][] board) {
        // for each row, track the number that were already used, use bitmap "1111111110" (10 digit), 1 = used
        int[] rows = new int[9];
        for (int i=0; i< 9; i++) rows[i] = trackRowUsed(board, i);
        int[] cols = new int[9];
        for (int j=0; j< 9; j++) cols[j] = trackColUsed(board, j);
        int[] areas = new int[9];
        for (int k=0; k< 9; k++) areas[k] = trackAreaUsed(board, k);

        // first get a hold of all empty cells, starting top-left -> bottom-right: 0 -> 80 (81 cells total)
        Set<int[]> emptyCells = getEmptyCells(board);
        if (emptyCells.isEmpty()) return;

        // simple solver: fill all cells that can only have 1 option
        simpleSolverFillCellsWithOnlyOneOption(board, rows, cols, areas, emptyCells);

        // The rest of the board: solve by recursion
        rescursiveSolve(board, emptyCells.stream().collect(Collectors.toList()), 0, rows, cols, areas);
    }

    private void simpleSolverFillCellsWithOnlyOneOption(
            char[][] board, int[] rows, int[] cols, int[] areas, Set<int[]> emptyCells) {
        Set<int[]> remove = new HashSet<>();
        boolean foundAny = true;
        while (foundAny) {
            foundAny = false;

            for (int[] cell: emptyCells) {
                List<Integer> posVals = getPosValue(cell[0], cell[1], rows, cols, areas);
                if (posVals.size()==1) {
                    int val = posVals.get(0);
                    board[cell[0]][cell[1]] = (char)((int)'0' + val);
                    updateData(rows, cols, areas, cell[0], cell[1], val);

                    remove.add(cell);
                    foundAny = true;
                }
            }

            emptyCells.removeAll(remove);
        }
    }

    private boolean rescursiveSolve(char[][] board, List<int[]> ecs, int ith, int[] rows, int[] cols, int[] areas) {
        // stop condition
        if (ith == ecs.size()) return true;

        // decide all possible values for ecs[ith]
        int i = ecs.get(ith)[0];
        int j = ecs.get(ith)[1];
        List<Integer> posVals = getPosValue(i, j, rows, cols, areas);
        for (int val: posVals) {
            board[i][j] = (char)((int)'0' + val);
            updateData(rows, cols, areas, i, j, val);

            boolean ans = rescursiveSolve(board, ecs, ith+1, rows, cols, areas);
            if (ans) return true;

            // undo
            unUpdateData(rows, cols, areas, i, j, val);
            board[i][j] = '.';
        }

        return false;
    }

    private void updateData(int[] rows, int[] cols, int[] areas, int i, int j, int val) {
        rows[i] = clearBit(rows[i], val);
        cols[j] = clearBit(cols[j], val);
        int areaId = getArea(i, j);
        areas[areaId] = clearBit(areas[areaId], val);
    }

    private void unUpdateData(int[] rows, int[] cols, int[] areas, int i, int j, int val) {
        rows[i] = setBit(rows[i], val);
        cols[j] = setBit(cols[j], val);
        int areaId = getArea(i, j);
        areas[areaId] = setBit(areas[areaId], val);
    }

    private int getOneBitPos(int posValue) {
        int pos = -1;
        while (posValue > 0) {
            ++pos;
            posValue = posValue>>1;
        }
        return pos;
    }

    private List<Integer> getPosValue(int i, int j, int[] rows, int[] cols, int[] areas) {
        int intersect = rows[i] & cols[j] & areas[getArea(i, j)];
        List<Integer> result = new ArrayList<>();
        for (int loc=1; loc<=9; loc++) {
            if (getBit(intersect, loc) == true) { // true means this bit is 1
                result.add(loc);
            }
        }
        return result;
    }

    // area labeled from 0 -> 8
    private int getArea(int i, int j) {
        // 3x3 row col calculated
        int row = i / 3;
        int col = j / 3;
        return row * 3 + col;
    }

    // https://stackoverflow.com/questions/3160659/innovative-way-for-checking-if-number-has-only-one-on-bit-in-signed-int
    private boolean isSingleOneBit(int value) {
        return value == (value & -value);
    }

    private Set<int[]> getEmptyCells(char[][] board) {
        Set<int[]> result = new HashSet<>();
        for (int i=0; i<9; i++) for (int j=0; j<9; j++) {
            if (board[i][j] == '.') {
                result.add(new int[] {i, j});
            }
        }
        return result;
    }

    private int trackAreaUsed(char[][] board, int k) {
        // 3x3 row col calculated
        int row = k / 3;
        int col = k % 3;

        // now project it to the 9x9
        int bitmap = Integer.parseInt("1111111110", 2);
        for (int i=3*row; i<3*row + 3; i++) {
            for (int j=3*col; j<3*col + 3; j++) {
                if (board[i][j] != '.') {
                    int used = board[i][j] - '0';
                    bitmap = clearBit(bitmap, used);
                }
            }
        }
        return bitmap;
    }

    private int trackColUsed(char[][] board, int col) {
        int bitmap = Integer.parseInt("1111111110", 2);
        for (int i=0; i<9; i++) {
            if (board[i][col] != '.') {
                int used = board[i][col] - '0';
                bitmap = clearBit(bitmap, used);
            }
        }
        return bitmap;
    }

    // track the number that were already used, use bitmap "0111111111" (10 digit), 1 = used
    private int trackRowUsed(char[][] board, int row) {
        int bitmap = Integer.parseInt("1111111110", 2);
        for (int j=0; j<9; j++) {
            if (board[row][j] != '.') {
                int used = board[row][j] - '0';
                bitmap = clearBit(bitmap, used);
            }
        }
        return bitmap;
    }

    // set bit used to 0, 0 = not possible for choosing / number already used
    private int clearBit(int bitmap, int used) {
        int newBit = bitmap & ~(1<<used);
        return newBit;
    }

    // set bit new/not-used to 1, 1 = possible for choosing
    private int setBit(int bitmap, int used) {
        int newBit = bitmap | ~(1<<used);
        return newBit;
    }

    // retrieve bit
    private boolean getBit(int bitmap, int loc) {
        int mask = 1<<loc;
        return (bitmap & mask) > 0;
    }
}
