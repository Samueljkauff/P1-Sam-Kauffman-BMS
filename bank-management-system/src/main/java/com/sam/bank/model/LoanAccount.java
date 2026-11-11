
package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanAccount {

    private long accountId;
    private BigDecimal principalAmount;
    private BigDecimal annualRate;
    private int tenureMonths;
    private LocalDate startDate;

    public LoanAccount(
            BigDecimal principalAmount,
            BigDecimal annualRate,
            int tenureMonths,
            LocalDate startDate
    ) {
        this.principalAmount = principalAmount;
        this.annualRate = annualRate;
        this.tenureMonths = tenureMonths;
        this.startDate = startDate;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public BigDecimal getAnnualRate() {
        return annualRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
