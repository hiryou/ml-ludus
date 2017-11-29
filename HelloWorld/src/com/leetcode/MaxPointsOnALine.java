package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/max-points-on-a-line/description/
 * TODO Need to review, this solution is not working yet
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
        Point[] points = {Point.of(0,1), Point.of(-1,2), Point.of(1,0)};

        MaxPointsOnALine p = new MaxPointsOnALine();
        System.out.println(p.maxPoints(points));
    }





    static class Ratio {
        final int num; // numerator
        final int div; // denominator

        public Ratio(int num, int div) {
            this.num = num;
            this.div = div;
        }

        @Override
        public boolean equals(Object obj){
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Ratio)) return false;

            Ratio that = (Ratio)obj;
            return this.num == that.num && this.div == that.div;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + num;
            result = prime * result + div;
            return result;
        }

        public static Ratio of(int num, int div) {
            if (num==0 || div==0) return new Ratio(num, div);

            int min = Math.min(num, div);
            int max = Math.max(num, div);

            int big = 1;
            for (int i=1; i<=min; i++) {
                big = i * max;
                if (big % min == 0) break;
            }

            int root = (min * max) / big;
            return new Ratio(num/root, div/root);
        }
    }

    public int maxPoints(Point[] points) {
        Map<Ratio, Integer> lineToCount = new HashMap<>();
        int max = 0;

        for (Point p: points) {
            Ratio r = Ratio.of(p.x, p.y);
            int count = lineToCount.containsKey(r) ?lineToCount.get(r) :0 ;
            lineToCount.put(r, count+1);
            max = count+1 > max ?count+1 :max ;
        }

        return max;
    }
}
