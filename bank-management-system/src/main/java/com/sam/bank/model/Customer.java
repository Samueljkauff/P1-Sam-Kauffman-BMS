package com.sam.bank.model;

import java.time.Instant;

public class Customer {
    private long customerId;
    private String fullName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private CustomerStatus status;
    private int failedLoginCount;
    private Instant lockedUntil;
    private Instant createdAt;
    private Instant updatedAt;

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

    public void setID(long id) {
        this.customerId = id; 
    }
}

