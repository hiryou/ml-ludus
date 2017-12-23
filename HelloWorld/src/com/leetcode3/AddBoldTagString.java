package com.leetcode3;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/add-bold-tag-in-string/description/
 * TODO Correct all answers
 */
public class AddBoldTagString {

    // similar to range merge problem
    public String addBoldTag(String s, String[] dict) {
        int[] start = new int[s.length()];
        int[] end = new int[s.length()];
        int count = 0;

        for (String d: dict) {
            int idx = 0;
            int pos = s.indexOf(d, idx);
            while (pos >= 0) {
                start[count] = pos;
                end[count++] = pos + d.length() - 1;

                // later
                idx = pos + d.length();
                if (idx+d.length()-1 >= s.length()) break;

                pos = s.indexOf(d, idx);
            }
        }

        // now do range merge
        Arrays.sort(start);
        Arrays.sort(end);
        int newCount = 0;
        for (int i=1; i<count; i++) {
            if (end[i-1] >= start[i]-1) { // can merge
                start[i] = start[i-1];
            } else {
                start[newCount] = start[i-1];
                end[newCount++] = end[i-1];
            }

            if (i==count-1) {
                start[newCount] = start[i];
                end[newCount++] = end[i];
            }
        }

        for (int i=newCount-1; i>=0; i--) {
            String sub = s.substring(start[i], end[i]+1);
            s = s.replace(sub, "<b>" + sub + "</b>");
        }
        return s;
    }
}
