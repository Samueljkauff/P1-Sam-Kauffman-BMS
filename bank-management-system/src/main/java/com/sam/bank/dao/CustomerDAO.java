package com.sam.bank.dao;

import com.sam.bank.model.Customer;
import com.sam.bank.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    private ConnectionFactory connectionFactory;

    public CustomerDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Customer createCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers ( full_name, email, phone, username, password_hash ) VALUES ( ?, ?, ?, ?, ? )";

        try(Connection connection = this.connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getFullName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getUsername());
            statement.setString(5, customer.getPasswordHash());

            statement.executeUpdate();
        }
        return customer;
    }
}
