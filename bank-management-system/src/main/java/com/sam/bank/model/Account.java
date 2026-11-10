package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.sam.bank.enums.AccountType;

public class Account {
    private long accountId;
    private long customerId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private AccountStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Account(long customerId, String accountNumber, AccountType accountType, BigDecimal balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }
}       
