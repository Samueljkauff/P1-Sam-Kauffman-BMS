package com.sam.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sam.bank.enums.AccountStatus;
import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.util.ConnectionFactory;

public class AccountDAO {
    private final ConnectionFactory connectionFactory;

    public AccountDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public Account createAccount(Connection connection, Account account) throws SQLException {
        return insertAccount(connection, account);
    }

    public Account createAccount(Account account) throws SQLException {
        try(Connection connection = connectionFactory.getConnection()) {
            return insertAccount(connection, account);
        }
    }

    private Account insertAccount(Connection connection, Account account) throws SQLException {
        String sql = "INSERT INTO accounts ( customer_id, account_number, account_type, balance, minimum_balance, overdraft_limit ) VALUES ( ?, ?, ?, ?, ?, ? ) RETURNING account_id";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, account.getCustomerId());
            statement.setString(2, account.getAccountNumber());
            statement.setString(3, account.getAccountType().name());
            statement.setBigDecimal(4, account.getBalance());
            statement.setBigDecimal(5, account.getMinimumBalance());
            statement.setBigDecimal(6, account.getOverdraftLimit());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    long accountId = result.getLong("account_id");
                    account.setAccountId(accountId);
                } else {
                    throw new SQLException("Account creation failed: no ID returned.");
                }
            }
        }

        return account;
    }

    public List<Account> getAllAccounts(long id, boolean depositIncluded, boolean isLoan) throws SQLException {
        String accountTypes;
        List<Account> accountList = new ArrayList<>();
        
        if (isLoan) {
            accountTypes = "'LOAN'";
        } else if (depositIncluded) {
            accountTypes = "'SAVINGS', 'CURRENT', 'DEPOSIT'";
        } else {
            accountTypes = "'SAVINGS', 'CURRENT'";
        }

        String sql = "SELECT account_id, customer_id, account_number, account_type, balance, minimum_balance, overdraft_limit, status "
                + "FROM accounts WHERE customer_id = ? AND status = 'ACTIVE' "
                + "AND account_type IN (" + accountTypes + ")";

        try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Account account = mapAccount(result);
                    accountList.add(account);
                }
            }
        }

        return accountList;
    }

    public void updateBalance(Connection connection, long accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, newBalance);
            statement.setLong(2, accountId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Account not found.");
            }
        }
    }

    public void updateStatus(Connection connection, long accountId, AccountStatus status) throws SQLException {
        String sql = "UPDATE accounts SET status = ? WHERE account_id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.name());
            statement.setLong(2, accountId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Account not found.");
            }
        }
    }

    public Account mapAccount(ResultSet result) throws SQLException {
        long accountId = result.getLong("account_id");
        long customerId = result.getLong("customer_id");
        String accountNumber = result.getString("account_number");
        String accountType = result.getString("account_type");
        BigDecimal balance = result.getBigDecimal("balance");
        BigDecimal minimumBalance = result.getBigDecimal("minimum_balance");
        BigDecimal overdraftLimit = result.getBigDecimal("overdraft_limit");
        AccountStatus status = AccountStatus.valueOf(result.getString("status"));

        Account account = new Account(customerId, accountNumber, AccountType.valueOf(accountType), balance, minimumBalance, overdraftLimit);
        account.setAccountId(accountId);
        account.setStatus(status);

        return account;
    }   
}
