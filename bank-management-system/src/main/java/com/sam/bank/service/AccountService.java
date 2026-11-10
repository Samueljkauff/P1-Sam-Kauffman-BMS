package com.sam.bank.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;

import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.security.SessionManager;

public class AccountService {
    private SessionManager session;
    private static final SecureRandom secureRandom = new SecureRandom();

    public AccountService(SessionManager session) {
        this.session = session;
    }

    public Account savingCreation(AccountType type, BigDecimal minBal) {
        Account account = createAccount(type, new BigDecimal("0.00"));
        return account;
    }

    public Account currentCreation(AccountType type, BigDecimal overdraftLimit) {
        Account account = createAccount(type, new BigDecimal("0.00"));
        return account;
    }

    public Account depositCreation(AccountType type, BigDecimal initAmount, LocalDate maturity, Long funding) {
        Account account = createAccount(type, initAmount);
        return account;
    }

    public Account loanCreation(AccountType type, BigDecimal principal) {
        Account account = createAccount(type, principal);
        return account;
    }

    private Account createAccount(AccountType type, BigDecimal balance) {
        long customerId = session.getCurrentCustomer().getId();
        String accountNumber = generateAccountNumber();
        Account account = new Account(customerId, accountNumber, type, balance);
        return account;
    }

    private String generateAccountNumber() {
        long origin = 1_000_000_000L;
        long bound = 9_000_000_000L;
        
        long randomNumber = origin + secureRandom.nextLong(bound);
        
        return String.valueOf(randomNumber);
    }
}
