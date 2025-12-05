package com.corebanking.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection factory.
 * Handles JDBC connection lifecycle for Oracle Database.
 */
public class ConnectionManager {

    static {
        try {
            Class.forName(DatabaseConfig.DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Oracle JDBC Driver not found. Ensure ojdbc6.jar is in classpath.");
        }
    }

    /**
     * Creates a new database connection.
     * Caller is responsible for closing the connection.
     *
     * @return Active database connection
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DatabaseConfig.JDBC_URL,
            DatabaseConfig.DB_USER,
            DatabaseConfig.DB_PASSWORD
        );
    }

    /**
     * Safely closes a connection, suppressing exceptions.
     *
     * @param connection Connection to close (can be null)
     */
    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log in production, suppress for now
            }
        }
    }
}
