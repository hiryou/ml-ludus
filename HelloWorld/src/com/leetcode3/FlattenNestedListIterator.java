package com.leetcode3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * https://leetcode.com/problems/flatten-nested-list-iterator/description/
 */
public class FlattenNestedListIterator implements Iterator<Integer> {

    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }



    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        List<Integer> flat = new LinkedList<>();
        flatItOut(nestedList, flat);
        iter = flat.iterator();
    }

    private void flatItOut(List<NestedInteger> list, List<Integer> flat) {
        for (NestedInteger item: list) {
            if (item.isInteger()) flat.add(item.getInteger());
            else flatItOut(item.getList(), flat);
        }
    }


    private Iterator<Integer> iter;

    @Override
    public Integer next() {
        return iter.next();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }
}
