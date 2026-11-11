package com.sam.bank.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;

import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.model.DepositAccount;
import com.sam.bank.model.LoanAccount;
import com.sam.bank.security.SessionManager;

public class AccountService {
    private SessionManager session;
    private static final SecureRandom secureRandom = new SecureRandom();
    private BigDecimal defaultDecimal;

    public AccountService(SessionManager session) {
        this.session = session;
        defaultDecimal = new BigDecimal("0.00");
    }

    public Account savingCreation(AccountType type, BigDecimal minBal) {
        Account account = createAccount(type, this.defaultDecimal, minBal, this.defaultDecimal);
        return account;
    }

    public Account currentCreation(AccountType type, BigDecimal overdraftLimit) {
        Account account = createAccount(type, this.defaultDecimal, this.defaultDecimal, overdraftLimit);
        return account;
    }

    public Account depositCreation(AccountType type, BigDecimal initAmount, LocalDate maturity, Long funding) {
        Account account = createAccount(type, initAmount, this.defaultDecimal, this.defaultDecimal);
        DepositAccount depositAccount = new DepositAccount(initAmount, maturity, funding);
        return account;
    }

    public Account loanCreation(AccountType type, BigDecimal principal) {
        Account account = createAccount(type, principal, this.defaultDecimal, this.defaultDecimal);
        // Maybe change this logic
        LoanAccount loanAccount = new LoanAccount(principal, principal);
        return account;
    }

    private Account createAccount(AccountType type, BigDecimal balance, BigDecimal minimumBalance, BigDecimal overdraftLimit) {
        long customerId = session.getCurrentCustomer().getId();
        String accountNumber = generateAccountNumber();
        Account account = new Account(customerId, accountNumber, type, balance, minimumBalance, overdraftLimit);
        return account;
    }

    private String generateAccountNumber() {
        long origin = 1_000_000_000L;
        long bound = 9_000_000_000L;
        
        long randomNumber = origin + secureRandom.nextLong(bound);
        
        return String.valueOf(randomNumber);
    }
}
