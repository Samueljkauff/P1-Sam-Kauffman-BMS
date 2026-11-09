package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

public class DepositAccount {
    private BigDecimal openingDeposit;
    private Instant maturityDate;
    private long fundingAccountId;
}
