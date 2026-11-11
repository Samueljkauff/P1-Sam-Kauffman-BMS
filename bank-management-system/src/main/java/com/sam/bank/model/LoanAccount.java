package com.sam.bank.model;

import java.math.BigDecimal;

public class LoanAccount {
    private BigDecimal principal;
    private BigDecimal outstandingBalance;

    public LoanAccount(BigDecimal principal, BigDecimal outstandingBalance) {
        this.principal = principal;
        this.outstandingBalance = outstandingBalance;
    }

    public BigDecimal getPincipal() {
        return this.principal;
    }

    public BigDecimal getOutstandingBalance() {
        return this.outstandingBalance;
    }
}