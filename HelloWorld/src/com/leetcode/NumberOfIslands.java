package com.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/number-of-islands/description/
 */
public class NumberOfIslands {

    public static void main(String[] args) {
        NumberOfIslands p = new NumberOfIslands();

        //char[][] grid = buildGrid("11110", "11010", "11000", "00000");
        char[][] grid = buildGrid("11000", "11000", "00100", "00011");
        System.out.println(p.numIslands(grid));
    }

    private static char[][] buildGrid(String... strs) {
        int m = strs.length;
        char[][] grid = new char[m][];
        int idx = 0;
        for (String s: strs) {
            grid[idx++] = s.toCharArray();
        }
        return grid;
    }


    /**
     * Think of an island as a tree: The first node that founded the island is the root of the island/tree. So to
     * find/update islandId of a node and all nodes in its island, all you need to do is just trace to the root node
     * and update its root to another island's root -> You have successfully merge the 2 island in just O(log(n)) time
     */
    class MyMap {
        final int m;
        final int n;
        final int[] parent; // parent[i] is i's parent, i is linear representation of cell[i][j]

        final Set<Integer> cells = new HashSet<>();

        int islandCount = 0;

        public MyMap(int m, int n) {
            this.m = m;
            this.n = n;
            this.parent = new int[m*n];
        }

        public void fillCell(int i, int j) {
            // translate cell i,j -> linear value from 0 -> (m*n) - 1
            int cell = getCell(i, j);
            if (cell != -1 && !cells.contains(cell)) {
                cells.add(cell);
                int top = getCell(i-1, j);
                int down = getCell(i+1, j);
                int left = getCell(i, j-1);
                int right = getCell(i, j+1);

                Set<Integer> neighborRoots = new HashSet<>();
                if (top != -1 && cells.contains(top)) neighborRoots.add(getRoot(top));
                if (down != -1 && cells.contains(down)) neighborRoots.add(getRoot(down));
                if (left != -1 && cells.contains(left)) neighborRoots.add(getRoot(left));
                if (right != -1 && cells.contains(right)) neighborRoots.add(getRoot(right));
                checkAndMerge(cell, neighborRoots);
            }
        }

        private int getRoot(int cell) {
            int root = cell;
            while (parent[root] != -1) {
                root = parent[root];
            }
            return root;
        }

        private void checkAndMerge(int cell, Set<Integer> roots) {
            if (roots.isEmpty()) {
                ++this.islandCount;
                parent[cell] = -1;
                return;
            }

            this.islandCount = this.islandCount - roots.size() + 1;
            Iterator<Integer> iter = roots.iterator();
            int commonRoot = iter.next();
            parent[cell] = commonRoot;
            while (iter.hasNext()) {
                parent[iter.next()] = commonRoot;
            }
        }

        // return -1 for cell outside of the map
        private int getCell(int i, int j) {
            if (i < 0 || i >=m) return -1;
            if (j < 0 || j >=n) return -1;
            return n*i + j;
        }

        public int getIslandCount() {
            return this.islandCount;
        }
    }

    private int solutionTreatingIslandsAsTrees(char[][] grid, int m, int n) {
        MyMap map = new MyMap(m, n);
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) if (grid[i][j] == '1') {
                map.fillCell(i, j);
            }
        }
        return map.getIslandCount();
    }


    public int numIslands(char[][] grid) {
        int m = grid.length;
        if (m==0) return 0;
        int n = grid[0].length;
        if (n==0) return 0;

        //return solutionTreatingIslandsAsTrees(grid, m, n);
        return simpleDFS(grid, m, n);
    }

    private int simpleDFS(char[][] grid, int m, int n) {
        int count = 0;
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) if (grid[i][j] == '1') {
                ++count;
                doVisit(grid, m, n, i, j);
            }
        }
        return count;
    }

    private void doVisit(char[][] grid, int m, int n, int i, int j) {
        if (i < 0 || i >=m || j < 0 || j >= n || grid[i][j] != '1') return;
        grid[i][j] = '0';
        doVisit(grid, m, n, i-1, j);
        doVisit(grid, m, n, i+1, j);
        doVisit(grid, m, n, i, j-1);
        doVisit(grid, m, n, i, j+1);
    }
}
