package com.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/candy/description/
 * @Diary: Came up with a too complicated solution using heap and map; Simple solution is O(n)
 */
public class ChildrenRatingCandy {

    public static void main(String[] args) {
        ChildrenRatingCandy p = new ChildrenRatingCandy();

        //int[] childrenRatings = {1, 2, 2};
        //int[] childrenRatings = {0};
        //int[] childrenRatings = {0, 0, 0};
        //int[] childrenRatings = {1, 2, 5, 2, 1, 5};
        int[] childrenRatings = {2, 2, 1};
        System.out.println(p.candy(childrenRatings));
    }



    static class Child implements Comparable<Child> {
        final int rating;
        final int index;

        public Child(int rating, int index) {
            this.rating = rating;
            this.index = index;
        }

        @Override
        public int compareTo(Child o) {
            if (rating != o.rating) return rating - o.rating;
            return index - o.index;
        }
    }

    static class CandyGift {
        final int childRating;
        final int candyCount;

        public CandyGift(int childRating, int candyCount) {
            this.childRating = childRating;
            this.candyCount = candyCount;
        }

        public static CandyGift empty() {
            return new CandyGift(Integer.MIN_VALUE, 0);
        }
    }

    public int candy(int[] ratings) {
        if (ratings.length == 0) return 0;

        return simpleScanning2Ways(ratings);
        //return solutionWithHeapAndMap(ratings);
    }

    private int simpleScanning2Ways(int[] ratings) {
        int[] candy = new int[ratings.length];

        // start with each children got 1 candy
        for (int i=0; i<ratings.length; i++) {
            candy[i] = 1;
        }
        // scan left to right to make sure right higher child gets +1 candy
        for (int i=1; i<ratings.length; i++) {
            if (ratings[i-1] < ratings[i] && candy[i-1] >= candy[i]) {
                candy[i] = candy[i-1] + 1;
            }
        }
        // scan right to left to make sure left higher child gets +1 candy
        for (int i=ratings.length-2; i>=0; i--) {
            if (ratings[i] > ratings[i+1] && candy[i] <= candy[i+1]) {
                candy[i] = candy[i+1] + 1;
            }
        }

        int total = 0;
        for (int i=0; i<candy.length; i++) {
            total += candy[i];
        }
        return total;
    }

    private int solutionWithHeapAndMap(int[] ratings) {
        // 1. use a min heap of {childRating, childIndex}
        // 2. use a hash map of childIndex -> {childRating, candyCount}
        // keeping track of current count to return at the end

        PriorityQueue<Child> children = new PriorityQueue<>();
        for (int i=0; i<ratings.length; i++) {
            children.add(new Child(ratings[i], i));
        }

        // now the hash map
        int totalCount = 0;
        Map<Integer, CandyGift> indexToCandy = new HashMap<>();
        while (children.peek() != null) {
            Child child = children.poll();
            int candyCount = decideCandyCount(indexToCandy, child);
            indexToCandy.put(child.index, new CandyGift(child.rating, candyCount));
            totalCount += candyCount;
        }

        return totalCount;
    }

    int decideCandyCount(Map<Integer, CandyGift> indexToCandy, Child child) {
        int index = child.index;

        CandyGift left = indexToCandy.containsKey(index-1) ?indexToCandy.get(index-1) :CandyGift.empty() ;
        CandyGift right = indexToCandy.containsKey(index+1) ?indexToCandy.get(index+1) :CandyGift.empty() ;

        int candyCount = 1;
        if (child.rating > left.childRating) {
            int newCount = left.candyCount + 1;
            candyCount = (newCount > candyCount) ?newCount :candyCount ;
        }
        if (child.rating > right.childRating) {
            int newCount = right.candyCount + 1;
            candyCount = (newCount > candyCount) ?newCount :candyCount ;
        }

        return candyCount;
    }
}
