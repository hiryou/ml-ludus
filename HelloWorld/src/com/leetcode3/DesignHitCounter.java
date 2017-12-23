package com.leetcode3;

import java.util.Map;
import java.util.TreeMap;

public class DesignHitCounter {

    public static void main(String[] args) {
        DesignHitCounter p = new DesignHitCounter();

        /**
         * ["HitCounter","hit","hit","hit","getHits","getHits","getHits","getHits","getHits","hit","getHits"]
            [[],[2],[3],[4],[300],[301],[302],[303],[304],[501],[600]]
         */

        p.hit(2);
        p.hit(3);
        p.hit(4);
        p.getHits(300);
        p.getHits(301);
        p.getHits(302);
        p.getHits(303);
        p.getHits(304);

        p.hit(501);
        p.getHits(600);
    }


    /** Initialize your data structure here. */
    public DesignHitCounter() {

    }


    private TreeMap<Integer, Integer> counter = new TreeMap<>();
    int curCount = 0;
    int passCount;

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        ++curCount;
        counter.put(timestamp, curCount);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        if (timestamp <= 300) return curCount;

        // remove the count more than 5 minutes ago
        int idx = timestamp - 300;
        Map.Entry<Integer, Integer> entry = counter.floorEntry(idx);

        // now move the main map to the tail portion
        if (entry != null) {
            passCount = entry.getValue();
            counter = new TreeMap<>(counter.tailMap(idx, false));
        }
        // then return result
        return curCount - passCount;
    }
}
