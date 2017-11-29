package com.leetcode2;

import java.util.Stack;

/**
 * https://leetcode.com/problems/evaluate-reverse-polish-notation/description/
 */
public class EvaluateReversePolishNotation {

    public static void main(String[] args) {
        EvaluateReversePolishNotation p = new EvaluateReversePolishNotation();

        //String[] s = {"2", "1", "+", "3", "*"};
        //String[] s = {"4", "13", "5", "/", "+"};
        String[] s = {"1", "2", "/"};
        BenchMark.run(() -> p.evalRPN(s));
    }


    /**
     * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
     * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        if (tokens.length==0) return 0;

        Stack<Integer> nums = new Stack<>();
        for (String s: tokens) {
            if (isOp(s)) {
                int second = nums.pop();
                int first = nums.pop();
                nums.add(calculate(first, second, s));
            } else {
                int num = Integer.parseInt(s);
                nums.add(num);
            }
        }
        return nums.peek();
    }

    int calculate(int a, int b, String s) {
        char op = s.charAt(0);
        switch (op) {
            case '-': return a - b;
            case '+': return a + b;
            case '*': return a * b;
            case '/': return a / b;
        }
        throw new RuntimeException();
    }

    private boolean isOp(String s) {
        if (s.length() > 1) return false;
        return s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-");
    }

    private boolean isLowerOp(String s) {
        if (s.length() > 1) return false;
        return s.equals("+") || s.equals("-");
    }
}
