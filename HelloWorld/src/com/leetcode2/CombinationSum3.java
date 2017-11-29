package com.leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/combination-sum-iii/description/
 */
public class CombinationSum3 {

    public static void main(String[] args) {
        CombinationSum3 p = new CombinationSum3();

        int k = 6, n = 23;
        BenchMark.run(() -> p.combinationSum3(k, n));
    }



    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        int[] a = new int[k];
        recursion(k, 0, 1, n, a, res);
        return res;
    }

    private void recursion(int k, int i, int min, int sum, int[] a, List<List<Integer>> res) {
        // stop condition
        if (i == k) {
            if (sum == 0) {
                res.add(Arrays.stream(a).boxed().collect(Collectors.toList()));
            }
            return;
        }

        for (int x=min; x<=Math.min(sum, 9); x++) {
            a[i] = x;
            recursion(k, i+1, x+1, sum - x, a, res);
        }
    }
}
