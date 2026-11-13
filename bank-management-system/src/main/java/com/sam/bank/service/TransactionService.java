package com.sam.bank.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.sam.bank.dao.AccountDAO;
import com.sam.bank.dao.TransactionDAO;
import com.sam.bank.model.Account;
import com.sam.bank.enums.AccountStatus;
import com.sam.bank.enums.AccountType;
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

        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("insufficient Funds.");
        }
        
        jdbcTransaction.execute(connection -> {
            accountDAO.updateBalance(connection, accountId, newBalance);
            System.out.println("Successfully updated amount of account: #" + account.getAccountNumber() + " to " + newBalance);
        });
    }

    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) throws SQLException {
        long fromAccountId = fromAccount.getAccountId();
        long toAccountId = toAccount.getAccountId();

        BigDecimal fromAccountNewBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal toAccountNewBalance = toAccount.getBalance().add(amount);

        if(fromAccountNewBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("insufficient Funds.");
        }

        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You cannot transfer $0.");
        }

        if(toAccount.getAccountId() == fromAccount.getAccountId()) {
            throw new IllegalArgumentException("You cannot transfer to the same account.");
        }

        jdbcTransaction.execute(connection -> {
            accountDAO.updateBalance(connection, toAccountId, toAccountNewBalance);
            accountDAO.updateBalance(connection, fromAccountId, fromAccountNewBalance);
            System.out.println("Transfer Successful!");
            System.out.println("#" + fromAccount.getAccountNumber() + " new balance: " + fromAccountNewBalance);
            System.out.println("#" + toAccount.getAccountNumber() + " new balance: " + toAccountNewBalance);
        });
    }

    public void repayLoan(Account fromAccount, Account loanAccount, BigDecimal amount) throws SQLException {
        long fromAccountId = fromAccount.getAccountId();
        long loanAccountId = loanAccount.getAccountId();

        BigDecimal fromAccountNewBalance = fromAccount.getBalance().subtract(amount);
        BigDecimal loanAccountNewBalance = loanAccount.getBalance().subtract(amount);

        if(fromAccountNewBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("insufficient Funds.");
        }

        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You cannot transfer $0.");
        }

        if(loanAccountNewBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You cannot transfer to the same account.");
        }

        jdbcTransaction.execute(connection -> {
            accountDAO.updateBalance(connection, loanAccountId, loanAccountNewBalance);
            accountDAO.updateBalance(connection, fromAccountId, fromAccountNewBalance);
                if (loanAccountNewBalance.compareTo(BigDecimal.ZERO) == 0) {
                    accountDAO.updateStatus(connection, loanAccountId, AccountStatus.CLOSED);
                }
        });
    }
}
