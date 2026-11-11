package com.sam.bank.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.model.DepositAccount;
import com.sam.bank.model.LoanAccount;
import com.sam.bank.security.SessionManager;

public class AccountService {
    private final AccountDAO accountDAO;
    private final SessionManager session;
    private static final SecureRandom secureRandom = new SecureRandom();

    public AccountService(AccountDAO accountDAO, SessionManager session) {
        this.accountDAO = accountDAO;
        this.session = session;
    }

    public Account savingCreation(AccountType type, BigDecimal minBal) throws SQLException {
        Account account = createAccount(type, BigDecimal.ZERO, minBal, BigDecimal.ZERO);
        accountDAO.createAccount(account);
        return account;
    }

    public Account currentCreation(AccountType type, BigDecimal overdraftLimit) throws SQLException {
        Account account = createAccount(type, BigDecimal.ZERO, BigDecimal.ZERO, overdraftLimit);
        accountDAO.createAccount(account);
        return account;
    }

    public Account depositCreation(AccountType type, BigDecimal initAmount, LocalDate maturity, Long funding) throws SQLException {
        Account account = createAccount(type, initAmount, BigDecimal.ZERO, BigDecimal.ZERO);
        accountDAO.createAccount(account);
        DepositAccount depositAccount = new DepositAccount(initAmount, maturity, funding);
        return account;
    }

    public Account loanCreation(AccountType type, BigDecimal principal) throws SQLException {
        Account account = createAccount(type, principal, BigDecimal.ZERO, BigDecimal.ZERO);
        // Maybe change this logic
        LoanAccount loanAccount = new LoanAccount(principal, principal);
        accountDAO.createAccount(account);
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
