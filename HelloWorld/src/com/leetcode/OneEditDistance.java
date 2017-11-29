package com.leetcode;

/**
 * https://leetcode.com/problems/one-edit-distance/description/
 */
public class OneEditDistance {

    public static void main(String[] args) {
        String s = "abcd", t = "abed";
        OneEditDistance p = new OneEditDistance();
        System.out.println(p.isOneEditDistance(s, t));
    }


    public boolean isOneEditDistance(String s, String t) {
        if (t.length() < s.length()) return isOneEditDistance(t, s);
        if (t.length() - s.length() > 1) return false;
        if (s.length()==0 && t.length()==1) return true;

        int i = 0, j = 0;
        int count = 0;
        while (i < s.length()) {
            if (j >= t.length() || j-i > 1) return false;

            if (s.charAt(i) != t.charAt(j)) {
                ++count;
                if (count > 1) return false;
                if (t.length() != s.length()) {
                    ++j;
                    continue;
                }
            }

            ++i;
            ++j;
        }

        if (!(i==s.length() && j==t.length())) ++count;

        return count == 1;
    }
}
