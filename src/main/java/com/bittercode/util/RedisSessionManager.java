package com.bittercode.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cloud-ready session manager using Azure Cache for Redis
 * This is a simplified implementation that can be replaced with Spring Session + Redis
 * For production, use Spring Session with Azure Cache for Redis
 */
public class RedisSessionManager {
    
    // In-memory fallback for development (replace with actual Redis client in production)
    // For production: Use Jedis or Lettuce client with Azure Cache for Redis connection string
    private static final Map<String, Map<String, Object>> sessionStore = new ConcurrentHashMap<>();
    
    /**
     * Store session attribute
     * @param sessionId Session identifier
     * @param key Attribute key
     * @param value Attribute value
     */
    public static void setAttribute(String sessionId, String key, Object value) {
        sessionStore.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>()).put(key, value);
    }
    
    /**
     * Get session attribute
     * @param sessionId Session identifier
     * @param key Attribute key
     * @return Attribute value or null
     */
    public static Object getAttribute(String sessionId, String key) {
        Map<String, Object> session = sessionStore.get(sessionId);
        return session != null ? session.get(key) : null;
    }
    
    /**
     * Remove session attribute
     * @param sessionId Session identifier
     * @param key Attribute key
     */
    public static void removeAttribute(String sessionId, String key) {
        Map<String, Object> session = sessionStore.get(sessionId);
        if (session != null) {
            session.remove(key);
        }
    }
    
    /**
     * Invalidate entire session
     * @param sessionId Session identifier
     */
    public static void invalidateSession(String sessionId) {
        sessionStore.remove(sessionId);
    }
    
    /**
     * Check if session has attribute
     * @param sessionId Session identifier
     * @param key Attribute key
     * @return true if attribute exists
     */
    public static boolean hasAttribute(String sessionId, String key) {
        Map<String, Object> session = sessionStore.get(sessionId);
        return session != null && session.containsKey(key);
    }
    
    /**
     * Get all session attributes
     * @param sessionId Session identifier
     * @return Map of all attributes
     */
    public static Map<String, Object> getAllAttributes(String sessionId) {
        return sessionStore.getOrDefault(sessionId, new HashMap<>());
    }
}
