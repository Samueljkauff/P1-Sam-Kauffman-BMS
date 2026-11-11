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
    private BigDecimal minimumBalance;
    private BigDecimal overdraftLimit;
    private AccountStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Account(long customerId, String accountNumber, AccountType accountType, BigDecimal balance, BigDecimal minimumBalance, BigDecimal overdraftLimit) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.overdraftLimit = overdraftLimit;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public BigDecimal getMinimumBalance() {
        return this.minimumBalance;
    }

    public BigDecimal getOverdraftLimit() {
        return this.overdraftLimit;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountId() {
        return this.accountId;
    }
}       
