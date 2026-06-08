package com.bittercode.service;

import javax.servlet.http.HttpSession;

import com.bittercode.model.StoreException;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;

/**
 * User service interface
 * Cloud-ready: Session management can be externalized to Azure Cache for Redis
 */
public interface UserService {

    /**
     * Login user with role-based authentication
     * @param role User role (CUSTOMER or SELLER)
     * @param email User email
     * @param password User password
     * @param session HTTP session (can be backed by Redis)
     * @return User object if login successful
     * @throws StoreException if login fails
     */
    public User login(UserRole role, String email, String password, HttpSession session) throws StoreException;

    /**
     * Register new user
     * @param role User role
     * @param user User details
     * @return Success or failure message
     * @throws StoreException if registration fails
     */
    public String register(UserRole role, User user) throws StoreException;

    /**
     * Check if user is logged in
     * @param role User role
     * @param session HTTP session (can be backed by Redis)
     * @return true if logged in
     */
    public boolean isLoggedIn(UserRole role, HttpSession session);

    /**
     * Logout user
     * @param session HTTP session (can be backed by Redis)
     * @return true if logout successful
     */
    public boolean logout(HttpSession session);

}
