package com.sam.bank.dao;

import com.sam.bank.model.DepositAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositDAO {
    public void createDepositDetails(Connection connection, DepositAccount depositAccount) throws SQLException {
        String sql = "INSERT INTO deposit_details (account_id, principal, term_months, interest_rate, maturity_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, depositAccount.getAccountId());
            statement.setBigDecimal(2, depositAccount.getPrincipal());
            statement.setInt(3, depositAccount.getTermMonths());
            statement.setBigDecimal(4, depositAccount.getInterestRate());
            statement.setDate(5, java.sql.Date.valueOf(depositAccount.getMaturityDate()));

            statement.executeUpdate();
        }
    }
}
