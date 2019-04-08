package com.vincent.delayqueue;

import java.util.concurrent.DelayQueue;

public class FetchOrder implements Runnable {

    private DelayQueue<ItemVO<Order>> delayQueue;

    public FetchOrder(DelayQueue<ItemVO<Order>> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            System.out.println("进入等待延时队列");
            while (true) {
                ItemVO<Order> orderItemVO = delayQueue.take();
                System.out.println("订单" + orderItemVO.getData().getName() + "已经到期,当前时间：" + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
