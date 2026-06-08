package com.bittercode.service;

import javax.servlet.http.HttpServletRequest;

import com.bittercode.model.StoreException;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;

public interface UserService {

    User login(UserRole role, String email, String password, HttpServletRequest request) throws StoreException;

    String register(UserRole role, User user) throws StoreException;

    boolean isLoggedIn(UserRole role, HttpServletRequest request);

    boolean logout(HttpServletRequest request);

}
