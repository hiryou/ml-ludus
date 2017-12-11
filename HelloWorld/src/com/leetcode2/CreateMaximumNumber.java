package com.leetcode2;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * https://leetcode.com/problems/create-maximum-number/description/
 */
public class CreateMaximumNumber {

    public static void main(String[] args) {
        CreateMaximumNumber p = new CreateMaximumNumber();

        //int[] a = {3, 4, 6, 5}, b = {9, 1, 2, 5, 8, 3}; int k = 5;
        //int[] a = {6, 7}, b = {6, 0, 4}; int k = 5;
        //int[] a = {3, 9}, b = {8, 9}; int k = 3;
        int[] a = {8,0,4,4,1,7,3,6,5,9,3,6,6,0,2,5,1,7,7,7,8,7,1,4,4,5,4,8,7,6,2,2,9,4,7,5,6,2,2,8,4,6,0,4,7,8,9,1,7,0}, b = {6,9,8,1,1,5,7,3,1,3,3,4,9,2,8,0,6,9,3,3,7,8,3,4,2,4,7,4,5,7,7,2,5,6,3,6,7,0,3,5,3,2,8,1,6,6,1,0,8,4}; int k = 50;
        BenchMark.run(() -> print(p.maxNumber(a, b, k)));
    }

    static void print(int[] a) {
        for (int x: a) System.out.print(x + " ");
        System.out.println();
    }


    class Tracker {

        int aStartIdx = 0, bStartIdx = 0;

        int m, n;
        int k;
        final TreeSet<Integer>[] idxa;
        final TreeSet<Integer>[] idxb;

        Tracker(TreeSet<Integer>[] idxa, TreeSet<Integer>[] idxb, int m, int n, int k) {
            this.m = m; this.n = n;
            this.k = k;

            this.idxa = idxa;
            this.idxb = idxb;
        }

        int pickBest() {
            int anum = -1, aidx = -1;
            for (int x=9; x>=0; x--) if (!idxa[x].isEmpty() && canPick(idxa[x].first()-aStartIdx, m, n)) {
                anum = x; aidx = idxa[x].first();
                break;
            }

            int bnum = -1, bidx = -1;
            for (int x=9; x>=0; x--) if (!idxb[x].isEmpty() && canPick(idxb[x].first()-bStartIdx, n, m)) {
                bnum = x; bidx = idxb[x].first();
                break;
            }

            if (anum > bnum) {
                int newM = removeMeAndBefore(idxa, aidx, m);
                aStartIdx += m - newM;
                this.m = newM;
                this.k--;
                return anum;
            } else {
                //if (bnum > anum) {
                int newN = removeMeAndBefore(idxb, bidx, n);
                bStartIdx += n - newN;
                this.n = newN;
                this.k--;
                return bnum;
            }

            // if there's a tie

            //this.k--;
        }

        int removeMeAndBefore(TreeSet<Integer>[] idxa, int aidx, int m) {
            for (int x=9; x>=0; x--) if (!idxa[x].isEmpty()) {
                Set<Integer> beforeIdxes = new HashSet<>(idxa[x].headSet(aidx, true));
                if (beforeIdxes.size() > 0 ) {
                    m -= beforeIdxes.size();
                    idxa[x].removeAll(beforeIdxes);
                }
            }
            return m;
        }

        boolean canPick(int idx, int meSize, int otherSize) {
            return meSize-idx + otherSize >= k;
        }
    }

    public int[] maxNumber(int[] a, int[] b, int k) {
        int[] option1 = chooseMaxNumber(a, b, k);
        int[] option2 = chooseMaxNumber(b, a, k);

        for (int i=0; i<option1.length; i++) {
            if (option1[i] > option2[i]) return option1;
            if (option2[i] > option1[i]) return option2;
        }
        return option2;
    }

    private int[] chooseMaxNumber(int[] a, int[] b, int k) {
        TreeSet<Integer>[] idxa = new TreeSet[10]; // 0 to 9
        TreeSet<Integer>[] idxb = new TreeSet[10]; // 0 to 9

        for (int i = 0; i <= 9; i++) {
            idxa[i] = new TreeSet<>();
            idxb[i] = new TreeSet<>();
        }

        for (int i = 0; i < a.length; i++) {
            idxa[a[i]].add(i);
        }
        for (int i = 0; i < b.length; i++) {
            idxb[b[i]].add(i);
        }

        Tracker track = new Tracker(idxa, idxb, a.length, b.length, k);

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = track.pickBest();
        }

        return res;
    }
}
