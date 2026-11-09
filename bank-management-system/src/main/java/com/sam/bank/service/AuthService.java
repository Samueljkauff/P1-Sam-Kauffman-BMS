package com.sam.bank.service;

import java.sql.SQLException;

import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.model.Customer;
import com.sam.bank.security.PasswordHasher;

public class AuthService {
    public CustomerDAO customerDAO;
    public PasswordHasher passwordHasher;

    public AuthService(CustomerDAO customerDAO, PasswordHasher passwordHasher) {
        this.customerDAO = customerDAO;
        this.passwordHasher = passwordHasher;
    }

    public boolean loginCredentials(String email, String password) throws SQLException {
        Customer customer = customerDAO.verifyCredentials(email.toLowerCase(), password);
        if(passwordHasher.verifyPassword(password, customer.getPasswordHash())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean registerCredentials(String firstName, String lastName, String email, String phone, String username, String password, String verifyPassword) throws SQLException {
        String fullName = new StringBuilder(firstName).append(' ').append(lastName).toString();

        if(password.equals(verifyPassword)) {
            password = passwordHasher.hashPassword(password);
            Customer customer = new Customer(fullName.toLowerCase(), email.toLowerCase(), phone, username.toLowerCase(), password);
            customerDAO.createCustomer(customer);
            return true;
        } else {
            return false;
        }
    }
}
