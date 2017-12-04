package com.leetcode2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/range-module/description/
 *
 * From: 12:30
 * To:
 * FAILED
 */
public class RangeModule {

    public static void main(String[] args) {
        RangeModule p = new RangeModule();

        System.out.println( p.addRange(10, 20) );
        System.out.println( p.removeRange(14, 16) );
        System.out.println( p.queryRange(10, 14) );
        System.out.println( p.queryRange(13, 15) );
        System.out.println( p.queryRange(16, 17) );
    }


    class Range {
        int start;
        int end;
        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(int k) {
            return start <= k && k < end;
        }

        boolean containsEnd(int k) {
            return start <= k && k <= end;
        }

        boolean isBetween(int start, int end) {
            return  start <= this.start && this.end <= end;
        }
    }

    // map from start -> range
    final TreeMap<Integer, Range> tmap = new TreeMap<>();

    public RangeModule() {

    }

    public String addRange(int start, int end) {
        Range sRange = findRangeContains(start);
        Range eRange = findRangeContains(end);

        if (sRange == null && eRange == null) {
            removeRangesBetween(start, end);
            tmapAddNewRange(start, end);
        } else if (sRange != null ^ eRange != null) {
            if (sRange != null) {
                removeRangesBetween(sRange.start, end);
                tmapAddNewRange(sRange.start, end);
            } else {
                removeRangesBetween(start, eRange.end);
                tmapAddNewRange(start, eRange.end);
            }
        } else {
            if (sRange != eRange) {
                removeRangesBetween(sRange.start, eRange.end);
                tmapAddNewRange(sRange.start, eRange.end);
            }
        }

        return "null";
    }

    public boolean queryRange(int start, int end) {
        Range sRange = findRangeContains(start);
        if (sRange == null) return false;
        return sRange.containsEnd(end);
    }

    public String removeRange(int start, int end) {
        Range sRange = findRangeContains(start);
        Range eRange = findRangeContains(end);

        if (sRange == null && eRange == null) {
            removeRangesBetween(start, end);
        }
        else if (sRange != null ^ eRange != null) {
            if (sRange != null) {
                sRange.end = start;
            } else {
                changeRangeStart(eRange, end);
            }
            removeRangesBetween(start, end);
        } else {
            if (sRange == eRange) {
                tmapAddNewRange(end, sRange.end);
                sRange.end = start;
            } else {
                sRange.end = start;
                eRange.start = end;
                removeRangesBetween(start, end);
            }
        }

        return "null";
    }

    Range findRangeContains(int k) {
        Map.Entry<Integer, Range> e = tmap.floorEntry(k);
        if (e == null) return null;
        Range r = e.getValue();
        if (!r.contains(k)) return null;
        return r;
    }

    void removeRangesBetween(int start, int end) {
        Map.Entry<Integer, Range> e = tmap.ceilingEntry(start);
        if (e == null) return;
        Range sRange = e.getValue();
        if (!sRange.isBetween(start, end)) return;

        e = tmap.floorEntry(end-1);
        if (e == null) return;
        Range eRange = e.getValue();
        if (!eRange.isBetween(start, end)) return;

        removeAllRanges(sRange, eRange);
    }

    void removeAllRanges(Range sRange, Range eRange) {
        Set<Range> rs = tmap.subMap(sRange.start, true, eRange.start, true).values().stream().collect(Collectors.toSet());
        for (Range r: rs) {
            tmap.remove(r.start);
        }
    }

    void tmapAddNewRange(int start, int end) {
        tmap.put(start, new Range(start, end));
    }

    void changeRangeStart(Range r, int newStart) {
        tmap.remove(r.start);
        r.start = newStart;
        tmap.remove(r.start, r);
    }
}
