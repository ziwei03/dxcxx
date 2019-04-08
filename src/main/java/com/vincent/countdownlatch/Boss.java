package com.vincent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class Boss implements Runnable {

    private CountDownLatch countDownLatch;

    public Boss(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("老板正在等待所有工人们干活");
            countDownLatch.await();
            System.out.println("工人们都干完活了，老板开始检查工作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Boss boss = new Boss(countDownLatch);
        Worker workerA = new Worker(countDownLatch, "张三");
        Worker workerB = new Worker(countDownLatch, "李四");
        Worker workerC = new Worker(countDownLatch, "王五");
        new Thread(boss).start();
        new Thread(workerA).start();
        new Thread(workerB).start();
        new Thread(workerC).start();
    }
}
