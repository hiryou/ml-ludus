package com.leetcode2;

import java.util.*;

/**
 * https://leetcode.com/problems/the-skyline-problem/description/
 */
public class TheSkylineProblem {

    public static void main(String[] args) {
        TheSkylineProblem p = new TheSkylineProblem();

        //int[][] bs = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        // int[][] bs = {{0,2,3},{2,5,3}};
        int[][] bs = {{0,2,3},{2,5,3}};
        BenchMark.run(() -> print(p.getSkyline(bs)));
    }

    private static String print(List<int[]> skyline) {
        StringBuilder b = new StringBuilder();
        for (int[] t: skyline) {
            b.append(String.format("[%d,%d], ", t[0], t[1]));
        }
        return b.toString();
    }


    class Building {
        final int left;
        final int right;
        final int top;

        boolean isExisted = true;  // when the building is gone, we flag this -> false

        Building(int l, int r, int t) {
            left = l;
            right = r;
            top = t;
        }

    }

    static int edgeId = 1;
    class Edge {
        final int x;
        final int id = edgeId++;
        Edge(int x) {
            this.x = x;
        }
    }

    // first need a tree map from left & right -> building
    final Map<Edge, Building> edgeToBuilding = new HashMap<>();
    // accompany by an ordering of edges
    PriorityQueue<Edge> edges = new PriorityQueue<>((e1, e2) -> {
        if (e1.x == e2.x) return e1.id - e2.id;
        return e1.x - e2.x;
    });

    // max heap of buildings based on their heights
    final PriorityQueue<Building> topBuildings = new PriorityQueue<>(Comparator.comparingInt(b -> -b.top));

    public List<int[]> getSkyline(int[][] bs) {
        List<int[]> res = new ArrayList<>();

        for (int[] p: bs) {
            int left = p[0];
            int right = p[1];
            int top = p[2];

            Building b = new Building(left, right, top);
            Edge le = new Edge(left);
            Edge re = new Edge(right);

            edgeToBuilding.put(le, b); edgeToBuilding.put(re, b);
            edges.add(le); edges.add(re);
        }

        // loop left to right in the order of the significant points
        while (!edges.isEmpty()) {
        //for (Map.Entry<Edge, Building> e: edgeToBuilding.entrySet()) {
            Edge edge = edges.remove();
            Building b = edgeToBuilding.get(edge);
            if (edge.x == b.left) {
                startBuilding(b, res);
            } else if (edge.x == b.right) {
                endBuilding(b, res);
            } else {
                throw new RuntimeException("Should not happen!");
            }
        }

        return res;
    }

    void startBuilding(Building b, List<int[]> res) {
        int highestTop = getHighestTop();
        if (b.top > highestTop) res.add(new int[] {b.left, b.top});
        topBuildings.add(b);
    }

    void endBuilding(Building b, List<int[]> res) {
        b.isExisted = false;  // for remove later from the max heap
        int highestTop = getHighestTop();
        if (highestTop < b.top) {
            res.add(new int[] {b.right, highestTop});
        }
    }

    void cleanupTop() {
        while (!topBuildings.isEmpty() && !topBuildings.peek().isExisted) {
            topBuildings.remove();
        }
    }

    int getHighestTop() {
        cleanupTop();
        if (topBuildings.isEmpty()) return 0;
        return topBuildings.peek().top;
    }








    class RightEdge implements Comparable<RightEdge> {
        int right;
        int top;
        int left;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RightEdge other = (RightEdge) o;

            if (right != other.right) return false;
            if (top != other.top) return false;
            return left == other.left;
        }

        @Override
        public int hashCode() {
            int result = right;
            result = 31 * result + top;
            result = 31 * result + left;
            return result;
        }

        RightEdge(int r, int t, int l) {
            right = r;
            top = t;
            l = left;
        }

        boolean doesCover(int x, int y) {
            return top >= y && left <= x && x <= right;
        }

        @Override
        public int compareTo(RightEdge o) {
            return this.right - o.right;
        }
    }

    class Shade {
        TreeMap<Integer, RightEdge> marks = new TreeMap<>();

        boolean isVisible(int x, int y) {
            if (marks.isEmpty()) return true;
            return !marks.lastEntry().getValue().doesCover(x, y);
        }

        void addShade(int right, int top, int left) {
            RightEdge e = new RightEdge(right, top, left);
            marks.put(right, e);
            // TODO more
        }

        // remove all shades whose right <= mark
        void removeAnyShadeBefore(int mark) {
            Map<Integer, RightEdge> es = new HashMap(marks.headMap(mark, true));
            for (int key: es.keySet()) marks.remove(key);
        }
    }

    public List<int[]> getSkyline2(int[][] bs) {
        List<int[]> res = new ArrayList<>();
        if (bs.length ==0 ) return res;

        Shade sd = new Shade();

        for (int[] b: bs) {
            int left = b[0];
            int right = b[1];
            int top = b[2];

            // check if top-left point can be added
            if (sd.isVisible(left, top)) {
                res.add(new int[] {left, top});
            } else {

            }

            // add shade
            sd.addShade(right, top, left);

            // before adding shade for this one
            sd.removeAnyShadeBefore(left);
            // TODO maybe can do something here to get the cut coordinate
        }

        // later
        return res;
    }
}
