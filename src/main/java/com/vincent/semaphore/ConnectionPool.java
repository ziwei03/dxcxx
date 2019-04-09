package com.vincent.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ConnectionPool {

    private static final int POOL_SIZE = 10;

    private static final LinkedList<Connection> POOL = new LinkedList<>();

    private Semaphore useful;

    private Semaphore useless;

    public ConnectionPool() {
        this.useful = new Semaphore(10);
        this.useless = new Semaphore(0);
    }

    static {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = ConnectionImpl.fechConnection();
            POOL.addLast(connection);
        }
    }

    public Connection fetchConnection() throws InterruptedException {
            useful.acquire();
            Connection connection;
            synchronized (POOL) {
                connection = POOL.removeFirst();
            }
            useless.release();
            return connection;
    }

    public void releaseConnection(Connection connection) throws InterruptedException {
            if (connection != null) {
                System.out.println("当前等待取连接的线程数：" + useful.getQueueLength() + ",可用连接数：" + useful.availablePermits());
                useless.acquire();
                synchronized (POOL) {
                    POOL.addLast(connection);
                }
                useful.release();
            }
    }
}
