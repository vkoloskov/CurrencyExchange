package com.petprojects.currencyexchange.utils;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {
    static final String CONNECTION_URL = "jdbc:sqlite:" + ConnectionManager.class.getResource("/currency_exchange.db");
    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initConnectionPool();
    }

    private static void initConnectionPool() {
        int poolSize = DEFAULT_POOL_SIZE;
        pool = new ArrayBlockingQueue(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    ((proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) :
                            method.invoke(connection, args)));
            pool.add(proxyConnection);
        }
    }
    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static Connection open() {
        try {
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ConnectionManager() {}
}
