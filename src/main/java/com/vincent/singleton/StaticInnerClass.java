package com.vincent.singleton;

public class StaticInnerClass {

    private StaticInnerClass() {}
    private static class InstanceHolder {
        private static StaticInnerClass staticInnerClass = new StaticInnerClass();
    }

    public StaticInnerClass getInstance() {
        return InstanceHolder.staticInnerClass;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                StaticInnerClass instance = new StaticInnerClass().getInstance();
                System.out.println(instance);
            }).start();
        }
        Thread.sleep(2000);
    }
}
