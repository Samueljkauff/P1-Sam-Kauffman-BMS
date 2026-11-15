package com.sam.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sam.bank.model.Customer;
import com.sam.bank.util.ConnectionFactory;

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

    public Customer verifyCredentials(String email1, String password) throws SQLException {
        String sql = "SELECT * FROM customers WHERE email = ?";
        Customer customer = null;

        try(Connection connection = this.connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email1);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                customer = mapCustomer(result);
            } else {
                System.out.println("ERROR: Credentials incorrect. Please try again.");
            }
        }
        return customer;
    }

    private Customer mapCustomer(ResultSet result) throws SQLException {
                String fullName = result.getString("full_name");
                String email = result.getString("email");
                String phone = result.getString("phone");
                String username = result.getString("username");
                String passwordHash = result.getString("password_hash");
                
                Customer customer = new Customer(fullName, email, phone, username, passwordHash);
                customer.setID(result.getLong("customer_id"));
                
                return customer;
    }

    public void changePassword(long customerId, String password) throws SQLException {
        String sql = "UPDATE customers SET password_hash = ? WHERE customer_id = ?";

        try(Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, password);
            statement.setLong(2, customerId);

            statement.executeUpdate();
        }
    }
}
