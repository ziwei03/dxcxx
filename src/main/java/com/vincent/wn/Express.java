package com.vincent.wn;

public class Express {

    public static final String SITE = "shanghai";
    private int km;
    private String site;

    public Express() {}

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm() {
        km = 101;
        notifyAll();
    }

    public synchronized void changeSite() {
        site = "enping";
        notifyAll();
    }

    public synchronized void waitKm() {
        while (km <= 100) {
            try {
                wait();
                System.out.println("km is notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("km is satisfied with condition");
    }

    public synchronized void waitSite() {
        while (SITE.equals(site)) {
            try {
                wait();
                System.out.println("site is notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("site is satisfied with condition");
    }
}
