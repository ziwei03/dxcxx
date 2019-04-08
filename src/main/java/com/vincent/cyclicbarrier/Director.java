package com.vincent.cyclicbarrier;

/**
 * 导演类
 */
public class Director implements Runnable {


    @Override
    public void run() {
        System.out.println("导演来了");
    }
}
