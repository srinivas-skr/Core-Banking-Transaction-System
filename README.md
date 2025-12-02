# Core Banking Transaction System

A **Console-Based Core Banking System** built with **Java** and **Oracle Database 11g XE**. This application simulates real-world banking operations with secure authentication, ACID-compliant fund transfers, and persistent transaction history.

---

## 🛠 Technology Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| **Language** | Java 17+ | Core application logic |
| **Database** | Oracle Database 11g XE | Persistent data storage |
| **Connectivity** | JDBC (ojdbc6.jar) | Java-to-Database communication |
| **SQL** | Oracle PL/SQL | Data manipulation & queries |

---

## 📁 Project Structure

```
Core-Banking-Transaction-System/
├── ConnectionManager.java    # Database connection handler
├── AccountManager.java       # Business logic (Login, Transfer, History)
├── OracleBankingApp.java     # Main application entry point
├── lib/
│   └── ojdbc6.jar            # Oracle JDBC Driver
├── database_setup.sql        # Table creation scripts
├── init_db.sql               # Automated DB setup script
├── setup_database.bat        # One-click database setup
└── start_app.bat             # One-click application launcher
```

---

## ✨ Key Features

### 1. Secure Login (SQL Injection Prevention)
- Uses **PreparedStatement** with parameterized queries
- User credentials verified against database

### 2. ACID-Compliant Fund Transfer
- **Atomicity**: Both debit and credit succeed or fail together
- **Consistency**: Balance validation before transfer
- **Isolation**: Each transaction is independent
- **Durability**: Data persisted after commit

### 3. Transaction History
- Every transfer logged with timestamp
- Permanent record in database

---

## 🗄 Database Schema

**Table: `accounts`**
| Column | Type | Description |
|--------|------|-------------|
| account_number | NUMBER(10) | Primary Key |
| full_name | VARCHAR2(50) | Customer name |
| email | VARCHAR2(50) | Unique login ID |
| balance | NUMBER(10,2) | Current balance |
| security_pin | NUMBER(4) | 4-digit PIN |

**Table: `transactions`**
| Column | Type | Description |
|--------|------|-------------|
| transaction_id | NUMBER(10) | Primary Key |
| account_number | NUMBER(10) | Foreign Key |
| transaction_type | VARCHAR2(20) | Transfer Sent/Received |
| amount | NUMBER(10,2) | Transaction amount |
| balance_after | NUMBER(10,2) | Balance after transaction |
| transaction_date | TIMESTAMP | Auto-generated |

---

## 🚀 How to Run

### Prerequisites
- Java JDK 17+
- Oracle Database 11g XE installed

### Step 1: Setup Database (One-time)
```
Double-click: setup_database.bat
Enter Oracle password when prompted
```

### Step 2: Run Application
```
Double-click: start_app.bat
```

### Test Credentials
| User | Email | PIN | Balance |
|------|-------|-----|---------|
| Ravi Kumar | ravi@gmail.com | 1234 | Rs.5000.00 |
| Priya Sharma | priya@gmail.com | 5678 | Rs.2000.00 |

---

## 📸 Application Menu

```
--- Banking Menu ---
1. Check Balance
2. Transfer Money
3. Transaction History
4. Logout
```

---

## 👨‍💻 Author

**Srinivas**  
Backend Java Developer

---

## 📄 License

This project is for educational purposes.