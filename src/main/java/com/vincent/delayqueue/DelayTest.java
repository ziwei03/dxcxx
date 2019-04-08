package com.vincent.delayqueue;

import java.util.concurrent.DelayQueue;

public class DelayTest {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<ItemVO<Order>> delayQueue = new DelayQueue<>();
        for (int i = 0; i < 3; i++) {
            Order order = new Order("Fender" + i, 9999);
            ItemVO<Order> orderItemVO = new ItemVO<>(order, 3000 + i * 1000);
            new Thread(new PutOrder(delayQueue, orderItemVO)).start();
        }

        new Thread(new FetchOrder(delayQueue)).start();

    }
}
