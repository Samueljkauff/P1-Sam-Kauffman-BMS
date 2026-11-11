package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepositAccount {
    private BigDecimal openingDeposit;
    private LocalDate maturityDate;
    private long fundingAccountId;

    public DepositAccount(BigDecimal openingDeposit, LocalDate maturiyDate, long fundingAccountId) {
        this.openingDeposit = openingDeposit;
        this.maturityDate = maturiyDate;
        this.fundingAccountId = fundingAccountId;
    }

    public BigDecimal getOpeningDeposit() {
        return this.openingDeposit;
    }

    public LocalDate getMaturityDate() {
        return this.maturityDate;
    }

    public long getFundingAccountId() {
        return this.fundingAccountId;
    }
}
