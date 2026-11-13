package com.sam.bank.service;

import com.sam.bank.dao.CustomerDAO;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
}
