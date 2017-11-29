package com.leetcode2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/basic-calculator-ii/description/
 */
public class BasicCalculator2 {

    public static void main(String[] args) {
        BasicCalculator2 p = new BasicCalculator2();

        //String s = "3 - 2*2 + 7";
        //String s = " 3 /  2  ";
        //String s = " 3+5  / 2 ";
        String s = "100";
        BenchMark.run(() -> p.calculate(s));
    }


    class LastPart {
        char op1 = '+';
        Integer num1;
        char op2 = '@';
        Integer num2;

        void finalizeNum1() {
            if (num1 != null) {
                if (op1 == '-') num1 = -num1;
                op1 = '@';
            }
        }

        void finalizeNum2() {
            if (num2 != null) {
                if (op2 == '-') num2 = -num2;
                if (!isHigherOp(op2)) {
                    op2 = '@';
                }
            }
        }

        public void finalize(char nextOp) {
            finalizeNum1();
            finalizeNum2();

            if (isHigherOp(op2) || isLowerOp(nextOp)) {
                if (num2 != null) {
                    num1 = calculate(num1, num2, op2);
                    num2 = null;
                    op2 = '@';
                }
            }
        }

        int value() {
            if (num1 == null) return 0;
            finalizeNum1();
            finalizeNum2();
            if (num2 == null) return num1;
            return calculate(num1, num2, op2);
        }

        public void add(String s) {
            s = s.trim();
            char op = '+';
            if (isOp(s.charAt(0))) {
                op = s.charAt(0);
                s = s.substring(1);
            }
            if (num1 == null) {
                op1 = op;
                num1 = Integer.parseInt(s.trim());
            } else {
                op2 = op;
                num2 = Integer.parseInt(s.trim());
            }
        }

        public void clear() {
            op1 = '@';
            num1 = null;
            op2 = '@';
            num2 = null;
        }
    }

    /**
     * Short 1-time linear solution
     * @param s
     * @return
     */
    public int calculate(String s) {
        if (s.trim().length()==0) return 0;

        int total = 0;
        LastPart part = new LastPart();
        s = "+" + s + "+";
        int sidx = 0;

        for (int i=1; i<s.length(); i++) {
            char c = s.charAt(i);
            if (isOp(c)) {
                part.add(s.substring(sidx, i));
                part.finalize(c);

                if (isLowerOp(c)) {
                    total += part.value();
                    part.clear();
                }

                sidx = i;
            }
        }

        return total;
    }

    private int decideValue(Integer fact1, Integer fact2, char op) {
        if (isHigherOp(op)) return calculate(fact1, fact2, op);
        return fact1;
    }

    private int setValue(char lastOp, String s) {
        int val = Integer.parseInt(s.trim());
        if (lastOp == '-') val = -val;
        return val;
    }




    public int calculate2(String s) {
        if (s.trim().length()==0) return 0;

        List<String> elements = parseElements(s);
        if (elements.size()==1) return Integer.parseInt(elements.get(0));

        Stack<Integer> nums = new Stack<>();

        char lastSign = '+';
        for (String e: elements) {
            if (isNumber(e)) {
                int now = Integer.parseInt(e);

                if (lastSign == '+') nums.add(now);
                else if (lastSign == '-') nums.add(-now);
                else {
                    int top = nums.pop();
                    nums.add(calculate(top, now, lastSign));
                }
            }
            // else if is op
            else {
                lastSign = e.charAt(0);
            }
        }

        // add all at the end
        return nums.stream().reduce((a, b) -> a + b).get();
    }

    private int calculate(int a, int b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
        }
        throw new RuntimeException();
    }

    private List<String> parseElements(String s) {
        List<String> result = new ArrayList<>();
        int start = 0, end = 0;
        s = s.trim();

        // first look for all the op
        List<Integer> opIdx = new ArrayList<>();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (isOp(c)) {
                opIdx.add(i);
            }
        }
        // now the numbers are the portions between these ops
        int sIdx = 0;
        for (int idx: opIdx) {
            result.add(s.substring(sIdx, idx).trim());  // the number
            result.add(s.substring(idx, idx+1).trim()); // the op
            sIdx = idx + 1;
        }
        // last number
        result.add(s.substring(sIdx).trim());

        return result;
    }

    private boolean isNumber(String e) {
        if (e.length() >= 2) return true;
        char c = e.charAt(0);
        return !isOp(c);
    }

    boolean isOp(char c) {
        return isHigherOp(c) || isLowerOp(c);
    }

    private boolean isHigherOp(char c) {
        return c == '/' || c =='*';
    }

    private boolean isLowerOp(char c) {
        return c == '+' || c =='-';
    }
}
