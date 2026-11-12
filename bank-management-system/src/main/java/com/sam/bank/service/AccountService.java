package com.sam.bank.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.DepositDAO;
import com.sam.bank.dao.LoanDAO;
import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.model.DepositAccount;
import com.sam.bank.model.LoanAccount;
import com.sam.bank.security.SessionManager;
import com.sam.bank.util.JdbcTransaction;

public class AccountService {
    private final AccountDAO accountDAO;
    private final LoanDAO loanDAO;
    private final DepositDAO depositDAO;
    private final SessionManager session;
    private final JdbcTransaction transaction;
    private static final SecureRandom secureRandom = new SecureRandom();

    public AccountService(AccountDAO accountDAO, LoanDAO loanDAO, DepositDAO depositDAO, SessionManager session, JdbcTransaction transaction) {
        this.accountDAO = accountDAO;
        this.loanDAO = loanDAO;
        this.depositDAO = depositDAO;
        this.session = session;
        this.transaction = transaction;
    }

    public Account savingCreation(BigDecimal initAmount, BigDecimal minBal) throws SQLException {
        AccountType type = AccountType.SAVINGS;
        Account account = createAccount(type, initAmount, minBal, BigDecimal.ZERO);
        accountDAO.createAccount(account);
        return account;
    }

    public Account currentCreation(BigDecimal initAmount, BigDecimal overdraftLimit) throws SQLException {
        AccountType type = AccountType.CURRENT;
        Account account = createAccount(type, initAmount, BigDecimal.ZERO, overdraftLimit);
        accountDAO.createAccount(account);
        return account;
    }

    public Account depositCreation(BigDecimal initAmount, int termMonths, LocalDate maturity, Long funding) throws SQLException {
        AccountType type = AccountType.DEPOSIT;
        Account account = createAccount(type, initAmount, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal interestRate = new BigDecimal("0.045");
        DepositAccount depositAccount = new DepositAccount(initAmount, termMonths, interestRate, maturity, funding);
        transaction.execute(connection -> {
            accountDAO.createAccount(connection, account);
            depositAccount.setAccountId(account.getAccountId());
            depositDAO.createDepositDetails(connection, depositAccount);
            // FUNDING LOGIC HERE
        });
        return account;
    }

    public Account loanCreation(BigDecimal principal) throws SQLException {
        AccountType type = AccountType.LOAN;
        Account account = createAccount(type, principal, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal interestRate = new BigDecimal("0.045");
        LocalDate startDate = LocalDate.now(); 
        int tenureMonths = 36;
        LoanAccount loanAccount = new LoanAccount(principal, interestRate,  tenureMonths, startDate);
        transaction.execute(connection -> {
            accountDAO.createAccount(connection, account);
            loanAccount.setAccountId(account.getAccountId());
            loanDAO.createLoanDetails(connection, loanAccount);
        });
        return account;
    }

    private Account createAccount(AccountType type, BigDecimal balance, BigDecimal minimumBalance, BigDecimal overdraftLimit) {
        long customerId = session.getCurrentCustomer().getId();
        String accountNumber = generateAccountNumber();
        Account account = new Account(customerId, accountNumber, type, balance, minimumBalance, overdraftLimit);
        return account;
    }

    public List<Account> getAllAccounts() throws SQLException {
        long id = session.getCurrentCustomer().getId();

        return accountDAO.getAllAccounts(id);
    }

    private String generateAccountNumber() {
        long origin = 1_000_000_000L;
        long bound = 9_000_000_000L;
        
        long randomNumber = origin + secureRandom.nextLong(bound);
        
        return String.valueOf(randomNumber);
    }
}
