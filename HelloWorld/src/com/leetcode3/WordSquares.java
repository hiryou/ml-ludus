package com.leetcode3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.com/problems/word-squares/description/
 * @Diary TLE with recursion solution
 * Updated solution: we need a quick way to retrieve a word with a given prefix -> trie. A trie node has a list of words
 */
public class WordSquares {

    static class Trie {
        Trie[] child = new Trie[256]; // ASCII characters
        List<String> ws = new ArrayList<>();
        static final List<String> EMPTY = new ArrayList<>();

        List<String> getWords(String pref) {
            if (pref.equals("")) return this.ws;
            return getWords(pref, 0);
        }
        private List<String> getWords(String pref, int idx) {
            // stop condition
            if (idx==pref.length()) return this.ws;

            char c = pref.charAt(idx);
            if (child[c-'a']==null) return EMPTY;

            return child[c-'a'].getWords(pref, idx+1);
        }

        void addWord(String w) {
            this.add(w, 0);
        }
        private void add(String w, int idx) {
            // log the word at this prefix
            ws.add(w);
            // stop condition
            if (idx==w.length()) {
                return;
            }

            char c = w.charAt(idx);
            if (child[c-'a'] == null ) child[c-'a'] = new Trie();
            child[c-'a'].add(w, idx+1);
        }
    }


    public List<List<String>> wordSquares(String[] ws) {
        List<List<String>> res = new LinkedList<>();
        if (ws.length==0) return res;

        Trie root = new Trie();
        for (String w: ws) root.addWord(w);

        List<String> cur = new ArrayList<>();
        for (int i=0; i<ws[0].length(); i++) cur.add("");
        recursionFind(root, ws, 0, ws[0].length(), cur, res);

        return res;
    }

    void recursionFind(Trie root, String[] ws, int step, int k, List<String> cur, List<List<String>> res) {
        // stop condition
        if (step==k) {
            //System.out.println(cur);
            res.add(new LinkedList<>(cur));
            return;
        }

        String pref = getPrefix(cur, step);
        List<String> cands = root.getWords(pref);
        for (String cand: cands) {
            cur.set(step, cand);
            recursionFind(root, ws, step+1, k, cur, res);
            cur.set(step, "");
        }
    }

    String getPrefix(List<String> cur, int lev) {
        if (lev==0) return "";
        String s = "";
        for (int i=0; i<lev; i++) s += cur.get(i).charAt(lev);
        return s;
    }
}
