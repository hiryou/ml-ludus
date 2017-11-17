package com.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TechnicalQuestions {

    public static void main(String[] args) {
        //calculateHourMinuteHandleAngle(12., 59.);

        //findMinimumIntegerRotatedSortedArray(new int[] {6, 7, 1, 2, 3, 4, 5});

        //isRansomNoteCutFromMagazine("what is moon", new String[] {"what", "moon", "is", "moon", "what"});

        stringTokenizePrintAllCombinations("thisisname", new String[] {"is", "name", "this", "isn", "ame"});
    }

    /**
     * Given a string sentence (no space separator) and a list of words, count all ways forming the string from the list
     * of words
     */
    private static void stringTokenizePrintAllCombinations(String noSpaceSentence, String[] words) {
        Map<Character, String[]> firstCharToWords = buildFirstCharToWord(words);
        AtomicInteger count = new AtomicInteger(0);
        stringTokenizePrintAllCombinationsRecursive(0, noSpaceSentence, firstCharToWords, count);

        System.out.println("Combination count = " + count.get());
    }
    private static void stringTokenizePrintAllCombinationsRecursive(
            int startIdx, String noSpaceSentence, Map<Character, String[]> firstCharToWords, AtomicInteger count) {
        if (startIdx == noSpaceSentence.length()) {
            count.incrementAndGet();
            return;
        }

        char c = noSpaceSentence.charAt(startIdx);
        if (!firstCharToWords.containsKey(c)) return;

        for (String word: firstCharToWords.get(c)) {
            if (word.length() <= noSpaceSentence.length() - startIdx
                    && word.equals(noSpaceSentence.substring(startIdx, startIdx + word.length())) ) {
                stringTokenizePrintAllCombinationsRecursive(
                        startIdx+word.length(), noSpaceSentence, firstCharToWords, count);
            }
        }
    }
    private static Map<Character,String[]> buildFirstCharToWord(String[] words) {
        Map<Character, List<String>> result = new HashMap<>();
        for (String word: words) {
            char c = word.charAt(0);
            if (!result.containsKey(c)) {
                result.put(c, new ArrayList<>());
            }
            result.get(c).add(word);
        }
        return result.entrySet().stream().collect(
                Collectors.toMap(e -> e.getKey(), e -> e.getValue().toArray(new String[0]))
        );
    }

    /**
     * Check if a ransom note whose words were cut from a given magazine (list of words)
     * @param note
     * @param words
     */
    private static void isRansomNoteCutFromMagazine(String note, String[] words) {
        Map<String, Integer> wordFreq = new HashMap<>();

        // from word
        int count = 0;
        for (String word: note.split(" ")) {
            int freq = (wordFreq.containsKey(word)) ?wordFreq.get(word) :0 ;
            wordFreq.put(word, freq + 1);
            ++count;
        }

        // ref back to magazine
        for (String word: words) {
            if (!wordFreq.containsKey(word)) continue;

            --count;
            if (count==0) break;

            int freq = wordFreq.get(word);
            if (--freq == 0) {
                wordFreq.remove(word);
            } else {
                wordFreq.put(word, freq);
            }
        }

        System.out.println(count==0 ?"YES" :"NO");
    }

    /**
     * Simple binary search
     * Assume nums is sorted array, rotated, and was rotated
     */
    private static void findMinimumIntegerRotatedSortedArray(int[] nums) {
        if (nums.length==0) return;

        int left = 0, right = nums.length - 1;
        int mid = left;

        while (right - left > 0) {
            mid = left + (right - left)/2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }

        System.out.println("Min = " + nums[left]);
    }

    /**
     * Calculate the angle between hour and minute handle of a clock
     * Simple math calculation
     */
    private static void calculateHourMinuteHandleAngle(double hour, double minute) {
        double minuteRatio = minute / 60;

        double minuteAngle = minuteRatio * 360 % 360;

        double hourBaseAngle = hour / 12 * 360 % 360;
        double hourAddAngle = minuteRatio * 30;

        double angle = Math.abs(minuteAngle - (hourBaseAngle+hourAddAngle));
        if (angle > 180) angle = 360. - angle;
        System.out.println(angle);
    }
}
