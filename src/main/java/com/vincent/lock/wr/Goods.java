package com.vincent.lock.wr;

public class Goods {

    private String name;

    private double totalMoney;

    private int storeNum;

    public Goods(String name, double totalMoney, int storeNum) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNum = storeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum -= storeNum;
    }
}
