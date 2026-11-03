package com.sam.bank.security;

import com.sam.bank.model.Customer;
import java.time.Instant;

public class SessionContext {
    public Customer customer;
    Instant loginTime;
    Instant lastActivityTime;
}
