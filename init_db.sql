-- AUTOMATED DATABASE SETUP SCRIPT
-- This script creates the user, tables, and dummy data automatically.

-- 1. Create the User (Drop if exists to reset)
BEGIN
   EXECUTE IMMEDIATE 'DROP USER banking_user CASCADE';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -1918 THEN
         RAISE;
      END IF;
END;
/

CREATE USER banking_user IDENTIFIED BY pass123;
GRANT CONNECT, RESOURCE TO banking_user;

-- 2. Connect as the new user
CONNECT banking_user/pass123@XE;

-- 3. Create Tables
CREATE TABLE accounts (
    account_number NUMBER(10) PRIMARY KEY,
    full_name VARCHAR2(50),
    email VARCHAR2(50) UNIQUE,
    balance NUMBER(10,2),
    security_pin NUMBER(4)
);

CREATE TABLE transactions (
    transaction_id NUMBER(10) PRIMARY KEY,
    account_number NUMBER(10),
    transaction_type VARCHAR2(20),
    amount NUMBER(10,2),
    balance_after NUMBER(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- 4. Insert Dummy Data
INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (1, 'Ravi Kumar', 'ravi@gmail.com', 5000.00, 1234);

INSERT INTO accounts (account_number, full_name, email, balance, security_pin) 
VALUES (2, 'Priya Sharma', 'priya@gmail.com', 2000.00, 5678);

COMMIT;

PROMPT ==========================================
PROMPT      DATABASE SETUP SUCCESSFUL!
PROMPT ==========================================
EXIT;
