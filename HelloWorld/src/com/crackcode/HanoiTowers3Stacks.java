package com.crackcode;

import java.util.Stack;

public class HanoiTowers3Stacks {

    public static void main(String[] args) {
        HanoiTowers3Stacks p = new HanoiTowers3Stacks();

        Stack<Integer> a = new Stack<>();
        Stack<Integer> b = new Stack<>();
        Stack<Integer> c = new Stack<>();

        int n = 5;
        for (int k=5; k>=1; k--) a.push(k);

        System.out.print(a);
        System.out.print(b);
        System.out.println(c);

        p.move(1, n, a, 'a', c, 'c', b, 'b');

        System.out.print(a);
        System.out.print(b);
        System.out.println(c);
    }

    void move(
            int ds, int de,
            Stack<Integer> from, char fromName,
            Stack<Integer> to, char toName,
            Stack<Integer> mid, char midName) {
        if (ds == de) {
            move(from, fromName, to, toName);
            return;
        }

        move(ds, de-1, from, fromName, mid, midName, to, toName);
        move(de, de, from, fromName, to, toName, mid, midName);
        move(ds, de-1, mid, midName, to, toName, from, fromName);
    }

    // move 1 disk from 1 stack to another stack
    void move(Stack<Integer> from, char fromName, Stack<Integer> to, char toName) {
        int k = from.pop();
        to.push(k);
        System.out.println(String.format("Move %d from %s -> %s", k, fromName, toName));
    }
}
