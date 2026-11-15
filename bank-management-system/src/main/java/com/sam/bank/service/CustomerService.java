package com.sam.bank.service;

import java.sql.SQLException;

import com.sam.bank.dao.CustomerDAO;
import com.sam.bank.security.PasswordHasher;

public class CustomerService {
    private final CustomerDAO customerDAO;
    private final PasswordHasher passwordHasher;

    public CustomerService(CustomerDAO customerDAO, PasswordHasher passwordHasher) {
        this.customerDAO = customerDAO;
        this.passwordHasher = passwordHasher;
    }

    public void changePassword(long customerId, String newPassword){
        newPassword = passwordHasher.hashPassword(newPassword);
        try {
            customerDAO.changePassword(customerId, newPassword);
            System.out.println("Password successfully updated.");
        }catch(SQLException e) {
            System.out.println(e);
        }
    }
}
