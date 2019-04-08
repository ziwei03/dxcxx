package com.vincent.singleton;

public class DoubleCheck {

    private static volatile DoubleCheck doubleCheck;
    private DoubleCheck() {}

    public DoubleCheck getInstance() {
        if (doubleCheck == null) {
            synchronized (DoubleCheck.class) {
                if (doubleCheck == null) {
                    doubleCheck = new DoubleCheck();
                }
            }
        }
        return doubleCheck;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                DoubleCheck instance = new DoubleCheck().getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(2000);
    }
}
