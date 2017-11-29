package com.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/insert-interval/description/
 */
public class InsertInterval {

    public static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<Interval>() {{
            add(new Interval(1, 2));
            add(new Interval(3, 5));
            add(new Interval(6, 7));
            add(new Interval(8, 10));
            add(new Interval(12, 16));
        }};

        Interval newInterval = new Interval(1, 10);

        List<Interval> result = new InsertInterval().insert(intervals, newInterval);
        System.out.println(result.size());
    }





    // the intervals were initially sorted according to their start times
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        // Length of intervals = N, count of all positions including in-between spaces = 2N + 1
        int startIdx = findIndexToInsert(intervals, newInterval.start);
        int endIdx = findIndexToInsert(intervals, newInterval.end);

        Interval mergeInterval = new Interval(newInterval.start, newInterval.end);

        if (startIdx % 2 == 1) {
            Interval startInterval = intervals.get(startIdx/2);
            mergeInterval.start = startInterval.start;
        }
        if (endIdx % 2 == 1) {
            Interval endInterval = intervals.get(endIdx/2);
            mergeInterval.end = endInterval.end;
        }

        List<Interval> result = new ArrayList<>();
        for (int i=0; i < 2*intervals.size() + 1; i++) {
            if (i < startIdx || endIdx < i)
                addToResult(result, intervals, i);
            else if (i == startIdx)
                result.add(mergeInterval);
            else
                continue;
        }

        return result;
    }

    void addToResult(List<Interval> result, List<Interval> intervals, int idx) {
        // if not actual interval in intervals, no-op
        if (idx % 2 == 0) return;

        result.add(intervals.get(idx/2));
    }

    // index range from 0 -> 2N
    int findIndexToInsert(List<Interval> intervals, int val) {
        int startIdx = 0, endIdx = 2 * intervals.size();
        while (startIdx < endIdx) {
            int midIdx = startIdx + (endIdx - startIdx)/2;

            // if actual interval
            if (midIdx % 2 == 1) {
                Interval interval = intervals.get(midIdx/2);

                if (interval.start <= val && val <= interval.end) {
                    return midIdx;
                }

                if (interval.end < val) {
                    startIdx = midIdx + 1;
                }
                else {
                    endIdx = midIdx;
                }
            }
            // if the space in-between
            else {
                Interval leftInterval = intervals.get((midIdx-1) / 2);
                Interval rightInterval = intervals.get((midIdx+1) / 2);

                if (leftInterval.end < val && val < rightInterval.start) {
                    return midIdx;
                }

                if (val <= leftInterval.end) {
                    endIdx = midIdx;
                }
                else {
                    startIdx = midIdx + 1;
                }
            }
        }

        if (startIdx != endIdx) throw new RuntimeException("startIdx != lastIdx: should not happen");
        return startIdx;
    }
}
