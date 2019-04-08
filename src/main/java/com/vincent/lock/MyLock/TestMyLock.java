package com.vincent.lock.MyLock;

import java.util.concurrent.locks.Lock;

public class TestMyLock {

    private final Lock myLock = new MyLock();

    public void test() throws InterruptedException {
        myLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is in test");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " is out test");
        } finally {
            myLock.unlock();
        }
    }

    private static class MyThread extends Thread {

        private TestMyLock testMyLock;

        public MyThread(TestMyLock testMyLock) {
            this.testMyLock = testMyLock;
        }

        @Override
        public void run() {
            try {
                testMyLock.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        TestMyLock testMyLock = new TestMyLock();
        new MyThread(testMyLock).start();
        new MyThread(testMyLock).start();
        new MyThread(testMyLock).start();
        new MyThread(testMyLock).start();
    }
}
