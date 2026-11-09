package com.sam.bank.security;

import com.sam.bank.model.Customer;

public class SessionManager {
    SessionContext currentSession;

    public SessionManager(SessionContext currentSession) {
        this.currentSession = currentSession;
    }

    public void setCurrentSession(Customer customer) {
        this.currentSession.customer = customer;

    }

    public boolean isLoggedIn() {
        if(currentSession.customer != null) {
            return true;
        }
        return false;
    }
}
