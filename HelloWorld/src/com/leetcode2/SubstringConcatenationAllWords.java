package com.leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/description/
 */
public class SubstringConcatenationAllWords {

    public static void main(String[] args) {
        SubstringConcatenationAllWords p = new SubstringConcatenationAllWords();

        //String s = "barfoothefoobarman"; String[] words = {"foo", "bar"};
        //String s = "catdogcatdogcatdogcatdogcat"; String[] words = {"cat", "dog"};
        //String s = "dogdogcatcatdogcatdog"; String[] words = {"cat", "dog"};
        String s = "aaaaaaa"; String[] words = {"a", "a", "a"};
        BenchMark.run(() -> p.findSubstring(s, words));
    }


    int N, K, L;

    public List<Integer> findSubstring(String s, String[] ws) {
        List<Integer> res = new LinkedList<>();
        if (ws.length == 0) return res;
        if (ws[0].equals("")) return res;

        s = s + "@";
        N = s.length();
        K = ws.length;
        L = ws[0].length();
        boolean[] trial = new boolean[N];
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        for (String w: ws) right.add(w);

        for (int i=0; i<=N-(K*L); i++) if (!trial[i]) {
            trial[i] = true;
            if (canFind(s, i, i, left, right)) {
                res.add(i);
                int stepIdx = i;
                int lastEndIdx = stepIdx + K*L;
                while (!left.isEmpty()) {
                    String w = left.remove(0);
                    right.add(w);
                    stepIdx += L;
                    if (stepIdx > N-(K*L)) break;

                    trial[stepIdx] = true;
                    if (canFind(s, stepIdx, lastEndIdx, left, right)) {
                        res.add(stepIdx);
                        lastEndIdx = stepIdx + K*L;
                    }
                }
            } else {
                right.addAll(left);
                left.clear();
            }
        }

        return res;
    }

    boolean canFind(String s, int startIdx, int idx, List<String> left, List<String> right) {
        if (idx >= s.length()) return false;
        // stop condition for a success
        if (idx - startIdx == K*L) return true;

        boolean ans = false;
        for (int i=0; i<right.size(); i++) if (canMatch(s, idx, right.get(i))) {
            // pop right to left
            String w = right.remove(i);
            left.add(w);
            ans |= canFind(s, startIdx, idx+L, left, right);
            if (ans) return true;
        }

        return ans;
    }

    boolean canMatch(String s, int idx, String w) {
        int i = idx, j = 0;
        while (j < w.length() && s.charAt(i) == w.charAt(j)) {
            ++i;
            ++j;
        }
        return j == w.length();
    }
}
