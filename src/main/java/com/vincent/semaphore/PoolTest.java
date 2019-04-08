package com.vincent.semaphore;

import java.sql.Connection;
import java.util.Random;

public class PoolTest {

    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                long start = System.currentTimeMillis();
                try {
                    Connection connection = connectionPool.fetchConnection();
                    System.out.println(Thread.currentThread().getName()
                            +"获取数据库连接共耗时【"+(System.currentTimeMillis() - start)+"】ms.");
                    Thread.sleep(100 + new Random().nextInt(100));
                    connectionPool.releaseConnection(connection);
                    System.out.println(Thread.currentThread().getName() + "释放连接.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
