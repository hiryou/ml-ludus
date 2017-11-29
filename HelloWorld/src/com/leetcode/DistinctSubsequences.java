package com.leetcode;

/**
 * https://leetcode.com/problems/distinct-subsequences/hints/
 */
public class DistinctSubsequences {

    public static void main(String[] args) {
        DistinctSubsequences p = new DistinctSubsequences();
        String s = "rabbbbbb";
        String t = "rabb";
        System.out.println(p.numDistinct(s, t));
    }



    public int numDistinct(String s, String t) {
        if (t.length() > s.length()) return 0;

        //return recursiveCount(t, 0, s, 0);  // time limit exceeded
        return dpCount(t, s);
    }

    /**
     * Reasoning for dynamic programming solution: for a given substring T[0..i], add 1 character at at time to S from
     * left to right to form string S[0..j]. For any new S[j] char added, calculate the result at step [i][j]:
     *
     * Suppose we already had [i][j-1] = 3: T[0..i] has 3 sub-sequences in S[0..j-1]. Now consider new character S[j]:
     *  - If T[i] != S[j], number of match falls back to what we already had for T[0..i] and S[0..j-1]
     *  - If T[i] == S[j]: now there're 2 ways to match character T[i]:
     *      + T[i] can just match up to S[j-1] already had -> result = [i][j-1]
     *      + T[i] can match with [Sj] -> result = 1 (T[i] matches S[j]) * number of match at step [i-1][j-1]
     *      => Total result = [i][j-1] + [i-1][j-1]
     *      => Final cell (bottom right) corner contains the final answer
     */
    private int dpCount(String t, String s) {
        int[][] dp = new int[t.length()][s.length()];

        // top left cell
        if (t.charAt(0) == s.charAt(0)) {
            dp[0][0] = 1;
        }
        // when there's just 1 char in T, match count is number of encounter of T[0] in S
        for (int j=1; j<s.length(); j++) {
            dp[0][j] = dp[0][j-1];
            if (t.charAt(0) == s.charAt(j)) {
                ++dp[0][j];
            }
        }
        // when there's just 1 char in S to match but >=2 chars in T, match count is always 0 (not enough to match)
        for (int i=1; i<t.length(); i++) {
            dp[i][0] = 0;
        }
        // now fill it up
        for (int i=1; i<t.length(); i++) {
            for (int j=1; j<s.length(); j++) {
                if (t.charAt(i) != s.charAt(j)) {
                    dp[i][j] = dp[i][j-1];
                } else {
                    int existingWayToMatchCount = dp[i][j-1];
                    int newWayToMatchCount = dp[i - 1][j - 1];
                    dp[i][j] = existingWayToMatchCount + newWayToMatchCount;
                }
            }
        }

        return dp[t.length()-1][s.length()-1];
    }

    private int recursiveCount(String t, int i, String s, int j) {
        if (i == t.length()) {
            return 1;
        }

        // trying to match t[i] in s starting from s[j]
        int count = 0;
        for (int k=j; k < s.length() - (t.length()-1 - i); k++) {
            if (t.charAt(i) == s.charAt(k)) {
                count += recursiveCount(t, i+1, s, k+1);
            }
        }

        return count;
    }
}


