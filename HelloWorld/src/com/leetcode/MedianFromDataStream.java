package com.leetcode;

import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/description/
 */
public class MedianFromDataStream {

    public static void main(String[] args) {
        MedianFromDataStream p = new MedianFromDataStream();
        System.out.println(p.findMedian()); // 0.0
        p.addNum(-1);
        System.out.println(p.findMedian()); // 1.0
        p.addNum(-2);
        System.out.println(p.findMedian()); // 1.5
        p.addNum(-3);
        System.out.println(p.findMedian()); // 2.0
        p.addNum(-4);
        System.out.println(p.findMedian()); // 2.0
        p.addNum(-5);
        System.out.println(p.findMedian()); // 2.0
    }



    final PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    final PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

    public boolean isEmpty() {
        return minHeap.isEmpty() && maxHeap.isEmpty();
    }

    public void addNum(int num) {
        if (isEmpty()) {
            maxHeap.add(num);
            return;
        }

        if (num <= maxHeap.peek()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }

        // re-balance if needed
        if (maxHeap.size() - minHeap.size() == 2) {
            flowNumberFromTo(maxHeap, minHeap);
        } else if (minHeap.size() - maxHeap.size() == 2) {
            flowNumberFromTo(minHeap, maxHeap);
        }
    }

    private void flowNumberFromTo(PriorityQueue<Integer> h1, PriorityQueue<Integer> h2) {
        int num = h1.remove();
        h2.add(num);
    }

    /** initialize your data structure here. Solution is to use 2 heaps */
    public double findMedian() {
        if (isEmpty()) return 0;
        if (minHeap.size() == maxHeap.size()) {
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        }
        if (minHeap.size() > maxHeap.size()) return minHeap.peek();
        return maxHeap.peek();
    }
}
