package com.sam.bank.service;

import java.sql.SQLException;

import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.model.Customer;
import com.sam.bank.security.PasswordHasher;
import com.sam.bank.security.SessionManager;

public class AuthService {
    private final CustomerDAO customerDAO;
    private PasswordHasher passwordHasher;
    private SessionManager sessionManager;

    public AuthService(CustomerDAO customerDAO, PasswordHasher passwordHasher, SessionManager sessionManager) {
        this.customerDAO = customerDAO;
        this.passwordHasher = passwordHasher;
        this.sessionManager = sessionManager;
    }

    public boolean loginCredentials(String email, String password) throws SQLException {
        Customer customer = customerDAO.verifyCredentials(email.toLowerCase(), password);
        if(customer != null && passwordHasher.verifyPassword(password, customer.getPasswordHash())) {
            sessionManager.setCurrentSession(customer);
            return true;
        } else {
            System.out.println("Credentials Incorrect.");
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
            System.out.println("Credentials incorrect. Please Try again. \n");
            return false;
        }
    }

    public void logout() {
        this.sessionManager.setCurrentSession(null);
    }

    public Customer getCurrentCustomer() {
        return sessionManager.getCurrentCustomer();
    }
}
