package com.sam.bank.service;

import com.sam.bank.model.Customer;

public class AuthService {

    public boolean loginCredentials(String email, String password) {
        return true;
    }

    public boolean registerCredentials(String firstName, String lastName, String email, String phone, String username, String password, String verifyPassword) {
        String fullName = new StringBuilder(firstName).append(' ').append(lastName).toString();
        
        Customer customer = new Customer(fullName, email, phone, username, password);

        return false;
    }
}
