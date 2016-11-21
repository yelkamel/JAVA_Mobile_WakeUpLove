package com.youceferie.wakeuplove;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by YouCef on 27/02/2016.
 */
public class SingletonConnectionPool implements ConnectionPool {

    private final String jdbcUrl;
    private Connection connection;

    public SingletonConnectionPool(String jdbcUrl) {
        this.jdbcUrl = Objects.requireNonNull(jdbcUrl, "jdbcUrl");
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbcUrl);
        }
        return connection;
    }

}
