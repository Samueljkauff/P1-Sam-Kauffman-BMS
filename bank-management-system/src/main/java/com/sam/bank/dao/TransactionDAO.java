package com.sam.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sam.bank.model.Transaction;

public class TransactionDAO {
    public void createTransaction(Connection connection, Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions ( reference_number, source_account_id, destination_account_id, transaction_type, amount, status, description, source_balance_after, dest_balance_after ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getReferenceNumber());

            if (transaction.getSourceAccountId() == null) {
                statement.setNull(2, java.sql.Types.BIGINT);
            } else {
                statement.setLong(2, transaction.getSourceAccountId());
            }

            if (transaction.getDestinationAccountId() == null) {
                statement.setNull(3, java.sql.Types.BIGINT);
            } else {
                statement.setLong(3, transaction.getDestinationAccountId());
            }

            statement.setString(4, transaction.getTransactionType().name());
            statement.setBigDecimal(5, transaction.getAmount());
            statement.setString(6, transaction.getStatus().name());
            statement.setString(7, transaction.getDescription());
            statement.setBigDecimal(8, transaction.getSourceBalanceAfter());
            statement.setBigDecimal(9, transaction.getDestBalanceAfter());

            statement.executeUpdate();
        }
    }
}
