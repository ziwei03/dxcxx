package com.vincent.framework.processor;

import com.vincent.framework.delayqueue.DelayItem;
import com.vincent.framework.pool.PendingJobPool;

import java.util.concurrent.DelayQueue;

public class CheckJobProcessor {

    private static DelayQueue<DelayItem<String>> delayQueue = new DelayQueue<>();

    private CheckJobProcessor() {}

    private static class InstanceHolder {
        private static CheckJobProcessor  checkJobProcessor = new CheckJobProcessor();
    }

    public static CheckJobProcessor getInstance() {
        return InstanceHolder.checkJobProcessor;
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("守护线程【定时清除工作缓存】开启...");
    }

    private static class FetchJob implements Runnable {

        @Override
        public void run() {
            for(;;) {
                try {
                    DelayItem<String> delayItem = delayQueue.take();
                    String jobName = delayItem.getData();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println("工作【"+ jobName +"】已经过期，从队列中删除");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobName, long delayTime) {
        DelayItem<String> delayItem = new DelayItem<>(jobName, delayTime);
        if (delayQueue.offer(delayItem)) {
            System.out.println("工作【"+ jobName +"】进入延时清除队列，延时时长：" + delayTime);
        }
    }
}
