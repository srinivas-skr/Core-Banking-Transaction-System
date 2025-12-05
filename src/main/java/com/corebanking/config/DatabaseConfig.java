package com.corebanking.config;

/**
 * Centralized database configuration.
 * In production, these would be loaded from environment variables or a properties file.
 */
public final class DatabaseConfig {

    // Oracle XE Connection Settings
    public static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public static final String DB_USER = "banking_user";
    public static final String DB_PASSWORD = "pass123";
    public static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";

    // Connection Pool Settings (for future enhancement)
    public static final int MAX_POOL_SIZE = 10;
    public static final int CONNECTION_TIMEOUT_MS = 5000;

    private DatabaseConfig() {
        // Prevent instantiation - utility class
    }
}
