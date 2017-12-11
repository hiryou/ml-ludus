package com.leetcode;

import com.geeksforgeeks.BenchMark;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.com/problems/max-points-on-a-line/description/
 *
 * Easy solution: we have n*(n-1)/2 possible lines. For each pair of point a and b:
 * - if a.x == b.x || a.y == b.y: count number of points with same x or y respectively
 * - else: transform coordinate so that a becomes {0,0} => the offset is {-a.x, -a.y}. Now calculate
 * Ratio(b.x-a.x/b.y-a.y), apply the transform to and count all points that return the same ratio -> same line
 */
public class MaxPointsOnALine {

    static class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }

        static Point of(int x, int y) {
            return new Point(x, y);
        }
    }

    public static void main(String[] args) {
        MaxPointsOnALine p = new MaxPointsOnALine();

        Point[] ps = {
                Point.of(-3,2), Point.of(-1,2), Point.of(4,0),
                Point.of(2,3), Point.of(2,-3),
                Point.of(0,1), Point.of(-2,-1), Point.of(-4,-3)
        };

        BenchMark.run(() -> p.maxPoints(ps));
    }



    class Ratio {
        final int x;
        final int y;
        Ratio(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Ratio ratio = (Ratio) o;
            if (x==0 && y==0) return ratio.x==0 && ratio.y==0;
            if (ratio.x==0 && ratio.y==0) return x==0 && y==0;

            long c1 = (long)x * ratio.y;
            long c2 = (long)y * ratio.x;

            return c1 == c2;
        }

        @Override
        @Deprecated
        public int hashCode() {
            return 31;
        }
    }

    public int maxPoints(Point[] ps) {
        if (ps.length <= 2) return ps.length;

        int max = 0;
        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();
        for (int i=0; i<ps.length-1; i++) for (int j=i+1; j<ps.length; j++) {
            Point a = ps[i];
            Point b = ps[j];
            if (a.x == b.x && !xs.contains(a.x)) {
                int count = countSameX(ps, a.x);
                xs.add(a.x);
                max = count > max ?count :max ;
            } else if (a.y == b.y && !ys.contains(a.y)) {
                int count = countSameY(ps, a.y);
                ys.add(a.y);
                max = count > max ?count :max ;
            } else {
                // transform coordinate so that point a becomes {0,0}
                int[] trans = {-a.x, -a.y};
                Ratio r = new Ratio(b.x + trans[0], b.y + trans[1]);
                int count = countSameRatio(ps, r, trans);
                max = count > max ?count :max ;
            }
        }

        return max;
    }

    int countSameRatio(Point[] ps, Ratio r, int[] trans) {
        int xoff = trans[0], yoff = trans[1];
        int count = 0;
        for (Point p: ps) {
            int x = p.x + xoff;
            int y = p.y + yoff;
            if (r.equals(new Ratio(x, y))) ++count;
        }
        return count;
    }

    int countSameX(Point[] ps, int x) {
        int count = 0;
        for (Point p: ps) if (p.x == x) ++count;
        return count;
    }

    int countSameY(Point[] ps, int y) {
        int count = 0;
        for (Point p: ps) if (p.y == y) ++count;
        return count;
    }

}
