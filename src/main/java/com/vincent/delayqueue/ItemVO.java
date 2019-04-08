package com.vincent.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ItemVO<T> implements Delayed {

    private T data;
    private long delayTime;

    public ItemVO(T data, long delayTime) {
        this.data = data;
        this.delayTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(getDelayTime() - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
    }
}
