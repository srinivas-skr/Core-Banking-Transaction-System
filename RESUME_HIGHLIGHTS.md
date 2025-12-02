# Corrected Resume & Interview Script (Oracle Version)

**Project Title:** Secure Core Banking System (Oracle Backend)

## 1. Core Java Concepts (The Foundation)

*   **Object-Oriented Programming (OOP):**
    *   **Encapsulation:** "I created a User class (POJO) to temporarily hold data securely after fetching it from the database, using private variables and Getters/Setters."
    *   **Exception Handling:** "I used try-catch blocks specifically to handle `SQLException`. If the Oracle database connection drops, my code catches the error gracefully instead of crashing."

## 2. Logic & Control Flow

*   **JDBC (The Main Skill):** "Instead of storing data in variables (which get deleted), I used JDBC to make the data persistent."
*   **Control Structures:** "Used a `do-while` loop for the Menu to ensure the user stays logged in until they explicitly click 'Exit'."

## 3. Database Concepts (The "Star" Feature)

*   **ACID Properties (Crucial):** "I implemented the **Atomicity** principle in the 'Transfer Money' feature. I disabled auto-commit (`conn.setAutoCommit(false)`). If money is deducted from Sender but fails to reach Receiver, my code performs a **Rollback** to prevent data loss."
*   **SQL Queries:**
    *   **Login:** Uses `SELECT * FROM accounts WHERE email = ?` (Replaces HashMap).
    *   **History:** Uses `INSERT INTO transactions` (Replaces ArrayList).
    *   **Security:** Uses `PreparedStatement` to prevent SQL Injection attacks.

---

## 📝 Cheat Sheet for your Resume

**Project Title:** Secure Core Banking Transaction System
**Role:** Backend Java Developer
**Tech Stack:** Java 17, Oracle Database 21c (XE), JDBC, SQL.

**Bullet Points:**
*   **Backend Logic:** Developed a console-based banking application using Java and Oracle Database to manage user accounts and financial data.
*   **Transaction Management:** Implemented ACID properties using JDBC Transaction Management (Commit/Rollback) to ensure 100% data consistency during fund transfers.
*   **Database Security:** Utilized `PreparedStatement` to execute secure SQL queries, preventing SQL Injection vulnerabilities during login and transactions.
*   **Persistence:** Designed a normalized Database Schema in Oracle to store customer details and transaction history permanently.

## 💡 One-Liner for the Interviewer

> "I built a Core Banking System connected to an Oracle Database. It handles transaction rollbacks so that money is never lost during a transfer failure, mimicking real banking architecture."
