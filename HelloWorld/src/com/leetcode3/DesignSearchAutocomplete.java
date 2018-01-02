package com.leetcode3;

import java.util.*;

/**
 * https://leetcode.com/problems/design-search-autocomplete-system/description/
 */
public class DesignSearchAutocomplete { class AutocompleteSystem {

    final class Trie {
        Trie[] child = new Trie[27];
        Map<String, Integer> stocount = new HashMap<>();

        void add(String s, int idx, int count) {
            if (count > 0) {
                stocount.put(s, count);
            } else {
                stocount.put(s, stocount.getOrDefault(s, 0) + 1);
            }

            // stop condition
            if (idx == s.length()) {
                return;
            }

            char c = s.charAt(idx);
            if (child[_int(c)] == null) child[_int(c)] = new Trie();
            child[_int(c)].add(s, idx+1, count);
        }

        Trie getChild(char c) {
            return child[_int(c)];
        }

        List<String> getTop3() {
            List<String> res = new LinkedList<>();
            List<String> cands = new ArrayList<>(stocount.keySet());
            Collections.sort(cands, (String a, String b) -> {
                int ac = stocount.get(a);
                int bc = stocount.get(b);
                if (ac == bc) return a.compareTo(b);
                return bc - ac;
            });
            for (int k=0; k<Math.min(3, cands.size()); k++) res.add(cands.get(k));
            return res;
        }
    }

    int _int(char c) {
        return c==' ' ?26 :c-'a' ;
    }

    final Trie root = new Trie();

    String stream = "";
    Trie streamNode = root;

    public AutocompleteSystem(String[] ss, int[] times) {
        for (int i=0; i<ss.length; i++) {
            root.add(ss[i], 0, times[i]);
        }
        streamNode = root;
    }

    public List<String> input(char c) {
        List<String> res = new LinkedList<>();
        if (c == '#') {
            root.add(stream, 0, -1);
            reset();
            return res;
        }

        // else
        stream += c;
        if (streamNode != null) streamNode = streamNode.getChild(c);

        if (streamNode != null)
            res.addAll(streamNode.getTop3());
        return res;
    }

    void reset() {
        stream = "";
        streamNode = root;
    }
}}
