# 🚨 CRITICAL SETUP FOR ORACLE 11g XE

You are using **Oracle Database 11g Express Edition**. This is great, but it requires specific steps.

## STEP 1: Get the Driver (It is already on your computer!)

Since you installed Oracle 11g, the file `ojdbc6.jar` is already on your hard drive. You do NOT need to download it.

1.  Open File Explorer.
2.  Go to `C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib`
    *   *(If you installed it somewhere else, search your computer for `ojdbc6.jar`)*
3.  **COPY** the `ojdbc6.jar` file.
4.  **PASTE** it into your project folder:
    *   `c:\Users\vikas\Downloads\AtmInterface-main\AtmInterface-main\lib\`

## STEP 2: Setup the Database (Using the Black Window)

1.  Open that **"Run SQL Command Line"** app you showed me.
2.  Type this command to login as the Super Admin (press Enter):
    ```sql
    connect sys as sysdba
    ```
    *(If it asks for a password, enter the one you set during installation. If you didn't set one, try just pressing Enter).*

3.  Once it says "Connected", copy and paste these lines ONE BY ONE:

    **Create the User:**
    ```sql
    CREATE USER banking_user IDENTIFIED BY pass123;
    ```

    **Give Permissions:**
    ```sql
    GRANT CONNECT, RESOURCE TO banking_user;
    ```

    **Login as the New User:**
    ```sql
    connect banking_user/pass123
    ```

    **Create the Tables:**
    ```sql
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
    ```
    *(Note: Oracle 11g does not support `GENERATED ALWAYS AS IDENTITY`. We will use manual IDs for simplicity).*

    **Insert Dummy Data:**
    ```sql
    INSERT INTO accounts VALUES (1, 'Ravi Kumar', 'ravi@gmail.com', 5000.00, 1234);
    INSERT INTO accounts VALUES (2, 'Priya Sharma', 'priya@gmail.com', 2000.00, 5678);
    COMMIT;
    ```

## STEP 3: Run the App

1.  Open your terminal in VS Code.
2.  Run this command (Make sure `ojdbc6.jar` is in the `lib` folder!):
    ```powershell
    javac -cp ".;lib/ojdbc6.jar" *.java
    java -cp ".;lib/ojdbc6.jar" OracleBankingApp
    ```
