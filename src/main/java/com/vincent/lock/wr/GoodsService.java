package com.vincent.lock.wr;

public interface GoodsService {

    Integer getGoodsNum() throws InterruptedException;

    void setGoodsNum(int num) throws InterruptedException;
}
