package com.bittercode.util;

import java.util.Optional;

/**
 * Resolves configuration values from environment variables first and then
 * falls back to JVM system properties. This keeps runtime configuration
 * externalized for Azure App Configuration / Key Vault injection patterns.
 */
public final class AzureConfigResolver {

    private AzureConfigResolver() {
    }

    public static String getRequired(String envKey, String propertyKey) {
        return getOptional(envKey, propertyKey)
                .orElseThrow(() -> new IllegalStateException(
                        "Missing required configuration for keys: " + envKey + " / " + propertyKey));
    }

    public static String getRequired(String envKey) {
        return getOptional(envKey).orElseThrow(
                () -> new IllegalStateException("Missing required configuration for key: " + envKey));
    }

    public static Optional<String> getOptional(String envKey, String propertyKey) {
        String value = firstNonBlank(System.getenv(envKey), System.getProperty(propertyKey));
        return Optional.ofNullable(value);
    }

    public static Optional<String> getOptional(String envKey) {
        return Optional.ofNullable(firstNonBlank(System.getenv(envKey), null));
    }

    public static String getOrDefault(String envKey, String propertyKey, String defaultValue) {
        return getOptional(envKey, propertyKey).orElse(defaultValue);
    }

    public static String getOrDefault(String envKey, String defaultValue) {
        return getOptional(envKey).orElse(defaultValue);
    }

    private static String firstNonBlank(String primary, String secondary) {
        if (primary != null && !primary.trim().isEmpty()) {
            return primary.trim();
        }
        if (secondary != null && !secondary.trim().isEmpty()) {
            return secondary.trim();
        }
        return null;
    }
}
