package com.sam.bank.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.TransactionDAO;
import com.sam.bank.model.Account;
import com.sam.bank.util.JdbcTransaction;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final AccountDAO accountDAO;
    private final JdbcTransaction jdbcTransaction;

    public TransactionService(TransactionDAO transactionDAO, AccountDAO accountDAO, JdbcTransaction jdbcTransaction) {
        this.transactionDAO = transactionDAO;
        this.accountDAO = accountDAO;
        this.jdbcTransaction = jdbcTransaction;
    }

    public void deposit(Account account, BigDecimal amount) throws SQLException {
        long accountId = account.getAccountId();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        BigDecimal newBalance = account.getBalance().add(amount);

        jdbcTransaction.execute(connection -> {
            accountDAO.updateBalance(connection, accountId, newBalance);
            System.out.println("Successfully updated amount of account: #" + account.getAccountNumber() + " to " + newBalance);
        });
    }

    public void withdrawal(Account account, BigDecimal amount) throws SQLException {
        
        long accountId = account.getAccountId();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);

        if(newBalance.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("insufficient Funds.");
        }
        
        jdbcTransaction.execute(connection -> {
            accountDAO.updateBalance(connection, accountId, newBalance);
            System.out.println("Successfully updated amount of account: #" + account.getAccountNumber() + " to " + newBalance);
        });
    }

    public void transfer(long fromAcount, long toAccount, BigDecimal amount) {

    }
}
