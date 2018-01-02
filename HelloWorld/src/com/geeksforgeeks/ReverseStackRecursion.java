package com.geeksforgeeks;

import java.util.Stack;

public class ReverseStackRecursion {

    //using Stack class for stack implementation
    static Stack<Character> st = new Stack<>();

    // Below is a recursive function that inserts an element
    // at the bottom of a stack.
    static void insert_at_bottom(char x){

        if(st.isEmpty())
            st.push(x);

        else{
	        /* All items are held in Function Call Stack until we
	           reach end of the stack. When the stack becomes
	           empty, the st.size() becomes 0, the
	           above if part is executed and the item is inserted
	           at the bottom */

            char a = st.pop();
            insert_at_bottom(x);

            //push allthe items held in Function Call Stack
            //once the item is inserted at the bottom
            st.push(a);
        }
    }

    // Below is the function that reverses the given stack using
    // insert_at_bottom()
    static void  reverse()
    {
        if(st.size()>0)
        {
	        /* Hold all items in Function Call Stack until we
	           reach end of the stack */
            char x = st.pop();
            reverse();
	        /* Insert all the items held in Function Call Stack
	           one by one from the bottom to top. Every item is
	           inserted at the bottom */
            insert_at_bottom(x);
        }
    }


    static void  reverse2() {
        int count = st.size();
        if (count == 1) return;

        for (int k=count; k>=1; k--) {
            char top = st.pop();
            recursion(top, st, k-1);
        }
    }
    static void recursion(char top, Stack<Character> st, int k) {
        if (k==0) {
            st.push(top); return;
        }

        char next = st.pop();
        recursion(top, st, k-1);
        st.push(next);
    }


    // Driver method
    public static void main(String[] args)
    {
        //push elements into the stack
        st.push('1');
        st.push('2');
        st.push('3');
        st.push('4');

        System.out.println("Original Stack");

        System.out.println(st);

        //function to reverse the stack
        //reverse();
        reverse2();

        System.out.println("Reversed Stack");

        System.out.println(st);
    }
}
