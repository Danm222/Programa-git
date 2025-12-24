package com.rura.util;

import java.sql.Connection;
import java.sql.SQLException;

public class databaseConnection {

    private static final String URL = "dbc:postgresql://localhost:5432/ruralia";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed() || !connection.isValid(2)) {
            connection = java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        }
        return connection;
    }

}
