package com.leetcode;

import lombok.RequiredArgsConstructor;

import java.util.*;

/**
 * https://leetcode.com/problems/number-of-atoms/description/
 */
public class NumberOfAtoms {

    public static void main(String[] args) {
        String input = "K4(ON(SO3)2)2";
        String result = new NumberOfAtoms().countOfAtoms(input);

        System.out.println(result);
    }

    public String countOfAtoms(String formula) {
        List<Item> items = BuildItems(formula);

        List<Map<String, Integer>> levelAtomToCount = new ArrayList<>();
        levelAtomToCount.add(new HashMap<>());

        for (Item item: items) {
            switch (item.type) {
                case Atom:
                    aggregateLastLevel(levelAtomToCount, item);
                    break;
                case Open:
                    levelAtomToCount.add(new HashMap<>());
                    break;
                case Close:
                    mergeLastLevelUp(levelAtomToCount, item);
                    break;
                default:
                    break;
            }
        }

        return formatResult(levelAtomToCount.get(0));
    }

    private String formatResult(Map<String, Integer> atomToCount) {
        TreeMap<String, Integer> sortedAtoms = new TreeMap<>(atomToCount);
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, Integer> entry: sortedAtoms.entrySet()) {
            b.append(entry.getKey() + (entry.getValue()>1 ?entry.getValue() :""));
        }

        return b.toString();
    }

    private void mergeLastLevelUp(List<Map<String, Integer>> levelAtomToCount, Item closeItem) {
        if (closeItem.type != ItemType.Close) throw new RuntimeException("Must be type Close!");
        if (levelAtomToCount.size() == 1) return;

        Map<String, Integer> lastLevel = levelAtomToCount.get(levelAtomToCount.size()-1);
        if (closeItem.count > 1) {
            int multiplier = closeItem.count;
            for (Map.Entry<String, Integer> entry: lastLevel.entrySet()) {
                String atomString = entry.getKey();
                int existingCount = entry.getValue();
                lastLevel.put(atomString, existingCount * multiplier);
            }
        }

        Map<String, Integer> prevLevel = levelAtomToCount.get(levelAtomToCount.size()-2);

        for (Map.Entry<String, Integer> entry: lastLevel.entrySet()) {
            String atomString = entry.getKey(); int lastLevelCount = entry.getValue();
            if (prevLevel.containsKey(atomString)) {
                int prevCount = prevLevel.get(atomString);
                prevLevel.put(atomString, prevCount + lastLevelCount);
            } else {
                prevLevel.put(atomString, lastLevelCount);
            }
        }

        // null out the last level
        int lastIndex = levelAtomToCount.size()-1;
        levelAtomToCount.remove(lastIndex);
    }

    private void aggregateLastLevel(List<Map<String, Integer>> levelAtomToCount, Item atomItem) {
        if (atomItem.type != ItemType.Atom) throw new RuntimeException("Must be type Atom!");

        Map<String, Integer> lastLevel = levelAtomToCount.get(levelAtomToCount.size()-1);
        int existingCount = lastLevel.containsKey(atomItem.element)
                ?lastLevel.get(atomItem.element)
                :0 ;
        lastLevel.put(atomItem.element, existingCount + atomItem.count);
    }

    private List<Item> BuildItems(String formula) {
        List<Item> items = new ArrayList<>();

        int startIdx = 0;
        while (startIdx < formula.length()) {
            char c = formula.charAt(startIdx);

            // if current c is (
            if (c == '(') {
                items.add(new Item(ItemType.Open, "", 0));
                ++startIdx;
            }
            // if current c is ), try to get the following count if applicable
            else if (c == ')') {
                int nextIdx = startIdx + 1;
                while ( nextIdx < formula.length() && isDigit(formula.charAt(nextIdx)) ) {
                    ++nextIdx;
                }

                int followingCount = 1;
                if (startIdx+1 < nextIdx) {
                    followingCount = Integer.parseInt(formula.substring(startIdx+1, nextIdx));
                }
                items.add(new Item(ItemType.Close, "", followingCount));
                startIdx = nextIdx;
            }
            // if signal of an Atom
            else if (isUpperCase(c)) {
                int nextIdx = startIdx + 1;

                // has to follow with 0 or more lower-case chars
                while ( nextIdx < formula.length() && isLowerCase(formula.charAt(nextIdx)) ) {
                    ++nextIdx;
                }
                String atomString = formula.substring(startIdx, nextIdx);

                // then follow by 0 or more digit signalling the count of the atom
                startIdx = nextIdx;
                while ( nextIdx < formula.length() && isDigit(formula.charAt(nextIdx)) ) {
                    ++nextIdx;
                }
                int atomCount = 1;
                if (startIdx < nextIdx) {
                    atomCount = Integer.parseInt(formula.substring(startIdx, nextIdx));
                }

                items.add(new Item(ItemType.Atom, atomString, atomCount));

                startIdx = nextIdx;
            }
            // exception detection
            else {
                throw new RuntimeException("Should not happen!");
            }
        }

        return items;
    }

    private boolean isUpperCase(char c) {
        return 'A' <= c && c <= 'Z';
    }

    private boolean isLowerCase(char c) {
        return 'a' <= c && c <= 'z';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    @RequiredArgsConstructor
    class Item {
        final ItemType type;

        final String element; // if type is Atom, default ""
        final int count; // if type is Atom or Count or Close, default 0
    }

    enum ItemType {
        Open, Close, Atom
    }
}
