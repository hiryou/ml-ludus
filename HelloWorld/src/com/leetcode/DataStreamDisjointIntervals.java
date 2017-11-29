package com.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/data-stream-as-disjoint-intervals/description/
 */
public class DataStreamDisjointIntervals {

    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }

        @Override
        public String toString() {
            return "[" + start + ", " + end +  "]";
        }
    }

    public static void main(String[] args) {
        DataStreamDisjointIntervals intervals = new DataStreamDisjointIntervals();

        int[] stream = {1, 3, 7, 2, 6};
        for (int val: stream) {
            intervals.addNum(val);
        }

        List<Interval> result = intervals.getIntervals();
        System.out.println(java.util.Arrays.toString(result.toArray()));
    }



    TreeMap<Integer, Interval> startToInterval = new TreeMap<>();
    TreeMap<Integer, Interval> endToInterval = new TreeMap<>();

    /**
     * Initialize your data structure here. */
    //public SummaryRanges() {
    public DataStreamDisjointIntervals() {

    }

    // 1, 3, 7, 2, 6, ...,
    public void addNum(int val) {
        // if val is contained within some existing interval, it's a no-op
        if (isContainedWithAnExistingInterval(val)) {
            return ;
        }

        // check for adjacent so there's potential for merging
        int leftEnd = val - 1;
        int rightStart = val + 1;
        Interval leftInterval = endToInterval.get(leftEnd);
        Interval rightInterval = startToInterval.get(rightStart);
        // add the right way
        addAccordingly(val, leftInterval, rightInterval);
    }

    private boolean isContainedWithAnExistingInterval(int val) {
        Map.Entry<Integer, Interval> floorEntry = startToInterval.floorEntry(val);
        Map.Entry<Integer, Interval> ceilingEntry = endToInterval.ceilingEntry(val);
        if (floorEntry == null || ceilingEntry == null) return false;

        return floorEntry.getValue() == ceilingEntry.getValue();
    }

    private void addAccordingly(int val, Interval leftInterval, Interval rightInterval) {
        Interval interval = new Interval(val, val);
        if (leftInterval != null) {
            interval.start = leftInterval.start;
            startToInterval.remove(leftInterval.start);
            endToInterval.remove(leftInterval.end);
        }
        if (rightInterval != null) {
            interval.end = rightInterval.end;
            startToInterval.remove(rightInterval.start);
            endToInterval.remove(rightInterval.end);
        }

        startToInterval.put(interval.start, interval);
        endToInterval.put(interval.end, interval);
    }

    public List<Interval> getIntervals() {
        List<Interval> result = new ArrayList<>();
        for(Map.Entry<Integer, Interval> entry : startToInterval.entrySet()) {
            result.add(entry.getValue());
        }

        return result;
    }
}
