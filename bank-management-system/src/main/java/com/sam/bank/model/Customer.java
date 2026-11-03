package com.sam.bank.model;

import java.time.Instant;

public class Customer {
    long customerId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String userName;
    String password;
    CustomerStatus status;
    int failedLoginCount;
    Instant lockedUntil;
    Instant createdAt;
    Instant updatedAt;
}
