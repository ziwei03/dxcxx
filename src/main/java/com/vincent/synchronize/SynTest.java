package com.vincent.synchronize;

public class SynTest {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private synchronized void syncMethod() {
        try {
            System.out.println(Thread.currentThread().getName() + " is into syncMethod....");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " is out syncMethod....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void synAndStaticMethod() {
        try {
            System.out.println(Thread.currentThread().getName() + " is into synAndStaticMethod");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " is out synAndStaticMethod");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void test() {
            System.out.println(Thread.currentThread().getName() + " is in test");
            for (int i = 0; i < 100000; i ++) {
                count ++;
            }
            System.out.println(Thread.currentThread().getName() + " is out test");
    }

    public static class MyThreadStatic extends Thread {

        private SynTest synTest;

        public MyThreadStatic(SynTest synTest) {
            this.synTest = synTest;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running");
            synAndStaticMethod();
        }
    }

    public static class TestThread extends Thread {

        private SynTest synTest;

        public TestThread(SynTest synTest) {
            this.synTest = synTest;
        }

        @Override
        public void run() {
            synTest.test();
            System.out.println(synTest.getCount());
        }
    }


    public static class MyThread extends Thread {

        private SynTest synTest;

        public MyThread(String threadName, SynTest synTest) {
            super(threadName);
            this.synTest = synTest;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running");
            synTest.syncMethod();
        }
    }

    public static void main(String[] args) {
        SynTest synTest = new SynTest();
//        SynTest synTest1 = new SynTest();
//        MyThread threadA = new MyThread("threadA", synTest);
//        //MyThread threadB = new MyThread("threadB", synTest1);
//        MyThreadStatic myThreadStatic = new MyThreadStatic(synTest);
//        MyThreadStatic myThreadStatic1 = new MyThreadStatic(synTest1);
//        //threadA.start();
////        myThreadStatic.start();
////        myThreadStatic1.start();
//        //threadB.start();

        TestThread testThread = new TestThread(synTest);
        TestThread testThread1 = new TestThread(synTest);
        testThread.start();
        testThread1.start();
        //System.out.println(synTest.getCount());
    }

}
