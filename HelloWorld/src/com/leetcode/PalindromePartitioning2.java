package com.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/palindrome-partitioning-ii/description/
 * TODO Need to revisit, have to solve with DP
 */
public class PalindromePartitioning2 {

    public static void main(String[] args) {
        PalindromePartitioning2 p = new PalindromePartitioning2();
        System.out.println(p.minCut("aabaaa"));
    }



    class Word {
        final List<Character> word = new ArrayList<>();
        // first init char
        public Word(char c) {
            word.add(c);
        }

        public int size() {
            return word.size();
        }

        public char charAt(int i) {
            return word.get(i);
        }

        public Word append(Word other) {
            word.addAll(other.word);
            return this;
        }

        public boolean isSymmetricWith(Word other) {
            if (this.size() != other.size()) return false;

            int i = -1, j = size();
            while (i < size()-1) {
                ++i; --j;
                if (charAt(i) != other.charAt(j)) return false;
            }

            return true;
        }
    }

    public int minCut(String s) {
        if (s.length()==0 || s.length() == 1) return 0;

        Stack<Word> stack = new Stack<>();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            addAndOptimize(stack, c);
        }

        return stack.size() - 1;
    }

    void addAndOptimize(Stack<Word> stack, char c) {
        if (stack.isEmpty()) {
            stack.push(new Word(c));
            return;
        }

        Word word = new Word(c);
        if (stack.peek().isSymmetricWith(word)) {
            optimizeStack(stack, word);
        } else {
            if (stack.size() == 1) {
                stack.push(word);
            } else {
                Word top = stack.pop();
                if (stack.peek().isSymmetricWith(word)) {
                    Word nextTop = stack.pop();
                    nextTop.append(top).append(word);
                    optimizeStack(stack, nextTop);
                } else {
                    stack.push(top);
                    stack.push(word);
                }
            }
        }
    }

    void optimizeStack(Stack<Word> stack, Word word) {
        boolean canContinue = !stack.isEmpty() && stack.peek().isSymmetricWith(word);
        Word current = word;

        while (canContinue) {
            Word top = stack.pop();
            top.append(current);
            current = top;
            canContinue = !stack.isEmpty() && stack.peek().isSymmetricWith(current);
        }

        // at the end at the big word back
        stack.push(current);
    }
}
