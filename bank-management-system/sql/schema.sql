BEGIN;


CREATE TABLE customers (
    customer_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    full_name VARCHAR(120) NOT NULL,

    email VARCHAR(254) NOT NULL UNIQUE,

    phone VARCHAR(30),

    username VARCHAR(80) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    failed_login_count INT NOT NULL DEFAULT 0,

    locked_until TIMESTAMP NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_customer_status
        CHECK (status IN ('ACTIVE', 'LOCKED', 'CLOSED')),

    CONSTRAINT chk_failed_customer
        CHECK (failed_login_count >= 0)
);


CREATE TABLE accounts (
    account_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    customer_id BIGINT NOT NULL,

    account_number VARCHAR(34) NOT NULL UNIQUE,

    account_type VARCHAR(20) NOT NULL,

    balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    minimum_balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,

    overdraft_limit DECIMAL(19,2) NOT NULL DEFAULT 0.00,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id),

    CONSTRAINT chk_account_type
        CHECK (account_type IN ('SAVINGS', 'CURRENT', 'DEPOSIT', 'LOAN')),

    CONSTRAINT chk_account_status
        CHECK (status IN ('ACTIVE', 'FROZEN', 'MATURED', 'CLOSED')),

    CONSTRAINT chk_account_balance
        CHECK (balance >= 0)
);

CREATE INDEX idx_accounts_customer
ON accounts(customer_id);


CREATE TABLE deposit_details (
    account_id BIGINT PRIMARY KEY,

    principal DECIMAL(19,2) NOT NULL,

    term_months INT NOT NULL,

    interest_rate DECIMAL(5,2) NOT NULL DEFAULT 0.00,

    maturity_date DATE NOT NULL,

    CONSTRAINT fk_dep_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(account_id),

    CONSTRAINT chk_dep_term
        CHECK (term_months > 0 AND principal > 0),

    CONSTRAINT chk_intrst_rt
        CHECK (interest_rate BETWEEN 0 AND 100)
);


CREATE TABLE loan_details (
    account_id BIGINT PRIMARY KEY,

    principal_amount DECIMAL(19,2) NOT NULL,

    annual_rate DECIMAL(5,2) NOT NULL DEFAULT 0.00,

    tenure_months INT NOT NULL,

    start_date DATE NOT NULL,

    CONSTRAINT fk_loan_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(account_id),

    CONSTRAINT chk_loan_vals
        CHECK (principal_amount > 0 AND tenure_months > 0),

    CONSTRAINT chk_anual_rt
        CHECK (annual_rate BETWEEN 0 AND 100)
);


CREATE TABLE transactions (
    transaction_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    reference_number VARCHAR(40) NOT NULL UNIQUE,

    source_account_id BIGINT NULL,

    destination_account_id BIGINT NULL,

    transaction_type VARCHAR(20) NOT NULL,

    amount DECIMAL(19,2) NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

    description VARCHAR(255),

    source_balance_after DECIMAL(19,2) NULL,

    dest_balance_after DECIMAL(19,2) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_txn_source
        FOREIGN KEY (source_account_id)
        REFERENCES accounts(account_id),

    CONSTRAINT fk_txn_dest
        FOREIGN KEY (destination_account_id)
        REFERENCES accounts(account_id),

    CONSTRAINT chk_txn_amount
        CHECK (amount > 0),

    CONSTRAINT chk_txn_type
        CHECK (
            transaction_type IN (
                'DEPOSIT',
                'WITHDRAWAL',
                'TRANSFER',
                'LOAN_REPAYMENT'
            )
        ),

    CONSTRAINT chk_txn_status
        CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED', 'REVERSED')),

    CONSTRAINT chk_txn_shape
        CHECK (
            (
                transaction_type = 'DEPOSIT'
                AND source_account_id IS NULL
                AND destination_account_id IS NOT NULL
            )
            OR
            (
                transaction_type = 'WITHDRAWAL'
                AND source_account_id IS NOT NULL
                AND destination_account_id IS NULL
            )
            OR
            (
                transaction_type IN ('TRANSFER', 'LOAN_REPAYMENT')
                AND source_account_id IS NOT NULL
                AND destination_account_id IS NOT NULL
                AND source_account_id <> destination_account_id
            )
        )
);

CREATE INDEX idx_txn_src_time
ON transactions(source_account_id, created_at, transaction_id);

CREATE INDEX idx_txn_dst_time
ON transactions(destination_account_id, created_at, transaction_id);


CREATE TABLE login_audit (
    audit_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    customer_id BIGINT NULL,

    username_attempted VARCHAR(80) NOT NULL,

    outcome VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id),

    CONSTRAINT chk_audit_outcome
        CHECK (
            outcome IN (
                'SUCCESS',
                'FAILURE',
                'LOCKED',
                'TIMEOUT',
                'LOGOUT'
            )
        )
);


CREATE TABLE transaction_audit (
    audit_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    customer_id BIGINT NOT NULL,

    reference_number VARCHAR(40) NULL,

    event_type VARCHAR(30) NOT NULL,

    outcome VARCHAR(20) NOT NULL,

    failure_code VARCHAR(40) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_tauditcust
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);

CREATE INDEX idx_taudit_customer
ON transaction_audit(customer_id, created_at);


CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_customers_updated_at
BEFORE UPDATE ON customers
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();


CREATE TRIGGER trg_accounts_updated_at
BEFORE UPDATE ON accounts
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();


COMMIT;
