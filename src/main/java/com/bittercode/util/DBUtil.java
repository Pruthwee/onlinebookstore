package com.bittercode.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.bittercode.constant.ResponseCode;
import com.bittercode.model.StoreException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBUtil {

    private static final HikariDataSource DATA_SOURCE;

    static {
        try {
            Class.forName(DatabaseConfig.DRIVER_NAME);
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(DatabaseConfig.DRIVER_NAME);
            config.setJdbcUrl(DatabaseConfig.CONNECTION_STRING);
            config.setUsername(DatabaseConfig.DB_USER_NAME);
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setConnectionTimeout(Long.parseLong(
                    AzureConfigResolver.getOrDefault("DB_CONNECTION_TIMEOUT_MS", "db.connection.timeout.ms", "10000")));
            config.setValidationTimeout(Long.parseLong(
                    AzureConfigResolver.getOrDefault("DB_VALIDATION_TIMEOUT_MS", "db.validation.timeout.ms", "5000")));
            config.setIdleTimeout(Long.parseLong(
                    AzureConfigResolver.getOrDefault("DB_IDLE_TIMEOUT_MS", "db.idle.timeout.ms", "600000")));
            config.setMaxLifetime(Long.parseLong(
                    AzureConfigResolver.getOrDefault("DB_MAX_LIFETIME_MS", "db.max.lifetime.ms", "1800000")));
            config.setMaximumPoolSize(Integer.parseInt(
                    AzureConfigResolver.getOrDefault("DB_MAX_POOL_SIZE", "db.max.pool.size", "10")));
            config.setMinimumIdle(Integer.parseInt(
                    AzureConfigResolver.getOrDefault("DB_MIN_IDLE", "db.min.idle", "2")));
            DATA_SOURCE = new HikariDataSource(config);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DBUtil() {
    }

    public static Connection getConnection() throws StoreException {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
    }
}
