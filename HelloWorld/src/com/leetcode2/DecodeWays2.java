package com.leetcode2;

/**
 * https://leetcode.com/problems/decode-ways-ii/description/
 */
public class DecodeWays2 {

    public static void main(String[] args) {
        DecodeWays2 p = new DecodeWays2();
        //String s = "*0**4";
        //String s = "*0**45679*12345";
        //String s = "*0**45679*12345*0**45679*12345*0**45679*12345*0**45679*12345*0**45679*12345*0**45679*12345345*0**45679*12345*0**45679*12345345*0**45679*12345*0**45679*12345345*0**45679*12345*0**45679*12345345*0**45679*12345*0**45679*12345";
        //String s = "*0**45679*12345*0**45679";

        //String s = "*0**45679*12345*0**45679*12345*0**45679*12345*";
        //String s   = "*0**45679*12345*0**45679*12345*0**";
        //String s = "1003";

        String s = "**********1111111111";

        System.out.println(p.numDecodings(s));
    }


    static class BigNumer {
        static final int base = 1_000_000_007;

        int baseCount = 0;
        int value = 0;

        public BigNumer(int value) {
            this.baseCount = 0;
            this.value = value;
            this.optimize();
        }

        public BigNumer(int baseCount, int value) {
            this.baseCount = baseCount;
            this.value = value;
            this.optimize();
        }

        public BigNumer multiply(int k) {
            BigNumer b = new BigNumer(this.baseCount, this.value);
            b.baseCount *= k;

            if (b.value == 0) {}
            else if (base / b.value >= k) {
                b.value *= k;
            } else {
                int oldVal = b.value;
                for (int i=2; i<=k; i++) {
                    b.value += oldVal;
                    b.optimize();
                }
            }

            b.optimize();
            return b;
        }

        private void optimize() {
            if (value < base) return;
            this.baseCount += value / base;
            value = value % base;
        }

        public BigNumer add(BigNumer bi) {
            BigNumer b = new BigNumer(this.baseCount, this.value);
            b.baseCount += bi.baseCount;
            b.value += bi.value;
            b.optimize();
            return b;
        }
    }

    private int solutionUsingMyBigNumber(String s) {
        BigNumer a = new BigNumer(1);
        BigNumer b = new BigNumer(numDecode(s.charAt(0)));

        for (int i=1; i<s.length(); i++) {
            char c = s.charAt(i);
            BigNumer bi = b.multiply(numDecode(c));
            BigNumer ai = a.multiply(numDecode(s.charAt(i-1), c));

            a = b;
            b = ai.add(bi);
        }

        return b.value;
    }




    /**
     * Algorithm: DP with O(1) space & linear time
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        if (s.isEmpty()) return 0;

        //return solutionUsingMyBigNumber(s);
        return solutionUsingLongInteger(s);
    }

    /**
     * How it works: Imagine you already have a substring s[0..k] and
     *      f[k] = number of ways for this substring
     *      f[k-1] = number of ways for the substring s[0..k-1]
     * Now encounter new character x: s[0..k]x. There're 2 ways to introduce x to the group:
     *      1. x by itself: number of ways = f[k] * numberOfWay(x)
     *      2. s[k]x being a 2-char decode: number of ways = f[k-1] + numberOfWays(s[k]x)
     * If sum of the above 2 == 0, return 0 immediately. Else update f[k+1] to be the current sum and continue
     * @param s
     * @return
     */
    private int solutionUsingLongInteger(String s) {
        final long BASE = 1_000_000_007;
        long first = 1; long second = numDecode(s.charAt(0));

        for (int i=1; i<s.length(); i++) {
            char c = s.charAt(i);
            long oneWay = (second * numDecode(c)) % BASE;
            long anotherWay = (first * numDecode(s.charAt(i-1), c)) % BASE;

            first = second;
            second = (oneWay + anotherWay) % BASE;
        }

        return (int)second;
    }

    /**
     * This is the core and most complicated function of this problem to get the answer right
     * @param c1
     * @param c2
     * @return
     */
    // count number of way "c1c2" makes a valid 2-char decoding
    private int numDecode(char c1, char c2) {
        if (c1 == '*') {
            // then c1 can only be either '1' or '2', then:
            if ('0' <= c2 && c2 <= '6') return 2;
            if (c2 == '*') return 15;
            return 1;
        }

        if (c1 == '0' || c1 >= '3') return 0;

        if (c1 == '1') {
            if (c2 == '*') return 9;
            return 1;
        }

        // only case left is c1 == '2'
        if (c2 == '*') return 6;
        if (c2 >= '7') return 0;
        return 1;
    }

    // count number of way "c" makes a valid 1-char decoding
    private int numDecode(char c) {
        if (c == '0') return 0;
        if (c == '*') return 9;
        return 1; // expecting valid 1->9 from input string
    }
}
