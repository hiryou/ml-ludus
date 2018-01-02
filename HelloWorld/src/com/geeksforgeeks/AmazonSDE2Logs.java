package com.geeksforgeeks;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Coding on paper. This file serves as a log of what I have completed / doing - Only coding questions
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-417-sde-2/ -- 7 rounds
 *      - Done all coding in 4:10
 *      - Question of reversing stack using recursion took ~30min
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-415-sde-2/ -- 5 rounds
 *      - Done all coding in 1:10
 *      - Interesting problem: Celebrity problem
 *
 * Doing: https://www.geeksforgeeks.org/amazon-interview-experience-set-412-sde-ii/ -- 6 rounds
 *      - Interesting problem: Finite Automata https://www.geeksforgeeks.org/searching-for-patterns-set-5-finite-automata/
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-405sde-ii/ -- 5 rounds
 *      - Done all coding in 2:00
 *      - Interesting learning: Understanding internal Java DS http://www.thejavageek.com/core-java/
 *      - Hard problem: Largest Sum Contiguous Subarray in round 2
 *      - Topic: Thread pooling, thread queuing
 *      - Design parking lot https://www.geeksforgeeks.org/design-parking-lot-using-object-oriented-principles/
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-402-experienced-sde-2/
 *      - Done all coding in 1:45
 *      - Problem to notice: Quick sort / 3-way partitioning similar to quick sort
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-3years-experience-sde-ii/
 *      - Done all coding in 4:15
 *      - Good problem to review: Jump game 2 - BFS
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-400-sde-2/
 *      - Done all coding in 3:45
 *      - Problem to review: Serialize Bin tree
 *      - Problem to review: serialization using different order of tree traversal
 *      - Rotate matrix: My idea of using Deque. Time Complexity = O(2*circumferences + K)
 *      - Topological sort
 *      - My added problem: Given topological order of tasks, find min length of concurrency run
 *          e.g. 0->1; 0->2; 3 : return 2 because [0->1|2] and [3] can run concurrently
 *
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-396-sde-2/
 *      - Wildcard matching greedy algorithm supporting only +''
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-392-sde-2/
 *      - Done all coding in 1:30
 *
 * DONE: https://www.geeksforgeeks.org/amazon-interview-experience-set-381-sde2/
 *      - Done all coding in 1:55
 *      - Round 2 has interesting question: Tournament Tree Problem
 *      - Round 3 of chess knight is a 2-way BFS
 *
 * Doing: https://www.geeksforgeeks.org/amazon-interview-experience-set-373-sde-2/
 *
 */
public class AmazonSDE2Logs {

    static class Key {
        int freq;
        int id;
        public Key(int freq, int id) {
            this.freq = freq;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return freq == key.freq && id == key.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(freq, id);
        }
    }

    static final Map<Key, String> map = new HashMap<>();

    public static void main(String[] args) {
        map.put(new Key(1, 2), "Long");
        map.put(new Key(1, 3), "Khanh");
        map.put(new Key(1, 2), "Huy");
        System.out.println(map.size());
    }
}
