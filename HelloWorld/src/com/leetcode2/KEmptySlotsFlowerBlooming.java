package com.leetcode2;

import java.util.*;

/**
 * https://leetcode.com/problems/k-empty-slots/description/
 * Solution: Transform problem to a different dimension which reveals much easier approach:
 *
 * 1. The problem gives array maps from day i to flower a[i]. This hinders difficulty because our job is to find out the
 * window [flower x -> flower y] that has NO flower blooming in between . It's easier to solve if we makes a 2nd
 * array mapping from flower i -> day of blooming a[i]. Problem becomes finding some k-window of flowers
 *
 * 2. Now we have our flower denoting i: 0 -> N-1, and a[i] is the day flower i will bloom. Looking for window k is
 * deterministic: a flower x and flower y k step from each other are being satisfied iff all flowers between them were
 * bloomed after them. it's easy to see this: after x bloomed, if any flower in between was bloomed before y, such flower
 * would have "ruined" the k empty spaces in between. Hench the window between x & y wouldn't have been satisfied. So, in
 * this k-window, all a[i] > a[x] and > a[y]. In other words: MIN(a[i] x < i < y) > a[x] and > a[y]
 *
 * 3. The problem becomes: Find a k-size window such that it's min > both of its immediate neighboring cells
 *
 * 4. Because k-size window is running left to right, this has to be a queue FIFO (left most popped, right most added).
 * How do we implement a queue which also tracks min: Using 2 stacks. And tracking min in a stack was a known solution
 * https://stackoverflow.com/questions/4802038/implement-a-queue-in-which-push-rear-pop-front-and-get-min-are-all-consta
 *
 *
 * Another solution
 * @see KEmptySlotsFlowerBlooming#kEmptySlots3(int[], int)
 * To find such a window k, let's say we have left and right such that a[left] and a[right] is k cell apart. All cells
 * between are smaller than both of these. We can keep a running i from left+1 -> right-1 to validate the condition holds
 * true. While validating, if encounter a cell a[k] that fail the condition. What to do? We have to slide the window rightward
 *
 * + If a[k] < a[left], we know that all a[left+1..k-1] > a[k] because these cells > a[left] > a[k]. So the only option
 * to slide right ward is to maek left = k, then continue the searching
 *
 * + If a[k] < a[right], we know that all a[left+1..k-1] > a[right] > a[k] (previously validated). So for any cell moving
 * rightward, the new a[left'] will be > current a[k]. This incorrectness is fixed until left' moves to k
 *
 * Conclusion: Start with left = 0, moving i rightward and do above validation. If i reaches right, we know there's 1
 * answer found. If invalidation happens at some cell a[k] move left to k and continue the same search
 */
public class KEmptySlotsFlowerBlooming {

    public static void main(String[] args) {
        KEmptySlotsFlowerBlooming p = new KEmptySlotsFlowerBlooming();

        //int[] flowers = {1,3,2}; int k = 1;
        //int[] flowers = {1,2,3}; int k = 1;
        //int[] flowers = {3,9,2,8,1,6,10,5,4,7} ; int k = 0;

        int[] flowers = random(20_000); int k = 256;
        //int[] flowers = random(20_000); int k = 0;

        BenchMark.run(() -> p.kEmptySlots3(flowers, k));
        BenchMark.run(() -> p.kEmptySlots(flowers, k));
        BenchMark.run(() -> p.kEmptySlots2(flowers, k));
    }

    private static int[] random(int size) {
        List<Integer> a = new ArrayList<>();
        for (int i=1; i<=size; i++) a.add(i);
        Collections.shuffle(a);

        int[] b = new int[size];
        for (int i=0; i<size; i++) {
            b[i] = a.get(i);
        }
        return b;
    }


    // use 2 stacks to implement a queue that tracks current min: Min of queue = 2 mins from 2 stack
    class Node {
        int value;
        Node min = null;
        public Node(int value) {
            this.value = value;
        }
    }
    class KWindow {
        int size;
        Stack<Node> st1 = new Stack<>();
        Stack<Node> st2 = new Stack<>();

        public KWindow(int size) {
            this.size = size;
        }

        public void enq(int a) {
            Node node = new Node(a);
            decideMinIn(node, st1);
            st1.add(node);
        }

        private void decideMinIn(Node self, Stack<Node> st1) {
            if (st1.isEmpty()) {
                self.min = self;
            } else {
                if (st1.peek().min.value < self.value) {
                    self.min = st1.peek().min;
                } else {
                    self.min = self;
                }
            }
        }

        public int min() {
            if (isEmpty()) return -1;
            int min1 = getMin(st1);
            int min2 = getMin(st2);
            if (min1 == -1) return min2;
            if (min2 == -1) return min1;
            return Math.min(min1, min2);
        }

        private int getMin(Stack<Node> st) {
            if (st.isEmpty()) return -1;
            return st.peek().min.value;
        }

        public boolean isEmpty() {
            return st1.isEmpty() && st2.isEmpty();
        }

        public int deq() {
            if (isEmpty()) return -1;
            if (!st2.isEmpty()) return st2.pop().value;

            // dump all from st1 -> st2
            while (!st1.isEmpty()) {
                Node node = st1.pop();
                decideMinIn(node, st2);
                st2.add(node);
            }
            // then poop from st2
            return st2.pop().value;
        }
    }

    // Cheapest iterative solution: a simple for loop as how I intuitively think a k window should look like
    public int kEmptySlots3(int[] flowers, int k) {
        int n = flowers.length;
        if (k+2 > n) return -1;
        int[] a = flowerToDay(flowers);

        // empty window / k == 0 is allowed
        if (k == 0) {
            int min = -1;
            for (int i=1; i<a.length; i++) {
                int date = Math.max(a[i-1], a[i]);
                if (min == -1 || date < min) {
                    min = date;
                }
            }
            return min;
        }

        int res = -1;
        int left = 0;
        for (int i=1; left+k+1 < a.length; i++) {
            // this is the main part of the algorithm: Keep validating the window iteratively
            if (a[left] > a[i] || a[i] < a[left+k+1]) { // current window is invalidated, move, left to i
                left = i;
                continue;
            }

            if (i == left+k) { // found 1, also move left to i to continue searching, or break early if any solution accepted
                int date = Math.max(a[left], a[left+k+1]);

                // this if condition may confuse, but simply what it does is tracking the earliest day this event occurs
                if (res == -1 || date < res) {
                    res = date;
                }
                left = i;
            }
        }

        return res;
    }

    // Solution using a sliding k-sized min queue sliding window
    public int kEmptySlots(int[] flowers, int k) {
        int n = flowers.length;
        if (k+2 > n) return -1;
        int[] a = flowerToDay(flowers);

        // empty window / k == 0 is allowed
        if (k == 0) {
            int min = -1;
            for (int i=1; i<a.length; i++) {
                int date = Math.max(a[i-1], a[i]);
                if (min == -1 || date < min) {
                    min = date;
                }
            }
            return min;
        }

        KWindow w = new KWindow(k);
        for (int i=0; i<k; i++) {
            w.enq(a[i]);
        }

        // lame question! Has to return the min result
        int min = -1;

        // slide window
        for (int i=1; i<n-k; i++) {
            w.deq();
            w.enq(a[i+k-1]);
            if (w.min() > 0 && w.min() > a[i-1] && w.min() > a[i+k]) {
                int date = Math.max(a[i-1], a[i+k]);
                if (min == -1 || date < min) {
                    min = date;
                }
            }
        }

        return min;
    }

    // map from flowr i -> day of blooming a[i]
    private int[] flowerToDay(int[] flowers) {
        int[] a = new int[flowers.length];
        for (int k=0; k<flowers.length; k++) {
            a[flowers[k] - 1] = k + 1; // because flower[0] means day 1 according to the question's phrasing
            // and flower ith starts from 0 instead of 1
        }
        return a;
    }


    // Solution using a sorted DS like treeset/treemap
    public int kEmptySlots2(int[] flowers, int k) {
        TreeSet<Integer> t = new TreeSet<>();

        for (int i=0; i<flowers.length; i++) {
            int pos = flowers[i];
            t.add(pos);

            Integer ceil = t.ceiling(pos+1);
            if (ceil != null && inBetween(ceil, pos) == k) return i + 1;

            Integer floor = t.floor(pos-1);
            if (floor != null && inBetween(floor, pos) == k) return i + 1;
        }

        return -1;
    }

    private int inBetween(int a, int b) {
        return Math.abs(a - b) - 1;
    }
}
