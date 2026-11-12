package com.sam.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Account> getAllAccounts(long id) throws SQLException {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT account_id, account_number, account_type, balance, minimum_balance, overdraft_limit FROM accounts WHERE customer_id = ? AND status = 'ACTIVE' AND account_type IN ('SAVINGS', 'CURRENT')";

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

    public Account mapAccount(ResultSet result) throws SQLException {
        long accountId = result.getLong("account_id");
        String accountNumber = result.getString("account_number");
        String accountType = result.getString("account_type");
        BigDecimal balance = result.getBigDecimal("balance");
        BigDecimal minimumBalance = result.getBigDecimal("minimum_balance");
        BigDecimal overdraftLimit = result.getBigDecimal("overdraft_limit");

        return new Account(accountId, accountNumber, AccountType.valueOf(accountType), balance, minimumBalance, overdraftLimit);
    }   
}
