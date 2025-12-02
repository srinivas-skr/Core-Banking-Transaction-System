-- Step 1: Create User (Run as SYSDBA)
-- sqlplus sys/admin123@//localhost:1521/XE as sysdba
/*
CREATE USER banking_user IDENTIFIED BY pass123;
GRANT CONNECT, RESOURCE, DBA TO banking_user;
EXIT;
*/

-- Step 2: Create Table and Insert Data (Run as banking_user)
-- Connect using: sqlplus banking_user/pass123@//localhost:1521/XE

-- 1. Create Accounts Table
-- Note: Removed GENERATED ALWAYS AS IDENTITY for Oracle 11g compatibility
CREATE TABLE accounts (
    account_number NUMBER(10) PRIMARY KEY,
    full_name VARCHAR2(50),
    email VARCHAR2(50) UNIQUE,
    balance NUMBER(10,2),
    security_pin NUMBER(4)
);

-- 2. Create Transactions Table (For History)
CREATE TABLE transactions (
    transaction_id NUMBER(10) PRIMARY KEY,
    account_number NUMBER(10),
    transaction_type VARCHAR2(20),
    amount NUMBER(10,2),
    balance_after NUMBER(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- 3. Insert Dummy Data to test
-- Manually adding IDs (1 and 2)
INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (1, 'Ravi Kumar', 'ravi@gmail.com', 5000.00, 1234);

INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (2, 'Priya Sharma', 'priya@gmail.com', 2000.00, 5678);

COMMIT;
