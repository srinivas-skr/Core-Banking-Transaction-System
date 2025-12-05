-- ============================================================
-- Core Banking Transaction System - Database Schema
-- Oracle 11g XE Compatible
-- ============================================================

-- Drop existing objects (for clean reinstall)
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE transactions CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE accounts CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

-- ============================================================
-- ACCOUNTS TABLE
-- Stores customer account information
-- ============================================================
CREATE TABLE accounts (
    account_number  NUMBER(10)      PRIMARY KEY,
    full_name       VARCHAR2(100)   NOT NULL,
    email           VARCHAR2(100)   UNIQUE NOT NULL,
    balance         NUMBER(15,2)    DEFAULT 0.00 NOT NULL,
    security_pin    NUMBER(4)       NOT NULL,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT chk_balance_positive CHECK (balance >= 0),
    CONSTRAINT chk_pin_valid CHECK (security_pin BETWEEN 1000 AND 9999)
);

-- ============================================================
-- TRANSACTIONS TABLE
-- Stores all transaction history
-- ============================================================
CREATE TABLE transactions (
    transaction_id      NUMBER(15)      PRIMARY KEY,
    account_number      NUMBER(10)      NOT NULL,
    transaction_type    VARCHAR2(20)    NOT NULL,
    amount              NUMBER(15,2)    NOT NULL,
    balance_after       NUMBER(15,2)    NOT NULL,
    transaction_date    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    reference_number    VARCHAR2(50),
    
    -- Foreign Key
    CONSTRAINT fk_transaction_account 
        FOREIGN KEY (account_number) 
        REFERENCES accounts(account_number),
    
    -- Constraints
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_txn_type CHECK (
        transaction_type IN ('Transfer Sent', 'Transfer Received', 'Deposit', 'Withdrawal')
    )
);

-- ============================================================
-- INDEXES for Performance
-- ============================================================
CREATE INDEX idx_transactions_account ON transactions(account_number);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_accounts_email ON accounts(email);

-- ============================================================
-- Commit
-- ============================================================
COMMIT;

PROMPT ==========================================
PROMPT   SCHEMA CREATED SUCCESSFULLY
PROMPT ==========================================
