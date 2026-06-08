    public User login(UserRole role, String email, String password, String sessionId) throws StoreException;
    public boolean isLoggedIn(UserRole role, String sessionId);

    public boolean logout(String sessionId);

    public boolean logout(HttpSession session);

}
