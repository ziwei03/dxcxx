package com.vincent.lock.wr;

public class GoodsTest {

    private static class ReadThread extends Thread {

        private GoodsService goodsService;

        public ReadThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                    goodsService.getGoodsNum();
                }
                System.out.println(Thread.currentThread().getName()+"读取商品数据耗时："
                        +(System.currentTimeMillis()-start)+"ms" + "商品剩余库存：" + goodsService.getGoodsNum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WriteThread extends Thread {
        private GoodsService goodsService;
        public WriteThread(GoodsService goodsService) {
            this.goodsService = goodsService;
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(50);
                    goodsService.setGoodsNum(1);
                }
                System.out.println(Thread.currentThread().getName()
                        +"写商品数据耗时："+(System.currentTimeMillis()-start)+"ms---------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Goods goods = new Goods("iphone10", 10000, 100000);
        GoodsService goodsService = new UseReadWriteImpl(goods);
        for (int i = 0; i < 10; i++) {
            WriteThread writeThread = new WriteThread(goodsService);
            for (int j = 0; j < 100; j++) {
                new ReadThread(goodsService).start();
            }
            Thread.sleep(50);
            writeThread.start();
        }
    }
}
