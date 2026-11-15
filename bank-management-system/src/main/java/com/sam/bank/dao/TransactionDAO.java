package com.sam.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sam.bank.enums.TransactionStatus;
import com.sam.bank.enums.TransactionType;
import com.sam.bank.model.Transaction;
import com.sam.bank.util.ConnectionFactory;

public class TransactionDAO {
    private final ConnectionFactory connectionFactory;

    public TransactionDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

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

    public ArrayList<Transaction> getHistory(long customerId) throws SQLException {
        String sql = "SELECT transaction_id, reference_number, source_account_id, destination_account_id, transaction_type, amount, status, description, source_balance_after, dest_balance_after, created_at FROM transactions t WHERE EXISTS ( SELECT 1 FROM accounts a WHERE a.account_id = t.source_account_id AND a.customer_id = ? ) OR EXISTS ( SELECT 1 FROM accounts a WHERE a.account_id = t.destination_account_id AND a.customer_id = ? ) ORDER BY t.created_at DESC";

        ArrayList<Transaction> history = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, customerId);
            statement.setLong(2, customerId);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    history.add(mapTransaction(result));
                }
            }
        }

        return history;
    }

    private Transaction mapTransaction(ResultSet result) throws SQLException {
        long sourceId = result.getLong("source_account_id");
        Long sourceAccountId = result.wasNull() ? null : sourceId;

        long destinationId = result.getLong("destination_account_id");
        Long destinationAccountId = result.wasNull() ? null : destinationId;

        return new Transaction(
            result.getLong("transaction_id"),
            result.getString("reference_number"),
            sourceAccountId,
            destinationAccountId,
            TransactionType.valueOf(result.getString("transaction_type")),
            result.getBigDecimal("amount"),
            TransactionStatus.valueOf(result.getString("status")),
            result.getString("description"),
            result.getBigDecimal("source_balance_after"),
            result.getBigDecimal("dest_balance_after"),
            result.getTimestamp("created_at").toInstant()
        );
    }
}
