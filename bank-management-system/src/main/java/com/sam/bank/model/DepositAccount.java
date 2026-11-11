
package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositAccount {

    private long accountId;
    private BigDecimal openingDeposit;
    private BigDecimal principal;
    private int termMonths;
    private BigDecimal interestRate;
    private LocalDate maturityDate;
    private long fundingAccountId;

    public DepositAccount(
            BigDecimal openingDeposit,
            int termMonths,
            BigDecimal interestRate,
            LocalDate maturityDate,
            long fundingAccountId
    ) {
        this.openingDeposit = openingDeposit;
        this.termMonths = termMonths;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
        this.fundingAccountId = fundingAccountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getPrincipal() {
        return openingDeposit;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public long getFundingAccountId() {
        return fundingAccountId;
    }
}
