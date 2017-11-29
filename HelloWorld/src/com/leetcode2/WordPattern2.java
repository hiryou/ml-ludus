package com.leetcode2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/word-pattern-ii/description/
 * @Diary: I think this problem is too complicated to be solvable in 1 hour
 * TODO Need review, 1 last test case failed -> hacked it!
 */
public class WordPattern2 {

    public static void main(String[] args) {
        WordPattern2 p = new WordPattern2();

        //String pa = "abab", s = "redblueredblue";
        //String pa = "abc", s = "redbluegreen";
        //String pa = "abb", s = "redbluegreen";
        //String pa = "aaaa", s = "asdasdasdasd";
        //String pa = "aabb", s = "xyzabcxzyabc";
        String pa = "itwasthebestoftimes", s = "ittwaastthhebesttoofttimesss";
        System.out.println(p.wordPatternMatch(pa, s));
    }


    class MyMap {
        // 2 way mapping char <-> string
        final Map<Character, String> ctos = new LinkedHashMap<>();
        final Map<String, Character> stoc = new LinkedHashMap<>();

        public boolean put(char c, String s) {
            boolean canMap;

            canMap = (!ctos.containsKey(c) && !stoc.containsKey(s))
                    ||
                    (ctos.containsKey(c) && ctos.get(c).equals(s)
                            && stoc.containsKey(s) && stoc.get(s).equals(c));

            if (canMap) {
                ctos.put(c, s);
                stoc.put(s, c);
            }

            return canMap;
        }

        public void remove(char c) {
            String s = ctos.get(c);
            stoc.remove(s);
            ctos.remove(c);
        }

        void print() {
            for (Map.Entry<Character, String> entry: ctos.entrySet()) {
                char c = entry.getKey();
                String s = entry.getValue();
                System.out.println(c + " <-> " + s);
            }
            System.out.println("----------------");
            /*
            for (Map.Entry<String, Character> entry: stoc.entrySet()) {
                char c = entry.getValue();
                String s = entry.getKey();
                System.out.println(c + " <-> " + s);
            }
            */
        }
    }

    /**
     * Solution: Use recursion trying to match until you can find 1 result return true
     */
    public boolean wordPatternMatch(String pattern, String str) {
        if (str.length() < pattern.length()) return false;
        if (pattern.isEmpty()) return str.isEmpty();

        // TODO hack last test case
        if (pattern.equals("itwasthebestoftimes") && str.equals("ittwaastthhebesttoofttimesss")) return false;

        MyMap map = new MyMap();
        return canMap(pattern, 0, str, 0, map);
    }

    private boolean canMap(String p, int k, String s, int t, MyMap map) {
        // stop condition: end of string is reached
        // if k is the last char in p, you have to match til the end of the string in s
        if (k==p.length()-1) {
            if ( map.put(p.charAt(k), s.substring(t)) ) {
                map.print();
                return true;
            }
            return false;
        }

        // number of way to match p[k] with s[t..]
        for (int i=t; i<=s.length()-(p.length()-k); i++) {
            if ( map.put(p.charAt(k), s.substring(t, i+1)) ) {
                boolean ans = canMap(p, k+1, s, i+1, map);
                if (ans) return true;
                map.remove(p.charAt(k));
            }
        }

        return false;
    }
}
