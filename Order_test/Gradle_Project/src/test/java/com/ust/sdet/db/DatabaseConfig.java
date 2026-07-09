package com.ust.sdet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfig {

    private DatabaseConfig() {}

    public static Connection getConnection() throws SQLException {

        String url = System.getProperty(
                "db.url",
                "jdbc:postgresql://localhost:5432/testdb"
        );

        String user = System.getProperty(
                "db.user",
                "postgres"
        );

        String password = System.getProperty(
                "db.password",
                "postgres"
        );

        return DriverManager.getConnection(url, user, password);
    }
}