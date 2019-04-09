package com.vincent.framework.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayItem<T> implements Delayed {

    private T data;
    private long delayTime;

    public DelayItem(T data, long delayTime) {
        this.data = data;
        this.delayTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    public T getData() {
        return data;
    }

    public long getDelayTime() {
        return delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(getDelayTime() - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long l = this.delayTime - o.getDelay(TimeUnit.NANOSECONDS);
        return l == 0 ? 0 : (l < 0) ? -1 : 1;
    }
}
