package com.leetcode3;

import java.util.*;

/**
 * https://leetcode.com/problems/strobogrammatic-number-ii/description/
 * TODO Correct all answers
 */
public class StrobogrammaticNumber2 {

    char[] cand = {'0', '1', '6', '8', '9'};
    // 5^n

    public List<String> findStrobogrammatic(int n) {
        List<String> res = new ArrayList<>();

        int half = n/2;
        int count = (int)Math.pow(5, half);
        char[] s = new char[half];
        for (int k = (int)Math.pow(5, half-1); k<count; k++) {  // first char cannot be '0'

            for (int p=0; p<half; p++) {
                int idx;
                if (p==half-1) {
                    idx = k % 5;
                } else {
                    idx = k / (int)Math.pow(5, half-p-1);
                }
                s[p] = cand[idx];
            }

            res.add(new String(s));
        }

        List<String> res2;
        if (n % 2 == 0) res2 = res;
        else {
            res2 = new ArrayList<>();
            for (String ss: res) for (char cc: cand) {
                res2.add(ss + cc);
            }
        }

        for (int i=0; i<res2.size(); i++) {
            res2.set(i, res2.get(i) + getRotate(res2.get(i)));
        }

        return res2;
    }

    String getRotate(String s) {
        int endIdx = s.length() % 2 == 0 ?s.length() :s.length()-1 ;

        String h = s.substring(0, endIdx);

        h = h.replaceAll("6", "@");
        h = h.replaceAll("9", "6");
        h = h.replaceAll("@", "9");
        return h;
    }
}
