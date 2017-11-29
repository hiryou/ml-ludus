package com.leetcode2;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/
 */
public class KthSmallestSortedMatrix {

    public static void main(String[] args) {
        KthSmallestSortedMatrix p = new KthSmallestSortedMatrix();

        int[][] matrix = {{1,5,9}, {10,11,13}, {12,13,15}}; int k = 7;

        System.out.println(p.kthSmallest(matrix, k));
    }



    public int kthSmallest(int[][] m, int k) {
        if (m.length==0) return 0;

        return solutionHeap(m, k);
    }

    // max heap if interger
    class MyHeap {
        final int size;
        final PriorityQueue<Integer> heap;
        final boolean isMaxHeap;

        public MyHeap(int size, boolean isMaxHeap) {
            this.size = size;
            this.isMaxHeap = isMaxHeap;
            heap  = new PriorityQueue<>((a, b) -> {
                if (this.isMaxHeap) {
                    if (a < b) return +1;
                    if (a == b) return 0;
                    return -1;
                }
                // else it's min heap
                if (a < b) return -1;
                if (a == b) return 0;
                return +1;
            });
        }

        public boolean add(int a) {
            if (heap.size() < size) {
                heap.add(a);
                return true;
            }

            // no, size is exceeded
            if (isMaxHeap) {
                if (a < heap.peek()) {
                    heap.remove();
                    heap.add(a);
                    return true;
                }
                return false;
            }

            // now it is min heap
            if (a > heap.peek()) {
                heap.remove();
                heap.add(a);
                return true;
            }
            return false;
        }
    }

    /**
     * Solution:
     * 1. normal k-size max heap to store k smallest elements
     * 2. loop through each "other" diag of the matrix (starting from top-left). Because the matrix tend to increase
     *      from top-left to bottom-right, looping this way makes less elements to be added to the heap
     * 3. when during any diag, if there's none element can be added to the heap, we know immediately that we don't need
     * to continue because the next diag is > this diag anyway -> exit early for fast runtime
     * 4. tweak the problem for faster runtime: k is from 1 -> n*n; if k is in smaller half, the problem is finding k
     * smallest; if k is in bigger half, the problem is finding (n*n-k+1) biggest and return the smallest in this group.
     * The whole purpose here is to have the least number of time we have to swap out the heap's top
     */
    private int solutionHeap(int[][] m, int k) {
        boolean isMaxHeap = (k <= m.length*m.length/2);
        MyHeap heap = isMaxHeap ?new MyHeap(k, true) :new MyHeap(m.length*m.length - k + 1, false);

        // found any on a diag
        boolean foundAny = false;

        if (isMaxHeap) {
            for (int t=0; t<2*m.length-1; t++) {
                foundAny = doDiag(t, heap, m);
                if (!foundAny) break;
            }
            return heap.heap.peek();
        }

        // else it is min heap
        for (int t=2*m.length-2; t>=0; t--) {
            foundAny = doDiag(t, heap, m);
            if (!foundAny) break;
        }
        return heap.heap.peek();
    }

    private boolean doDiag(int t, MyHeap heap, int[][] m) {
        // if first half
        if (t < m.length) {
            int i = t; int j = t - i;
            boolean foundAny = false;
            while (i >= 0) {
                if (heap.add(m[i--][j++])) {
                    foundAny = true;
                }
            }
            return foundAny;
        }

        // else it is second half
        int j = m.length - 1; int i = t - j;
        boolean foundAny = false;
        while (i < m.length) {  // because this is s square
            if (heap.add(m[i++][j--])) {
                foundAny = true;
            }
        }
        return foundAny;
    }
}
