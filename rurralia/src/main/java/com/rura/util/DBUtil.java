package com.rura.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utilidad para gestionar conexiones a la base de datos PostgreSQL
 */
public class DBUtil {

    // Configuración de conexión a PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/ruralia";
    private static final String USER = "postgres";
    private static final String PASS = "123";

    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de PostgreSQL (opcional en versiones modernas)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL no encontrado", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Cierra un objeto Connection de forma segura
     * @param conn Connection a cerrar
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cierra un objeto Statement de forma segura
     * @param stmt Statement a cerrar
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cierra un objeto PreparedStatement de forma segura
     * @param pstmt PreparedStatement a cerrar
     */
    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Cierra un objeto ResultSet de forma segura
     * @param rs ResultSet a cerrar
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

