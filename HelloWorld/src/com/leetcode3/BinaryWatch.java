package com.leetcode3;

import java.util.*;

/**
 * https://leetcode.com/problems/binary-watch/description/
 * TODO Check if the question answer was wrong?? with 2 dot we can form 12:00, but the answer doesn't contain one
 */
public class BinaryWatch {

    int[] vals = {1,2,4,8,16,32};

    public List<String> readBinaryWatch(int n) {
        List<String> res = new LinkedList<>();
        if (n <= 0 || n > 8) return res;

        for (int a=0; a<=n; a++) {
            int b = n - a;
            List<Integer> hh = new LinkedList<>();
            if (a==0) {
                hh.add(0);
            } else if (a <= 4) {
                recursion(a, 0, 4, 0, 0, 12, hh);
                if (hh.isEmpty()) continue;
            }

            List<Integer> mm = new LinkedList<>();
            if (b==0) {
                mm.add(0);
            } else if (b <= 6) {
                recursion(b, 0, 6, 0, 0, 59, mm);
                if (mm.isEmpty()) continue;
            }

            res.addAll(combine(hh, mm));
        }

        return res;
    }

    List<String> combine(List<Integer> hh, List<Integer> mm) {
        List<String> res = new LinkedList<>();
        for (int h: hh) for (int m: mm) {
            res.add(h+ ":" + padZero(m));
        }
        return res;
    }

    String padZero(int x) {
        if (x >= 10) return String.valueOf(x);
        return "0" + x;
    }

    void recursion(int requiredOnCount, int pos, int totalDot, int onCount, int val, int maxVal, List<Integer> res) {
        // stop condition for early back tracking
        if (val > maxVal) return;
        // stop condition for result
        if (pos == totalDot) {
            if (onCount == requiredOnCount && val <= maxVal) res.add(val);
            return;
        }

        // if this dot can turn on, if current number of onCount is still < required onCount
        if (onCount < requiredOnCount) {
            recursion(requiredOnCount, pos+1, totalDot, onCount+1, val + vals[pos], maxVal, res);
        }
        // this dot can also be turn off, if the next positions are enough to sum up to requiredOnCount
        if (onCount + (totalDot-pos) >= requiredOnCount) {
            recursion(requiredOnCount, pos+1, totalDot, onCount, val, maxVal, res);
        }
    }
}
