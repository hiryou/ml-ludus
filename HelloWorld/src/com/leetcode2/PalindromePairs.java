package com.leetcode2;

import java.util.*;

/**
 * https://leetcode.com/problems/palindrome-pairs/description/
 * Best solution is to use trie
 */
public class PalindromePairs {

    public static void main(String[] args) {
        PalindromePairs p = new PalindromePairs();

        //String[] ws = {"bat", "tab", "cat", ""};
        String[] ws = {"abcd", "dcba", "lls", "s", "sssll"};
        //String[] ws = {"aba", ""};
        //String[] ws = {"a", ""};
        BenchMark.run(() -> p.palindromePairs(ws));
    }

    class Trie {
        Trie[] next = new Trie[26]; // 'a' to 'z' only
        final int height; // height indicates index of char in all words with same prefix (except the root node)
        List<Integer> wordIdx = new LinkedList<>(); // all wordIdx that pass through this path
        List<Integer> wordEndIdx = new LinkedList<>(); // all wordIdx that completed at this node

        // root has height = -1
        Trie() {
            this.height = -1;
        }

        private Trie(int height) {
            this.height = height;
        }

        void addWord(int wIdx, String w) {
            // only root should call this method
            if (height != -1) return;
            this.traceChar(wIdx, w, 0);
        }

        private void traceChar(int wIdx, String w, int idx) {
            wordIdx.add(wIdx);
            if (idx < w.length()) {
                char c = w.charAt(idx);
                if (next[c - 'a'] == null) next[c - 'a'] = new Trie(height+1);
                next[c - 'a'].traceChar(wIdx, w, idx+1);
            } else {
                // if reaching the end of the word
                wordEndIdx.add(wIdx);
            }
        }

        void readBackward(List<List<Integer>> res, String[] ws, int wIdx) {
            if (height != -1) return;
            this.traceBackward(res, ws, wIdx, ws[wIdx].length()-1);
        }

        private void traceBackward(List<List<Integer>> res, String[] ws, int wIdx, int idx) {
            // when input word ends
            if (idx == -1) {
                for (int wwIdx: this.wordIdx) if (wwIdx != wIdx && isPalindrome(ws[wwIdx], height+1)) {
                    res.add(Arrays.asList(wwIdx, wIdx));
                }
                return;
            }

            // if some trie word has length <= input word
            if (!this.wordEndIdx.isEmpty() && isPalindrome(ws[wIdx], 0, idx)) {
                for (int wwIdx: this.wordEndIdx) if (wwIdx != wIdx) {
                    res.add(Arrays.asList(wwIdx, wIdx));
                }
            }

            char c = ws[wIdx].charAt(idx);
            if (next[c - 'a'] == null) return;
            next[c - 'a'].traceBackward(res, ws, wIdx, idx-1);
        }

        boolean isPalindrome(String w, int s) {
            return isPalindrome(w, s, w.length()-1);
        }

        boolean isPalindrome(String w, int s, int t) {
            if (t <= s) return true;
            int i = s, j = t;
            while (i < j && w.charAt(i) == w.charAt(j)) {
                ++i;
                --j;
            }
            return j <= i;
        }
    }

    public List<List<Integer>> palindromePairs(String[] ws) {

        Trie root = new Trie();
        for (int i=0; i<ws.length; i++) {
            root.addWord(i, ws[i]);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i=0; i<ws.length; i++) {
            root.readBackward(res, ws, i);
        }

        return res;
    }
}
