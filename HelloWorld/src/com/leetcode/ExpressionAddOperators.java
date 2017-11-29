package com.leetcode;

import com.leetcode2.BenchMark;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/expression-add-operators/description/
 * @Diary: Too complicated initial thinking; Need to slow down, calm and produce simpler solution!!!
 * TODO Need review + more practice with recursion computation!!!
 */
public class ExpressionAddOperators {

    public static void main(String[] args) {
        ExpressionAddOperators p = new ExpressionAddOperators();

        //String num = "123"; int target = 6;
        //String num = ""; int target = 6;
        //String num = "105"; int target = 5;
        //String num = "3456237490"; int target = 9191;
        //String num = "123"; int target = 6;
        //String num = "123456789"; int target = 45;
        //String num = "000"; int target = 0;
        String num = "2147483647"; int target = 2147483647;
        BenchMark.run(() -> p.addOperators(num, target));
    }

    /**
     * Solution: Recursion that loops string in reverse: Reason is because you know beforehand what was the last number
     * on the right side, hence making it easy to aggregate formula result
     * @param num
     * @param target
     * @return
     */
    public List<String> addOperators(String num, int target) {
        final List<String> result = new ArrayList<>();
        if (num.trim().isEmpty()) return result;

        char[] ops = new char[num.length()];
        doRecusion(num, ops, target, num.length()-1, num.length()-1, 0, result, false, 0);

        return result;
    }

    // num[sIdx..eIdx] is the last complete number on the right
    // last factor only applies if there was a '*something' -> lastFactor = something
    private void doRecusion(
            String num, char[] ops, int target,
            int idx, int eIdx, long outcome,
            List<String> result,
            boolean pendingMul, long lastFactor) {

        // stop condition
        if (idx == -1) {
            if (outcome == target) {
                String ss = buildResult(num, ops);
                result.add(ss);
            }
            return;
        }

        // at position index idx
        long newOutcome = outcome;

        // TODO remember: do not accept number starting with 0 like '02', but '0' by itself is ok
        // TODO remember: also do not allow zero like '00' or '000' etc
        if ( idx == eIdx || (num.charAt(idx) != '0' && !isAllZeroes(num, idx, eIdx)) ) {
            // if +
            ops[idx] = '+';
            if (pendingMul) {
                newOutcome = outcome + value(num, idx, eIdx) * lastFactor;
            } else {
                newOutcome = outcome + value(num, idx, eIdx);
            }
            doRecusion(num, ops, target, idx-1, idx-1, newOutcome, result, false, 0);

            // do not apply - * or ! in front of char[0]
            if (idx == 0) return;

            // if -
            ops[idx] = '-';
            if (pendingMul) {
                newOutcome = outcome - value(num, idx, eIdx) * lastFactor;
            } else {
                newOutcome = outcome - value(num, idx, eIdx);
            }
            doRecusion(num, ops, target, idx-1, idx-1, newOutcome, result, false, 0);

            // if *
            ops[idx] = '*';
            long newLastFactor = lastFactor;
            if (pendingMul) {
                newLastFactor = value(num, idx, eIdx) * lastFactor;
            } else {
                newLastFactor = value(num, idx, eIdx);
            }
            doRecusion(num, ops, target, idx-1, idx-1, outcome, result, true, newLastFactor);
        }

        // if 2+ digit number
        // TODO remember: do not allow too big number
        if (eIdx - idx + 2 < 11 && (idx != 0 || !isAllZeroes(num, idx, eIdx))) {
            ops[idx] = '!';
            doRecusion(num, ops, target, idx-1, eIdx, outcome, result, pendingMul, lastFactor);
        }
    }

    private boolean isAllZeroes(String num, int idx, int eIdx) {
        if (eIdx == idx) return false;
        for (int i=idx; i<=eIdx; i++) {
            if (num.charAt(i) != '0') return false;
        }
        return true;
    }

    private long value(String num, int sIdx, int eIdx) {
        return Long.parseLong(num.substring(sIdx, eIdx+1));
    }

    private String buildResult(String num, char[] ops) {
        StringBuilder b = new StringBuilder();
        b.append(num.charAt(0));
        for (int i=1; i<num.length(); i++) {
            char op = ops[i];
            if (op != '!') {
                b.append(op);
            }
            b.append(num.charAt(i));
        }

        String ss = b.toString();

        return ss;
    }

}
