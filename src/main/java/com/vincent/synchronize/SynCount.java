package com.vincent.synchronize;

import java.util.concurrent.CountDownLatch;

public class SynCount {

    private int count;

    public SynCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void addCount() {
        count ++;
    }

    private static class MyThread extends Thread {

        private SynCount synCount;

        private CountDownLatch countDownLatch;

        public MyThread(SynCount synCount, CountDownLatch countDownLatch) {
            this.synCount = synCount;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                synCount.addCount();
            }
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynCount synCount = new SynCount(0);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            MyThread myThread = new MyThread(synCount, countDownLatch);
            myThread.start();
        }
        countDownLatch.await();
        System.out.println(synCount.getCount());
    }
}
