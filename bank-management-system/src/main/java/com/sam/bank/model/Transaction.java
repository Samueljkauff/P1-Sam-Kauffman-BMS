
package com.sam.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.sam.bank.enums.TransactionType;
import com.sam.bank.enums.TransactionStatus;

public class Transaction {

    private long transactionId;
    private String referenceNumber;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private TransactionStatus status;
    private String description;
    private BigDecimal sourceBalanceAfter;
    private BigDecimal destBalanceAfter;
    private Instant createdAt;

    public Transaction(long transactionId, String referenceNumber,
            Long sourceAccountId, Long destinationAccountId,
            TransactionType transactionType, BigDecimal amount,
            TransactionStatus status, String description,
            BigDecimal sourceBalanceAfter, BigDecimal destBalanceAfter,
            Instant createdAt) {

        this.transactionId = transactionId;
        this.referenceNumber = referenceNumber;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.sourceBalanceAfter = sourceBalanceAfter;
        this.destBalanceAfter = destBalanceAfter;
        this.createdAt = createdAt;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSourceBalanceAfter() {
        return sourceBalanceAfter;
    }

    public BigDecimal getDestBalanceAfter() {
        return destBalanceAfter;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
