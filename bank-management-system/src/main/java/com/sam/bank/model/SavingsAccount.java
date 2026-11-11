package com.sam.bank.model;

import java.math.BigDecimal;

public class SavingsAccount {
    private BigDecimal minimumBalance;

    public SavingsAccount(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMinimumBalance() {
        return this.minimumBalance;
    }
}
