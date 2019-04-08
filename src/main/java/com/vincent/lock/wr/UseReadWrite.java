package com.vincent.lock.wr;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UseReadWrite implements GoodsService {

    private Goods goods;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();



    public UseReadWrite(Goods goods) {
        this.goods = goods;
    }

    @Override
    public Integer getGoodsNum() throws InterruptedException {
        readLock.lock();
        try {
            Thread.sleep(5);
            return goods.getStoreNum();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setGoodsNum(int num) throws InterruptedException {
        writeLock.lock();
        try {
            Thread.sleep(5);
            goods.setStoreNum(num);
        } finally {
            writeLock.unlock();
        }
    }
}
