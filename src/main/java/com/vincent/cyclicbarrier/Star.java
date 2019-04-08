package com.vincent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 明星类
 */
public class Star implements Runnable {

    private CyclicBarrier cyclicBarrier;

    private String name;

    private long sleepTime;

    public Star(CyclicBarrier cyclicBarrier, String name, long sleepTime) {
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try {
            System.out.println("明星" + name + "开始睡觉");
            Thread.sleep(sleepTime);
            System.out.println("明星" + name + "睡完觉了");
            cyclicBarrier.await();
            System.out.println("明星" + name + "出发进行拍摄");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Director());
        Star startA = new Star(cyclicBarrier, "陈赫", 1000);
        Star startB = new Star(cyclicBarrier, "郑恺", 2000);
        Star startC = new Star(cyclicBarrier, "Angelababy", 3000);
        new Thread(startA).start();
        new Thread(startB).start();
        new Thread(startC).start();
    }
}
