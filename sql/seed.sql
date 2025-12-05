-- ============================================================
-- Core Banking Transaction System - Seed Data
-- Test accounts for development
-- ============================================================

-- Clear existing data
DELETE FROM transactions;
DELETE FROM accounts;

-- ============================================================
-- TEST ACCOUNTS
-- ============================================================
INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (1001, 'Ravi Kumar', 'ravi@gmail.com', 5000.00, 1234);

INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (1002, 'Priya Sharma', 'priya@gmail.com', 2000.00, 5678);

INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (1003, 'Amit Patel', 'amit@gmail.com', 10000.00, 4321);

-- ============================================================
-- SAMPLE TRANSACTIONS (Optional)
-- ============================================================
INSERT INTO transactions (transaction_id, account_number, transaction_type, amount, balance_after)
VALUES (1, 1001, 'Deposit', 5000.00, 5000.00);

INSERT INTO transactions (transaction_id, account_number, transaction_type, amount, balance_after)
VALUES (2, 1002, 'Deposit', 2000.00, 2000.00);

-- ============================================================
-- Commit
-- ============================================================
COMMIT;

PROMPT ==========================================
PROMPT   SEED DATA INSERTED SUCCESSFULLY
PROMPT ==========================================
PROMPT.
PROMPT   Test Accounts:
PROMPT   1. ravi@gmail.com / PIN: 1234
PROMPT   2. priya@gmail.com / PIN: 5678
PROMPT   3. amit@gmail.com / PIN: 4321
PROMPT ==========================================
