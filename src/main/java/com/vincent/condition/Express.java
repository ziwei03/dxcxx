package com.vincent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Express {

    public static final String SITE = "shanghai";
    private int km;
    private String site;
    private Lock lock = new ReentrantLock();
    private Condition kmCondition = lock.newCondition();
    private Condition siteCondition = lock.newCondition();

    public Express() {}

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public void changeKm() {
        lock.lock();
        try {
            km = 101;
            kmCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void changeSite() {
        lock.lock();
        try {
            site = "enping";
            siteCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void waitKm() {
        lock.lock();
        try {
            while (km <= 100) {
                try {
                    kmCondition.await();
                    System.out.println("km is notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("km is satisfied with condition");
        } finally {
            lock.unlock();
        }
    }

    public void waitSite() {
        lock.lock();
        try {
            while (SITE.equals(site)) {
                try {
                    siteCondition.await();
                    System.out.println("site is notified");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("site is satisfied with condition");
        } finally {
            lock.unlock();
        }
    }
}
