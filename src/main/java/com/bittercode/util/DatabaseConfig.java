package com.bittercode.util;

class DatabaseConfig {

    private DatabaseConfig() {
    }

    public static final String DRIVER_NAME = AzureConfigResolver.getOrDefault("DB_DRIVER", "db.driver",
            "com.mysql.cj.jdbc.Driver");
    public static final String DB_HOST = AzureConfigResolver.getRequired("DB_HOST", "db.host");
    public static final String DB_PORT = AzureConfigResolver.getOrDefault("DB_PORT", "db.port", "3306");
    public static final String DB_NAME = AzureConfigResolver.getRequired("DB_NAME", "db.name");
    public static final String DB_USER_NAME = AzureConfigResolver.getRequired("DB_USERNAME", "db.username");
    public static final String DB_PASSWORD = AzureConfigResolver.getRequired("DB_PASSWORD", "db.password");
    public static final String CONNECTION_STRING = buildConnectionString();

    private static String buildConnectionString() {
        if (DB_HOST.startsWith("jdbc:")) {
            if (DB_HOST.contains("/${DB_NAME}")) {
                return DB_HOST.replace("${DB_NAME}", DB_NAME);
            }
            if (DB_HOST.endsWith("/" + DB_NAME) || DB_HOST.contains("/" + DB_NAME + "?")) {
                return DB_HOST;
            }
            if (DB_HOST.endsWith("/")) {
                return DB_HOST + DB_NAME;
            }
            return DB_HOST + "/" + DB_NAME;
        }
        return "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    }
}
