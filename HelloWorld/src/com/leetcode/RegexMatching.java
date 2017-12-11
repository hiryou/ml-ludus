package com.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Dynamic programming DP
 * TODO Revisit and finish
 */
public class RegexMatching {

    public static void main(String[] args) {
        //String input = "aab", pattern = "a*a";
        //String input = "ab", pattern = ".*c";
        //String input = "aa", pattern = "aa";
        //String input = "a", pattern = "ab*";
        String input = "a", pattern = ".*..a*";
        boolean answer = new RegexMatching().isMatch(input, pattern);
        System.out.println(answer);
    }



    class Node {
        final String s;
        final String regex;
        final int st;
        final int rt;

        char sc = '@';
        char r1 = '@';
        char r2 = '~';

        Node(String s, int st, String regex, int rt) {
            this.s = s;
            this.st = st;
            this.regex = regex;
            this.rt = rt;

            if (st < s.length()) sc = s.charAt(st);
            if (rt < regex.length()) r1 = regex.charAt(rt);
            if (rt+1 < regex.length()) r2 = regex.charAt(rt+1);
        }

        List<Node> nextNodes() {
            List<Node> res = new ArrayList<>();
            if (!canMatch()) return res;

            if (r2 == '*') {
                res.add(new Node(s, st, regex, rt+2));
                for (int i=st; i<s.length(); i++) {
                    if (isMatchChar(r1, s.charAt(i))) {
                        res.add(new Node(s, i+1, regex, rt+2));
                    } else {
                        break;
                    }
                }
            } else {  // has to be a 1
                if (isMatchChar(r1, sc)) {
                    res.add(new Node(s, st+1, regex, rt+2));
                }
            }
            return res;
        }

        boolean isMatchChar(char regex, char c) {
            if (regex == '@' || c == '@') return false;
            return regex == '.' || regex == c;
        }

        boolean canMatch() {
            if (r2 == '*') {
                return true;
            }

            // else {  // c2 == '1'
            return isMatchChar(r1, sc);
        }

        boolean isCompleted() {
            return st >= s.length() && rt >= regex.length();
        }
    }

    public boolean isMatch(String s, String regex) {
        regex = shrinkFormat(regex);

        Node root = new Node(s, 0, regex, 0);

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node cur = q.remove();
            if (cur.isCompleted()) return true;
            q.addAll(cur.nextNodes());
        }

        // if none found
        return false;
    }

    // "a*a*..." becomes a*
    // "a*b" becomes a*b1
    String shrinkFormat(String regex) {
        StringBuilder b = new StringBuilder();
        Character lastC = null;
        for (int i=0; i<regex.length(); i++) {
            char c = regex.charAt(i);
            if (c == '*') {
                if (!alreadyHasStar(b, lastC)) {
                    b.append(lastC);
                    b.append('*');
                }
                lastC = null;
            } else {
                if (lastC != null) {
                    b.append(lastC);
                    b.append('1');
                }
                lastC = c;
            }
        }
        if (lastC != null) {
            b.append(lastC);
            b.append('1');
        }

        return b.toString();
    }

    boolean alreadyHasStar(StringBuilder b, Character c) {
        if (b.length() <= 1) return false;
        if (c == null) return false;

        int len = b.length();
        return b.charAt(len-1)=='*' && b.charAt(len-2)==c;
    }
}
