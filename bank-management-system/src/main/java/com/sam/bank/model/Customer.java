package com.sam.bank.model;

import java.time.Instant;

public class Customer {
    long customerId;
    String fullName;
    String email;
    String phone;
    String username;
    String password;
    CustomerStatus status;
    int failedLoginCount;
    Instant lockedUntil;
    Instant createdAt;
    Instant updatedAt;

    public Customer(String fullName, String email, String phone, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return password;
    }
}

