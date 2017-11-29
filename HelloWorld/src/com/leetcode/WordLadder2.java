package com.leetcode;

import java.util.*;

/**
 * https://leetcode.com/problems/word-ladder-ii/description/
 * TODO working but Need revisit; Some test cases exceeded time limit
 */
public class WordLadder2 {

    public static void main(String[] args) {
        String beginWord = "hit", endWord = "cog";
        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");

        //String beginWord = "a", endWord = "c";
        //List<String> wordList = Arrays.asList("a", "b", "c");

        WordLadder2 p = new WordLadder2();
        List<List<String>> ladders = p.findLadders(beginWord, endWord, wordList);
        for (List<String> path: ladders) {
            System.out.println(path);
        }
    }




    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        TreeMap<String, TreeSet<String>> adjMap = buildAdjacentMap(wordList, beginWord, endWord);

        //int minPath = wordList.size() + 2; // including begining & end words
        List<List<String>> result = new ArrayList<>();

        // track visited yet or not
        Map<String, Boolean> visited = new HashMap<>();
        for (String w: adjMap.keySet()) {
            visited.put(w, false);
        }

        List<String> path = new ArrayList<>();
        path.add(beginWord);
        visited.put(beginWord, true);
        recursiveDfsAndOptimize(path, endWord, adjMap, visited, result);
        visited.put(beginWord, false);

        return result;
    }

    private void recursiveDfsAndOptimize(
            List<String> path,
            String endWord,
            TreeMap<String, TreeSet<String>> adjMap, Map<String, Boolean> visited,
            List<List<String>> result) {

        // exit early if current result is worse than what already had
        if (!result.isEmpty() && path.size() > result.get(result.size()-1).size()) {
            return;
        }

        // current word
        String word = path.get(path.size()-1);

        // stop condition
        if ( word.equals(endWord) ) {
            // if new path is better than the best seen so far, this is the new best
            if (!result.isEmpty() && path.size() < result.get(result.size()-1).size()) {
                result.clear();
            }
            List<String> foundPath = new ArrayList<>(path);
            result.add(foundPath);
            return;
        }

        // visiting adjacent words
        for (String nextWord: adjMap.get(word)) if (!visited.get(nextWord)) {
            path.add(nextWord);
            visited.put(nextWord, true);
            recursiveDfsAndOptimize(path, endWord, adjMap, visited, result);
            visited.put(nextWord, false);
            path.remove(path.size()-1);
        }
    }

    // include begin word and end word
    TreeMap<String, TreeSet<String>> buildAdjacentMap(List<String> words, String beginWord, String endWord) {
        TreeMap<String, TreeSet<String>> result = new TreeMap<>();

        Set<String> setWords = new HashSet<>(words);
        setWords.add(beginWord);
        setWords.add(endWord);
        for (String word: setWords) {
            result.put(word, getAdjacents(word, words));
        }

        return result;
    }

    // exclude begin word and end word
    TreeSet<String> getAdjacents(String token, List<String> words) {
        TreeSet<String> result = new TreeSet<>();

        for (String word: words) {
            if (isOneCharDiff(token, word)) {
                result.add(word);
            }
        }

        return result;
    }

    boolean isOneCharDiff(String w1, String w2) {
        if (w1.equals(w2)) return false;

        int count = 0;
        for (int i=0; i<w1.length(); i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                ++count;
                if (count > 1) return false;
            }
        }
        return true;
    }
}
