# Oracle Core Banking System Setup Guide

This project implements a core banking system using Oracle Database XE, focusing on ACID properties and transaction management.

## Prerequisites

1.  **Oracle Database 21c Express Edition (XE)**:
    *   Download and install from Oracle's website.
    *   During installation, set the password to `admin123`.

2.  **Oracle JDBC Driver (`ojdbc8.jar`)**:
    *   Download `ojdbc8.jar` from the Oracle website or Maven Central.
    *   Place the `ojdbc8.jar` file in the `lib` folder of this project.

## Database Setup

1.  Open a Command Prompt (cmd).
2.  Connect to Oracle as SYSDBA:
    ```cmd
    sqlplus sys/admin123@//localhost:1521/XE as sysdba
    ```
3.  Create the banking user:
    ```sql
    CREATE USER banking_user IDENTIFIED BY pass123;
    GRANT CONNECT, RESOURCE, DBA TO banking_user;
    EXIT;
    ```
4.  Connect as the new user:
    ```cmd
    sqlplus banking_user/pass123@//localhost:1521/XE
    ```
5.  Run the SQL commands found in `database_setup.sql` to create the table and insert dummy data.

## Running the Application

1.  Compile the Java files (ensure `ojdbc8.jar` is in the classpath):
    ```cmd
    javac -cp .;lib/ojdbc8.jar *.java
    ```
    *(Note: On Linux/Mac, use `:` instead of `;` for the classpath separator)*

2.  Run the application:
    ```cmd
    java -cp .;lib/ojdbc8.jar OracleBankingApp
    ```

3.  Follow the on-screen prompts to transfer money between accounts (e.g., from Account 1 to Account 2).
