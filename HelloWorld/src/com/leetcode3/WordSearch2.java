package com.leetcode3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.*;

/**
 * https://leetcode.com/problems/word-search-ii/description/
 */
public class WordSearch2 {

    static class Trie {
        final Trie[] child = new Trie[26];
        int isWordIdx = -1; // if this node marking ending a word, value >= 0

        final String[] ws;

        Trie(String[] ws) {
            this.ws = ws;
        }

        void add(int wIdx, int idx) {
            // stop condition
            if (idx == ws[wIdx].length()) {
                isWordIdx = wIdx;
                return;
            }

            char c = ws[wIdx].charAt(idx);
            if (child[c - 'a'] == null) child[c - 'a'] = new Trie(this.ws);
            child[c - 'a'].add(wIdx, idx+1);
        }
    }

    Trie root;
    int M; int N;

    // solution: a trie prefix tree for all words
    // for the board: indexing from all beginning chars in words to list of coordinates in board a
    public List<String> findWords(char[][] a, String[] ws) {
        M = a.length;
        N = a[0].length;

        // Trie
        root = new Trie(ws);

        for (int i=0; i<ws.length; i++) {
            root.add(i, 0);
        }

        return wordSearch(a);
    }

    static int[][] dis = {{-1,0}, {+1,0}, {0,-1}, {0,+1}};

    // do BFS, starting parallel from all starting chars
    List<String> wordSearch(char[][] a) {
        Set<Integer> foundIdx = new HashSet<>();

        for (int i=0; i<M; i++) for (int j=0; j<N; j++) {
            dfs(a, i, j, root, foundIdx);
        }

        List<String> res = new LinkedList<>();
        for (int wIdx: foundIdx) res.add(root.ws[wIdx]);
        return res;
    }

    void dfs(char[][] a, int i, int j, Trie trie, Set<Integer> foundIdx) {
        // if found a word
        if (trie.isWordIdx != -1) foundIdx.add(trie.isWordIdx);

        char c = a[i][j];
        if (c == '#') return; // already visited
        if (trie.child[c - 'a'] == null) return; // not exist

        // marking as visited
        a[i][j]= '#';
        // visiting neighbors
        int x, y;
        for (int[] di: dis) {
            x = i + di[0];
            y = j + di[1];
            if (inbound(x, y) && a[x][y] != '#') dfs(a, x, y, trie.child[c - 'a'], foundIdx);
        }
        // mark back
        a[i][j] = c;
    }

    boolean inbound(int i, int j) {
        return 0 <= i && i < M && 0 <= j && j < N;
    }
}
