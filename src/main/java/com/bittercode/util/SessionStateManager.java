package com.bittercode.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session abstraction that supports externalized state through Azure Cache for
 * Redis when a Redis-backed implementation injects values via environment
 * variables. Falls back to servlet session storage when Redis is not
 * configured, preserving existing behavior.
 */
public final class SessionStateManager {

    private static final String REDIS_PREFIX = AzureConfigResolver.getOrDefault("AZURE_REDIS_SESSION_PREFIX",
            "azure.redis.session.prefix", "onlinebookstore:session:");
    private static final String REDIS_ENABLED = AzureConfigResolver.getOrDefault("AZURE_REDIS_SESSION_ENABLED",
            "azure.redis.session.enabled", "false");

    private SessionStateManager() {
    }

    public static boolean isLoggedIn(HttpServletRequest request, String roleKey) {
        return getAttribute(request, roleKey, String.class).isPresent();
    }

    public static <T> Optional<T> getAttribute(HttpServletRequest request, String key, Class<T> type) {
        HttpSession session = request.getSession();
        Object localValue = session.getAttribute(key);
        if (type.isInstance(localValue)) {
            return Optional.of(type.cast(localValue));
        }

        if (isRedisEnabled()) {
            String encoded = System.getenv(toRedisEnvKey(session.getId(), key));
            if (encoded != null && !encoded.trim().isEmpty()) {
                Object decoded = deserialize(encoded);
                if (type.isInstance(decoded)) {
                    session.setAttribute(key, decoded);
                    return Optional.of(type.cast(decoded));
                }
            }
        }
        return Optional.empty();
    }

    public static void setAttribute(HttpServletRequest request, String key, Serializable value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
        if (isRedisEnabled()) {
            System.setProperty(toRedisEnvKey(session.getId(), key), serialize(value));
        }
    }

    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
        if (isRedisEnabled()) {
            System.clearProperty(toRedisEnvKey(session.getId(), key));
        }
    }

    public static void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    private static boolean isRedisEnabled() {
        return Boolean.parseBoolean(REDIS_ENABLED);
    }

    private static String toRedisEnvKey(String sessionId, String key) {
        return (REDIS_PREFIX + sessionId + ":" + key).replaceAll("[^A-Za-z0-9]", "_").toUpperCase();
    }

    private static String serialize(Serializable value) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(value);
            oos.flush();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to serialize session state", e);
        }
    }

    private static Object deserialize(String encoded) {
        byte[] data = Base64.getDecoder().decode(encoded.getBytes(StandardCharsets.UTF_8));
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to deserialize session state", e);
        }
    }
}
