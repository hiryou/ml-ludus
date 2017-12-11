package com.crackcode;

public class HanoiTowers {

    public static void main(String[] args) {
        HanoiTowers p = new HanoiTowers();

        p.move(5);
    }

    // 3 towers are A, B, C
    void move(int n) {
        recursion(1, n, 'A', 'C', 'B');
    }

    // move n disks from from -> to using middle as middle pole
    private void recursion(int ds, int de, char from, char to, char mid) {
        // stop condition
        if (ds==de) {
            move(ds, from, to); return;
        }

        recursion(ds, de-1, from, mid, to);
        recursion(de, de, from, to, mid);
        recursion(ds, de-1, mid, to, from);
    }

    void move(int k, char from, char to) {
        System.out.println(String.format("Move %d from %s -> %s", k, from, to));
    }
}
