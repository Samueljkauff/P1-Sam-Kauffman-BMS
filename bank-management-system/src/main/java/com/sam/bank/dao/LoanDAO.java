package com.sam.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sam.bank.model.LoanAccount;

public class LoanDAO {
public void createLoanDetails(Connection connection, LoanAccount loanAccount)
        throws SQLException {

    String sql = "INSERT INTO loan_details (account_id, principal_amount, annual_rate, tenure_months, start_date) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setLong(1, loanAccount.getAccountId());
        statement.setBigDecimal(2, loanAccount.getPrincipalAmount());
        statement.setBigDecimal(3, loanAccount.getAnnualRate());
        statement.setInt(4, loanAccount.getTenureMonths());
        statement.setDate(5, java.sql.Date.valueOf(loanAccount.getStartDate()));

        statement.executeUpdate();
    }
}
}
