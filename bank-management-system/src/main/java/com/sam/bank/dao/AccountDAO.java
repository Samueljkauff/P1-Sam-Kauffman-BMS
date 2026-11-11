package com.sam.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sam.bank.model.Account;
import com.sam.bank.util.ConnectionFactory;

public class AccountDAO {
    private final ConnectionFactory connectionFactory;

    public AccountDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Account createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account ( customer_id, account_number, account_type, balance, minimum_balance, overdraft_limit ) VALUES ( ?, ?, ?, ?, ?, ? )";

        try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, account.getCustomerId());
            statement.setString(2, account.getAccountNumber());
            statement.setString(3, account.getAccountType().name());
            statement.setBigDecimal(4, account.getBalance());
            statement.setBigDecimal(5, account.getMinimumBalance());
            statement.setBigDecimal(6, account.getOverdraftLimit());

            statement.executeUpdate();
        }
        return account;
    }
}
