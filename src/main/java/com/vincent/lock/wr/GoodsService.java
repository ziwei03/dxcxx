package com.vincent.lock.wr;

public interface GoodsService {

    /**
     * 获取商品数量
     * @return 数量
     * @throws InterruptedException 抛出中断异常
     */
    Integer getGoodsNum() throws InterruptedException;

    /**
     * 设置商品数量
     * @param num 数量
     * @throws InterruptedException 抛出中断异常
     */
    void setGoodsNum(int num) throws InterruptedException;
}
