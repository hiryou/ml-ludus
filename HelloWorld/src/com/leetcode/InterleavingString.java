package com.leetcode;

/**
 * Diary: referenced DP solution
 *  => Understood both DP and recursive with memoization
 */
public class InterleavingString {

    public static void main(String[] args) {
        InterleavingString program = new InterleavingString();
        //String s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac";
        //s3 = "aadbbbaccc"; // false

        //String s1 = "", s2 = "b", s3 = "b";
        //String s1 = "", s2 = "", s3 = "";

        String s1 = "aabd", s2 = "abdc", s3 = "aabdabcd";

        System.out.println(program.isInterleave(s1, s2, s3));
    }



    public boolean isInterleave(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) return false;

        if (s1.isEmpty()) return s3.equals(s2);
        if (s2.isEmpty()) return s3.equals(s1);

        //return recursiveCheck(s1, s2, s3);
        return dynamicProgrammingCheck(s1, s2, s3);
    }

    /**
     * To come up with a DP solution of time complexity M*N (M = s1 length, N is s2 length):
     *
     * Assume that at some point, we already used up s1[0->i] and s2[0->j] to interleave the prefix s3[0->i+j+1],
     * True if success, False if not. To interleave for the next char in s3, we can either choose s1[i+1] or s2[j+1]:
     *  *> If result up to s1[0->i] and s2[0->j] is already False, this step result is False
     *  *> If result up to s1[0->i] and s2[0->j] is True, this step result is True if either s1[i+1] or s2[j+1] matches
     *      the next char in s3, or False if not
     *
     * => Intuitive thinking: from a stage [i][j], you can derive right [i][j+1] or down [i+1][j]
     * => What it means for DP: from a cell [i][j], you can compute answer from top [i-1][j] or left [i][j-1]
     */
    private boolean dynamicProgrammingCheck(String s1, String s2, String s3) {
        // -1 = init; 0 = false, 1 = true
        int[][] dp = new int[s1.length()+1][s2.length()+1];
        for (int i=0; i<s1.length()+1; i++) {
            for (int j=0; j<s2.length()+1; j++) {
                dp[i][j] = -1;
            }
        }

        // when there's nothing to match, true
        dp[0][0] = 1;
        // first col: only use s1 to interleave s3
        for (int i=0; i<s1.length(); i++) {
            dp[i+1][0] = (dp[i][0]==1 && s1.charAt(i) == s3.charAt(i)) ?1 :0;
        }
        // first row: only use s2 to interleave s3
        for (int j=0; j<s2.length(); j++) {
            dp[0][j+1] = (dp[0][j]==1 && s2.charAt(j) == s3.charAt(j)) ?1 :0;
        }

        // now build up the dp table
        // rows represent s1
        for (int i=0; i<s1.length(); i++) {
            // cols represent s2
            for (int j=0; j<s2.length(); j++) {
                int row = i+1, col = j+1;
                char s3Char = s3.charAt(i+j+1);

                if (dp[row-1][col]==0 && dp[row][col-1]==0) {
                    dp[row][col] = 0;
                } else {
                    boolean chooseS2Char = dp[row][col-1]==1 && s2.charAt(j) == s3Char;
                    if (chooseS2Char) {
                        dp[row][col] = 1;
                    } else {
                        dp[row][col] = dp[row-1][col]==1 && s1.charAt(i) == s3Char ?1 :0 ;
                    }
                }
            }
        }

        return dp[s1.length()][s2.length()] == 1;
    }


    private boolean recursiveCheck(String s1, String s2, String s3) {
        // value: 1 = true; 0 = false; -1 = un-init
        int[][] memo = new int[s1.length()][s2.length()];
        for (int i=0; i<s1.length(); i++) {
            for (int j=0; j<s2.length(); j++) {
                memo[i][j] = -1;
            }
        }
        return recursiveCheckMemoization(s1, 0, s2, 0, s3, 0, memo);
    }

    private boolean recursiveCheckMemoization(String s1, int i, String s2, int j, String s3, int k, int[][] memo) {
        // stop condition: simple
        if (k == s3.length()) return true;
        // if exhaust s1, compare remaining of s2
        if (i == s1.length()) return s2.substring(j).equals(s3.substring(k));
        // if exhaust s2, compare remaining of s1
        if (j == s2.length()) return s1.substring(i).equals(s3.substring(k));

        // check cached result
        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        // last stop: do actual computation
        char c3 = s3.charAt(k);
        boolean ans = false;
        ans |= s1.charAt(i) == c3 && recursiveCheckMemoization(s1, i+1, s2, j, s3, k+1, memo);
        if (ans) return true;
        ans |= s2.charAt(j) == c3 && recursiveCheckMemoization(s1, i, s2, j+1, s3, k+1, memo);
        if (ans) return true;

        // do cache result
        memo[i][j] = ans ?1 :0 ;

        return memo[i][j] == 1;
    }
}
