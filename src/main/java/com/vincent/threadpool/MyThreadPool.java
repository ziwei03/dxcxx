package com.vincent.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyThreadPool {

    private static final int WORK_NUM = 5;
    private static final int THREAD_NUM = 100;

    private int work_num;

    private final BlockingQueue<Runnable> queue;
    private WorkThread[] workThreads;

    public MyThreadPool() {
        this(WORK_NUM, THREAD_NUM);
    }

    public MyThreadPool(int workNum, int threadNum) {
        this.work_num = workNum;
        queue = new ArrayBlockingQueue<>(threadNum);
        workThreads = new WorkThread[work_num];
        for (int i = 0; i < work_num; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    private class WorkThread extends Thread {

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Runnable runnable = queue.take();
                    System.out.println(getId()+" ready exec :" + runnable);
                    runnable.run();
                }
            } catch (Exception e) {
                // TODO: 2019/4/3
            }
        }

        public void stopWork() {
            interrupt();
        }
    }

    public void execute(Runnable runnable) {
        try {
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        System.out.println("MyThreadPool is closing ...");
        for (int i = 0; i < work_num; i++) {
            workThreads[i].stopWork();
            workThreads[i] = null; // help gc
        }
        queue.clear();
    }

    @Override
    public String toString() {
        return "WorkThread number:" + work_num
                + "  wait task number:" + queue.size();
    }
}
