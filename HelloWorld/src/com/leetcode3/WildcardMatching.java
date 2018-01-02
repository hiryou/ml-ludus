package com.leetcode3;

/**
 * https://leetcode.com/problems/wildcard-matching/description/
 */
public class WildcardMatching {

    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[p.length()+1][s.length()+1];
        dp[0][0] = true;

        char[] pp = p.toCharArray();
        for (int i=0; i<p.length(); i++) {
            dp[i+1][0] = pp[i] == '*' && dp[i][0];
        }

        char[] ss = s.toCharArray();
        for (int i=0; i<pp.length; i++) for (int j=0; j<ss.length; j++) {
            if (pp[i]=='*') dp[i+1][j+1] = dp[i+1][j] || dp[i][j+1];
            else dp[i+1][j+1] = dp[i][j] && (pp[i]=='?' || pp[i]==ss[j]);
        }

        return dp[p.length()][s.length()];
    }
}
