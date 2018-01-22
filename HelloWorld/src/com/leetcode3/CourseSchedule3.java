package com.leetcode3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * https://leetcode.com/problems/course-schedule-iii/solution/
 * Diary: Had to reference solution. 4th solution is very smart!
 * Explanation: See approach 3
 *  - Sort the course array by ascending order of end date
 *  - Keep 1 running variable of time indicating the current time finishing a course ith
 */
public class CourseSchedule3 {

    // solution: dynamic programming
    public int scheduleCourse(int[][] cs) {
        if (cs==null) return 0;
        int n = cs.length;
        if (n==0) return 0;

        // first sort all course by deadline date - time-spent day count
        Arrays.sort( cs, Comparator.comparing((int[] c) -> c[1]) );

        int time = 0;
        int count = 0;
        for (int i=0; i<n; i++) {
            // if can take course ith, increment the number of course takne
            if (time + cs[i][0] <= cs[i][1]) {
                time += cs[i][0];
                ++count;
            }

            // if cannot take course ith, find the previous course with maximum duration that is > duration of course ith
            // replace that course by course ith. Why doing this: We're greedily fitting number of course by removing
            // the course with too big time duration
            else {
                int maxith = i;
                for (int j=0; j < count; j++) if (cs[j][0] > cs[maxith][0]) maxith = j;

                if (maxith != i) {
                    // remove that time-consuming course, replace it by current course ith
                    time = time - cs[maxith][0] + cs[i][0];
                    // truly replace
                    cs[maxith] = cs[i];
                }
            }
        }

        return count;
    }
}
