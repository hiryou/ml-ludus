package com.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/shortest-palindrome/description/
 * TODO Need review, failed some test cases
 */
public class ShortestPalindrome {

    public static void main(String[] args) {
        String s = "aaaaa";
        ShortestPalindrome p = new ShortestPalindrome();
        System.out.println(p.shortestPalindrome(s));
    }



    static class Word {
        final List<Character> chars = new ArrayList<>();
        Word nextWord = null;
        private char[] reverseChars;

        public Word(char c) {
            chars.add(c);
        }

        public int length() {
            return chars.size();
        }

        public boolean isPalindrome() {
            return length() >= 2;
        }

        public boolean canFormPalindromeWith(Word o) {
            if (Math.abs(length() - o.length()) > 1) return false;

            if (length() > o.length()) return isSymmetricWith(chars, 0, chars.size()-2, o.chars, 0, o.chars.size()-1);
            return isSymmetricWith(chars, 0, chars.size()-1, o.chars, 1, o.chars.size()-1);
        }

        boolean isSymmetricWith(Word o) {
            return this.length() == o.length()
                    && isSymmetricWith(chars, 0, chars.size() - 1, o.chars, 0, o.chars.size() - 1);
        }

        private static boolean isSymmetricWith(
                List<Character> a, int a1, int a2,
                List<Character> b, int b1, int b2) {
            if (a.size() != b.size()) return false;
            int i = a1-1, j = b2+1;
            while (i < a2) {
                ++i;
                --j;
                if (a.get(i) != b.get(j)) return false;
            }
            return true;
        }

        public Word combineWith(Word word) {
            this.chars.addAll(word.chars);
            return this;
        }

        public String getReverseChars() {
            StringBuilder result = new StringBuilder();
            for (int i=length()-1; i>=0; i--) {
                result.append(chars.get(i));
            }
            return result.toString();
        }
    }

    /**
     * Using stack and greedily building palindrome
     *
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        if (s.isEmpty()) return s;

        Stack<Word> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            pushAndOptimize(stack, new Word(c));
        }

        if (stack.size() == 1) return s;
        StringBuilder b = new StringBuilder();
        while (stack.size() > 1) {
            Word top = stack.pop();
            b.append(top.getReverseChars());
        }
        b.append(s);
        return b.toString();
    }

    private void pushAndOptimize(Stack<Word> stack, Word word) {
        // try combining word with the top 2 elements of the stack
        Word curWord = word;
        boolean canContinue = true;

        while (canContinue) {
            canContinue = false;
            if (stack.isEmpty()) break;

            //if (isSymmetric(stack.peek(), curWord)) {
            if (stack.peek().canFormPalindromeWith(curWord)) {
                canContinue = true;
                curWord = stack.pop().combineWith(curWord);
            } else if (isSymmetric(stack.peek().nextWord, curWord)) {
                Word top = stack.pop();
                curWord = stack.pop().combineWith(top).combineWith(curWord);
                canContinue = true;
            }
        }

        curWord.nextWord = (!stack.isEmpty()) ?stack.peek() :null;
        stack.push(curWord);
    }

    private boolean isSymmetric(Word peek, Word word) {
        if (peek == null && word == null) return false;
        if (peek == null || word == null) return false;
        return (peek.isSymmetricWith(word));
    }
}
