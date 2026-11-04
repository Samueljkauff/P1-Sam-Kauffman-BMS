package com.sam.bank.model;

import java.time.Instant;

public class Customer {
    long customerId;
    String fullName;
    String email;
    String phone;
    String userName;
    String password;
    CustomerStatus status;
    int failedLoginCount;
    Instant lockedUntil;
    Instant createdAt;
    Instant updatedAt;

    public Customer(String fullName, String email, String phone, String userName, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
    }
}

