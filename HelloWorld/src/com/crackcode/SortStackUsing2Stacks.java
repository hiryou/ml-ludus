package com.crackcode;

import java.util.Arrays;
import java.util.Stack;

public class SortStackUsing2Stacks {

    public static void main(String[] args) {
        SortStackUsing2Stacks p = new SortStackUsing2Stacks();

        Stack<Integer> sta = new Stack<>();
        sta.addAll(Arrays.asList(3, 4, 1, 3, 5, 4,6));
        p.sort(sta, new Stack<>());
        System.out.println(sta);
    }

    void sort(Stack<Integer> sta, Stack<Integer> stb) {
        int n = sta.size();
        while (n > 0) {
            Integer max = moveFromTo(n, sta, stb);
            Integer max2 = moveFromTo(n-1, stb, sta);

            if (max != null) stb.add(max);
            if (max2 != null) stb.add(max2);

            n -= 2;
        }

        Integer max = moveFromTo(stb.size(), stb, sta);
        sta.add(max);
    }

    private Integer moveFromTo(int n, Stack<Integer> sta, Stack<Integer> stb) {
        if (n <= 0) return null;
        Integer max = !sta.isEmpty() ?sta.pop() :null ;
        int count = max != null ?1 :0 ;
        while (count < n) {
            int k = sta.pop();
            if (k > max) {
                stb.add(max);
                max = k;
            } else {
                stb.add(k);
            }
            ++count;
        }

        return max;
    }
}
