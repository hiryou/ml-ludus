package com.leetcode;

import java.util.Stack;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/description/
 */
public class LongestValidParentheses {

    public static void main(String[] args) {
        String s = "))()(())(()";
        //s = "(()())";
        //s = "(((";
        //s = "))))))";
        //s = "";
        //s = "(()";
        //s = ")()())";
        LongestValidParentheses p = new LongestValidParentheses();
        System.out.println(p.longestValidParentheses(s));
    }



    /**
     * Using a stack push and match '(' with ')', if there's a match remove such from the stack. What is left at the end
     * are the indices of stand-alone invalid chars. The spaces between these chars are the length of all valid intervals
     */
    public int longestValidParentheses(String s) {
        if (s.isEmpty()) return 0;

        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                char cTop = s.charAt(stack.peek());
                if (cTop == '(' && c == ')') {
                    stack.pop();
                } else {
                    stack.push(i);
                }
            }
        }

        if (stack.isEmpty()) return s.length();

        int max = (s.length()-1) - stack.peek(); // firstly, from last invalid char to end of string
        while (!stack.isEmpty()) {
            int top = stack.pop();
            int peek = !stack.isEmpty() ?stack.peek() :-1 ;
            int newMax = top - peek - 1;
            max = newMax > max ?newMax :max ;
        }
        return max;
    }
}
