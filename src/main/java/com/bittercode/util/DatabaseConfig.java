package com.bittercode.util;

/**
 * Cloud-ready database configuration using environment variables
 * Compatible with Azure App Configuration and Azure Key Vault
 */
class DatabaseConfig {

    // Read configuration from environment variables for cloud compatibility
    // These can be set via Azure App Configuration or Azure App Service configuration
    public final static String DRIVER_NAME = getEnvOrDefault("DB_DRIVER", "com.mysql.cj.jdbc.Driver");
    public final static String DB_HOST = getEnvOrDefault("DB_HOST", "jdbc:mysql://localhost");
    public final static String DB_PORT = getEnvOrDefault("DB_PORT", "3306");
    public final static String DB_NAME = getEnvOrDefault("DB_NAME", "onlinebookstore");
    public final static String DB_USER_NAME = getEnvOrDefault("DB_USERNAME", "root");
    public final static String DB_PASSWORD = getEnvOrDefault("DB_PASSWORD", "root");
    public final static String CONNECTION_STRING = DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    /**
     * Helper method to read environment variables with fallback defaults
     * @param envKey Environment variable key
     * @param defaultValue Default value if environment variable is not set
     * @return Environment variable value or default
     */
    private static String getEnvOrDefault(String envKey, String defaultValue) {
        String value = System.getenv(envKey);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
}
