package com.vincent.wn;

public class ExpressTest {
    public static void main(String[] args) throws InterruptedException {
        Express express = new Express(0, Express.SITE);
        for (int i = 0; i < 3; i++) {
            new Thread(express::waitSite).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(express::waitKm).start();
        }
        Thread.sleep(2000);
        express.changeKm();
    }
}
