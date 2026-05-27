package com.bittercode.util;

import java.util.Optional;

/**
 * Database configuration that is cloud-ready for Azure.
 * <p>
 * Values are resolved from environment variables which can be
 * populated from Azure App Configuration / Key Vault.
 */
class DatabaseConfig {

    // Environment variable names (can be mapped from Azure App Configuration)
    private static final String ENV_DB_DRIVER = "DB_DRIVER";
    private static final String ENV_DB_HOST = "DB_HOST";
    private static final String ENV_DB_PORT = "DB_PORT";
    private static final String ENV_DB_NAME = "DB_NAME";
    private static final String ENV_DB_USERNAME = "DB_USERNAME";
    private static final String ENV_DB_PASSWORD = "DB_PASSWORD";
    private static final String ENV_DB_URL = "DB_URL"; // optional full JDBC URL

    public static final String DRIVER_NAME = getEnv(ENV_DB_DRIVER).orElse("org.postgresql.Driver");

    public static final String DB_HOST = getEnv(ENV_DB_HOST).orElse("localhost");

    // Port must be externalized; default kept only as a fallback
    public static final String DB_PORT = getEnv(ENV_DB_PORT).orElse("5432");

    public static final String DB_NAME = getEnv(ENV_DB_NAME).orElse("bookstore");

    public static final String DB_USER_NAME = getEnv(ENV_DB_USERNAME).orElse("");

    // Password is expected to come from Azure Key Vault via environment variable
    public static final String DB_PASSWORD = getEnv(ENV_DB_PASSWORD).orElse("");

    /**
     * JDBC connection string. If DB_URL is provided it is used directly,
     * otherwise it is constructed from host/port/name.
     */
    public static final String CONNECTION_STRING = getEnv(ENV_DB_URL)
            .orElseGet(() -> "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME);

    private static Optional<String> getEnv(String key) {
        String value = System.getenv(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getProperty(key);
        }
        return Optional.ofNullable(value).map(String::trim).filter(v -> !v.isEmpty());
    }
}
