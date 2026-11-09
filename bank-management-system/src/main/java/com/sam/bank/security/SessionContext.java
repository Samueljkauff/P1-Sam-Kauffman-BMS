package com.sam.bank.security;

import com.sam.bank.model.Customer;
import java.time.Instant;

public class SessionContext {
    public Customer customer;
    Instant loginTime;
    Instant lastActivityTime;

    public SessionContext() {
        this.customer = null;
        this.loginTime = null;
        this.lastActivityTime = null;
    }
}
