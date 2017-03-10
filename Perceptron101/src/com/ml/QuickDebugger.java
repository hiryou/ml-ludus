package com.ml;

/**
 * Created by longuy on 3/9/2017.
 */
public class QuickDebugger {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello world! Do you have a boyfriend?");
        IPerson p = new Person1();
        p.printAge();
        System.out.println(args.length);
    }

    private static interface IPerson {

        void printName();

        void printAge();
    }

    static class Person1 implements IPerson {

        @Override
        public void printName() {
            IPerson p = new Person1();
        }

        @Override
        public void printAge() {

        }
    }

    static class Person2 implements IPerson {

        @Override
        public void printName() {

        }

        @Override
        public void printAge() {
            IPerson p = new Person1();
        }
    }
}
