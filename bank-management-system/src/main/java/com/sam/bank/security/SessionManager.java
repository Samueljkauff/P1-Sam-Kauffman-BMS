package com.sam.bank.security;

public class SessionManager {
    SessionContext currentSession;

    public SessionManager() {
        this.currentSession = null;
    }

    public boolean isLoggedIn() {
        return false;
    }
}
