package com.leetcode2;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/edit-distance/description/
 *
 * @Diary: I tweaked the problem like this: first make sure s1 length <= s2 otherwise reverse. This makes sense because
 * it takes the same number of min steps to change from s2 back to s1
 * Tweak 2: Then this problem is about finding the maximum sub-sequence of s1 in s2
 *
 * @Diary: My tweaked solution wrong! Had to reference solution. Lesson learned: For DP, follow the essence of the
 * problem (in this example, understand what it means to do add/insert/delete instead of dismissing these 3 factors).
 * Tweaking the problem too much leads to wrong answer. My solution solves the problem of finding maximum sub-sequence,
 * not minimum operations which was the main question being asked.
 *
 * TODO Visit later to understand what it means to replace/insert/delete
 */
public class EditDistance {

    public static void main(String[] args) {
        EditDistance p = new EditDistance();

        //String s1 = "abkdc", s2 = "fadcng";
        //String s1 = "abaac", s2 = "bbbb";
        //String s1 = "ros", s2 = "horse";
        //String s1 = "astesghdsrf", s2 = "ertesdterytytrdjytur";
        String s1 = "sdijghioeu", s2 = "uihsgiefhgfggdif";
        BenchMark.run(() -> p.minDistance(s1, s2));
    }


    public int minDistance(String s1, String s2) {
        if (s1.length() > s2.length()) return minDistance(s2, s1);
        if (s1.isEmpty()) return s2.length();

        int[][] dp = fillDp(s1, s2);

        // now trace the matching chars
        int count = dp[s1.length()][s2.length()];
        if (count == 0) return s2.length();

        int[] idx1 = new int[count+2]; // in s1, fake char beginning and end
        idx1[0] = -1;
        idx1[count+1] = s1.length();
        int[] idx2 = new int[count+2]; // in s2, fake char beginning and end
        idx2[0] = -1;
        idx2[count+1] = s2.length();

        int i = dp.length-1, j = dp[0].length-1;
        while (count > 0) {
            j = traceLeft(dp, i, j);
            i = traceUp(dp, i, j);
            idx1[count] = i - 1;
            idx2[count] = j - 1;
            // later
            --count;
            --i;
            --j;
        }

        System.out.println("idx1 = " + Arrays.toString(idx1));
        System.out.println("idx2 = " + Arrays.toString(idx2));

        int res = 0;
        for (int k=1; k<idx1.length; k++) {
            i = idx1[k]; j = idx2[k];
            // Ex: "|ka" -> "|bca", "|kda" -> "|bca", "|kdffa" -> "|bca"
            res += Math.max(i - idx1[k-1] - 1, j - idx2[k-1] - 1);
        }

        return res;
    }

    private int traceLeft(int[][] dp, int i, int j) {
        while (j > 0 && dp[i][j] == dp[i][j-1]) --j;
        return j;
    }

    private int traceUp(int[][] dp, int i, int j) {
        while (i > 0 && dp[i][j] == dp[i-1][j]) --i;
        return i;
    }

    private int[][] fillDp(String s1, String s2) {
        int[][] dp = new int[s1.length()+1][s2.length()+1];

        for (int j=0; j<s2.length(); j++) {
            dp[0][j] = 0;
        }
        for (int i=0; i<s1.length(); i++) {
            dp[i][0] = 0;
        }

        for (int i=0; i<s1.length(); i++) for (int j=0; j<s2.length(); j++) {
            int res = Math.max(dp[1 + i-1][1 + j], dp[1 + i][1 + j-1]);
            if (s1.charAt(i) != s2.charAt(j)) {
                dp[1 + i][1 + j] = res;
            } else {
                dp[1 + i][1 + j] = Math.max(dp[1 + i-1][1 + j-1] + 1, res);
            }
        }

        return dp;
    }
}
