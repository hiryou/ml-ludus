package com.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/number-of-islands-ii/description/
 */
public class NumberOfIslands2 {

    public static void main(String[] args) {
        NumberOfIslands2 p = new NumberOfIslands2();

        //int m = 3, n = 3; int[][] positions = new int[][] {{0,0}, {0,1}, {1,2}, {2,1}};
        //int m = 1, n = 1; int[][] positions = new int[][] {{0,0}, {0,1}, {1,2}, {2,1}};
        int m = 3, n = 3; int[][] positions = new int[][] {{0,1}, {1,2}, {2,1}, {1,0}, {0,2}, {0,0}, {1,1}};
        System.out.println(p.numIslands2(m, n, positions));
    }


    /**
     * No need to loop through all cells to update its islandId to some other islandId. Think of an island as a tree:
     * The first node that founded the island is the root of the island/tree. So to find/update islandId of a node and
     * all nodes in its island, all you need to do is just trace to the root node and update its root to another island's
     * root -> You have successfully merge the 2 island in just O(log(n)) time instead of n
     */
    class MyMap2 {
        final int m;
        final int n;
        final int[] parent; // parent[i] is i's parent, i is linear representation of cell[i][j]
        final Set<Integer> cells = new HashSet<>();

        int islandCount = 0;

        public MyMap2(int m, int n) {
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

    class MyMap {
        final int m; // m rows
        final int n; // n cols

        final Set<Integer> cells = new HashSet<>();

        final Map<Integer, Integer> cellToIslandId = new HashMap<>();
        final Map<Integer, Set<Integer>> islandIdToCells = new HashMap<>();

        // incrementing variable
        int globalIslandId = 0;
        private Integer islandCount;

        public MyMap(int m, int n) {
            this.m = m;
            this.n = n;
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

                Set<Integer> neighborIslands = new HashSet<>();
                if (top != -1 && cells.contains(top)) neighborIslands.add(cellToIslandId.get(top));
                if (down != -1 && cells.contains(down)) neighborIslands.add(cellToIslandId.get(down));
                if (left != -1 && cells.contains(left)) neighborIslands.add(cellToIslandId.get(left));
                if (right != -1 && cells.contains(right)) neighborIslands.add(cellToIslandId.get(right));
                checkAndMerge(cell, neighborIslands);
            }
        }

        private void checkAndMerge(int cell, Set<Integer> neighborIslands) {
            if (neighborIslands.isEmpty()) {
                ++globalIslandId;
                cellToIslandId.put(cell, globalIslandId);
                islandIdToCells.put(globalIslandId, new HashSet<>());
                islandIdToCells.get(globalIslandId).add(cell);
                return;
            }

            int mergeIslandId = -1;
            for (int neighborIslandId: neighborIslands) {
                if (mergeIslandId == -1) {
                    mergeIslandId = neighborIslandId;
                    cellToIslandId.put(cell, mergeIslandId);
                    islandIdToCells.get(mergeIslandId).add(cell);
                }
                // for the rest of the neighborhood islands, merge to the first appointed found island
                else {
                    Set<Integer> allNeighbors = islandIdToCells.get(neighborIslandId);

                    // remove old mappings
                    islandIdToCells.remove(neighborIslandId);

                    // new mappings
                    islandIdToCells.get(mergeIslandId).addAll(allNeighbors);
                    for (int friend: allNeighbors) {
                        cellToIslandId.put(friend, mergeIslandId);
                    }
                }
            }
        }

        // return -1 for cell outside of the map
        private int getCell(int i, int j) {
            if (i < 0 || i >=m) return -1;
            if (j < 0 || j >=n) return -1;
            return n*i + j;
        }

        public int getIslandCount() {
            return islandIdToCells.size();
        }
    }

    /**
     * 3
     * 3
     *
     * @param positions [[0,0],[0,1],[1,2],[2,1]]
     */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        //MyMap map = new MyMap(m, n);
        MyMap2 map = new MyMap2(m, n);
        List<Integer> result = new ArrayList<>();

        for (int k = 0; k < positions.length; k++) {
            int i = positions[k][0];
            int j = positions[k][1];
            map.fillCell(i, j);
            result.add(map.getIslandCount());
        }
        return result;
    }
}
