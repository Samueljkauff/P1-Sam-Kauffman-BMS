package com.sam.bank.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.sam.bank.app.ConsoleInput;
import com.sam.bank.enums.AccountType;
import com.sam.bank.model.Account;
import com.sam.bank.security.SessionManager;

public class AccountService {
    private SessionManager session;
    private ConsoleInput input;

    public AccountService(SessionManager session, ConsoleInput input) {
        this.session = session;
        this.input = input;
    }

    public Account savingCreation(AccountType type, int minBal) {
        return new Account();
    }

    public Account currentCreation(AccountType type, int overdraftLimit) {
        return new Account();
    }

    public Account depositCreation(AccountType type, BigDecimal initAmount, LocalDate maturity, Account funding) {
        return new Account();
    }

    public Account loanCreation(AccountType type, BigDecimal principal) {
        return new Account();
    }
}
