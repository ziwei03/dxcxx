package com.vincent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

    private CountDownLatch countDownLatch;

    private String name;

    public Worker(CountDownLatch countDownLatch, String name) {
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("工人" + name + "开始干活");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("工人" + name + "干完活了");
        countDownLatch.countDown();
    }
}
