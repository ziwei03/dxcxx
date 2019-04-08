package com.vincent.delayqueue;

import java.util.concurrent.DelayQueue;

public class PutOrder implements Runnable {

    private DelayQueue<ItemVO<Order>> delayQueue;

    private ItemVO<Order> itemVO;

    public PutOrder(DelayQueue<ItemVO<Order>> delayQueue, ItemVO<Order> itemVO) {
        this.delayQueue = delayQueue;
        this.itemVO = itemVO;
    }

    public DelayQueue<ItemVO<Order>> getDelayQueue() {
        return delayQueue;
    }

    public void setDelayQueue(DelayQueue<ItemVO<Order>> delayQueue) {
        this.delayQueue = delayQueue;
    }

    public ItemVO<Order> getItemVO() {
        return itemVO;
    }

    public void setItemVO(ItemVO<Order> itemVO) {
        this.itemVO = itemVO;
    }

    @Override
    public void run() {
        boolean offer = delayQueue.offer(itemVO);
        if (offer) {
            System.out.println("订单" + itemVO.getData().getName() + "失效时间开始倒计时, 当前时间：" + System.currentTimeMillis());
        }
    }
}
