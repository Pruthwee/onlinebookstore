package com.bittercode.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.bittercode.constant.ResponseCode;
import com.bittercode.model.StoreException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Cloud-ready database utility using HikariCP connection pooling
 * Configured with proper timeouts for Azure cloud environment
 */
public class DBUtil {

    private static HikariDataSource dataSource;

    static {
        try {
            // Configure HikariCP connection pool with Azure-recommended settings
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DatabaseConfig.CONNECTION_STRING);
            config.setUsername(DatabaseConfig.DB_USER_NAME);
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setDriverClassName(DatabaseConfig.DRIVER_NAME);
            
            // Cloud-optimized connection pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000); // 30 seconds
            config.setIdleTimeout(600000); // 10 minutes
            config.setMaxLifetime(1800000); // 30 minutes
            config.setLeakDetectionThreshold(60000); // 1 minute
            
            // Connection validation
            config.setConnectionTestQuery("SELECT 1");
            config.setValidationTimeout(5000); // 5 seconds
            
            // Pool name for monitoring
            config.setPoolName("OnlineBookStorePool");
            
            dataSource = new HikariDataSource(config);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    /**
     * Get a connection from the pool
     * @return Database connection
     * @throws StoreException if connection cannot be obtained
     */
    public static Connection getConnection() throws StoreException {
        try {
            if (dataSource == null || dataSource.isClosed()) {
                throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
            }
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
    }

    /**
     * Close the data source (for graceful shutdown)
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
