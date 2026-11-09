package com.sam.bank.model;

import java.time.Instant;

import com.sam.bank.enums.AccountType;

public class Account {
    private int accountId;
    private int customerId;
    private int accountNumber;
    private AccountType accountType;
    private long balance;
    private AccountStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}   
