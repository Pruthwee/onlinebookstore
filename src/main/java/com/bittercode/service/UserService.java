package com.bittercode.service;

import com.bittercode.model.StoreException;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;

/**
 * User service abstraction.
 *
 * Session-related concerns are handled at the web layer; this service is
 * stateless to support cloud-native scaling.
 */
public interface UserService {

    User login(UserRole role, String email, String password) throws StoreException;

    String register(UserRole role, User user) throws StoreException;
}
