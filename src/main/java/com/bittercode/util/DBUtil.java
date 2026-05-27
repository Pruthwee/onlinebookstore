package com.bittercode.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bittercode.constant.ResponseCode;
import com.bittercode.model.StoreException;

/**
 * Simple connection utility.
 * <p>
 * Note: For true cloud-native readiness a connection pool such as HikariCP
 * should be used. Here we keep the existing behaviour but ensure that
 * configuration is externalized and no hard-coded credentials/ports are used.
 */
public class DBUtil {

    private static Connection connection;

    static {
        try {
            Class.forName(DatabaseConfig.DRIVER_NAME);
            connection = DriverManager.getConnection(DatabaseConfig.CONNECTION_STRING,
                    DatabaseConfig.DB_USER_NAME, DatabaseConfig.DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws StoreException {
        if (connection == null) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
        return connection;
    }
}
