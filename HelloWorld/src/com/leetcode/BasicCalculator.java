package com.leetcode;

import java.util.*;

/**
 *  https://leetcode.com/problems/basic-calculator/description/
 */
public class BasicCalculator {

    public static void main(String[] args) {
        String form = "(1+(4+5+2)-3)+(6+8)";

        BasicCalculator p = new BasicCalculator();
        System.out.println(p.calculate(form));
    }



    enum Type {
        Open, Close, Operator, Number
    }

    class Element {
        final Type type;
        final char token; // open, close, operator
        final int value; // number

        public Element(Type t, char c) {
            type = t;
            token = c;
            value = 0;
        }

        public Element(Type t, int val) {
            type = t;
            token = '*';
            value = val;
        }
    }

    public int calculate(String s) {
        if (s.length()==0) return 0;

        List<Element> elements = buildElements(s);
        List<Queue<Element>> queues = new ArrayList<>();
        queues.add(new LinkedList<>());
        for (Element e: elements) {
            Queue<Element> curQueue = queues.get(queues.size()-1);
            switch (e.type) {
                case Number:
                case Operator:
                    curQueue.add(e);
                    break;
                case Open:
                    queues.add(new LinkedList<>());
                    curQueue = queues.get(queues.size()-1);
                    break;
                case Close:
                    int value = calculateQueue(curQueue);
                    queues.remove(queues.size()-1);
                    curQueue = queues.get(queues.size()-1);
                    curQueue.add(new Element(Type.Number, value));
            }
        }

        // calculate top queue
        if (queues.size() != 1) throw new RuntimeException("!= 1 queue left over: Should not happen!");
        Queue<Element> curQueue = queues.get(0);
        return calculateQueue(curQueue);
    }

    private int calculateQueue(Queue<Element> queue) {
        if (queue.isEmpty()) return 0;
        int value = 0;
        int sum = queue.poll().value;
        while (!queue.isEmpty()) {
            char op = queue.poll().token;
            int num = queue.poll().value;
            sum = calculate(sum, op, num);
        }

        return sum;
    }

    private int calculate(int sum, char op, int num) {
        switch (op) {
            case '-': return sum - num;
            case '+': return sum + num;
        }
        return 0;
    }


    private List<Element> buildElements(String s) {
        List<Element> result = new ArrayList<>();
        result.add(new Element(Type.Number, 0));
        result.add(new Element(Type.Operator, '+'));

        int idx = 0;
        while (idx < s.length()) {
            char c = s.charAt(idx);
            switch (s.charAt(idx)) {
                case ' ':
                    ++idx;
                    break;
                case '(':
                    result.add(new Element(Type.Open, c));
                    ++idx;
                    break;
                case ')':
                    result.add(new Element(Type.Close, c));
                    ++idx;
                    break;
                case '+':
                case '-':
                    result.add(new Element(Type.Operator, c));
                    ++idx;
                    break;
                default: // number
                    if (!isDigit(c)) throw new RuntimeException("digit must be between 0-9");
                    // try to scan to the right to get a complete number
                    int sIdx = idx;
                    while (idx < s.length() && isDigit(c)) {
                        ++idx;
                        if (idx < s.length()) {
                            c = s.charAt(idx);
                        }
                    }
                    int number = Integer.parseInt(s.substring(sIdx, idx));
                    result.add(new Element(Type.Number, number));
                    break;
            }
        }

        return result;
    }

    boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }
}
