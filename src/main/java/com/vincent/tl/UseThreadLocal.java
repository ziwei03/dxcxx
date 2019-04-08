package com.vincent.tl;

public class UseThreadLocal {

    private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

    private void addThreadLocal(Integer i) {
        threadLocal.set(i);
    }

    public ThreadLocal<Integer> getThreadLocal() {
        return threadLocal;
    }

    public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
        this.threadLocal = threadLocal;
    }

    private static class MyThread extends Thread {

        private UseThreadLocal useThreadLocal;

        public MyThread(UseThreadLocal useThreadLocal) {
            this.useThreadLocal = useThreadLocal;
        }

        @Override
        public void run() {
            useThreadLocal.addThreadLocal((int) (Thread.currentThread().getId() + useThreadLocal.getThreadLocal().get()));
            System.out.println("ThreadId =" + Thread.currentThread().getId() + " threadLocal = " + useThreadLocal.getThreadLocal().get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal useThreadLocal = new UseThreadLocal();
        for(int i = 0; i < 3; i ++) {
            new MyThread(useThreadLocal).start();
        }
    }
}
