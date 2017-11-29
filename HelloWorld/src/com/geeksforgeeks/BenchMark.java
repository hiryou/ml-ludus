package com.geeksforgeeks;

import java.util.concurrent.Callable;

public class BenchMark {

    public static void run(Callable<Object> func) {
        try {
            long startTime = System.currentTimeMillis();
            Object result = func.call();
            long endTime = System.currentTimeMillis();

            System.out.println(result);
            System.out.println(" * " + (endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(Runnable func) {
        try {
            long startTime = System.currentTimeMillis();
            func.run();
            long endTime = System.currentTimeMillis();

            System.out.println((endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
