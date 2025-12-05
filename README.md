# Core Banking Transaction System

A **production-grade Console Banking Application** demonstrating enterprise Java development practices with **Oracle Database** integration and **ACID-compliant** transactions.

---

## 🏗️ Architecture Overview

This project follows **Domain-Driven Design (DDD)** and **Layered Architecture** principles:

```
src/main/java/com/corebanking/
├── CoreBankingApplication.java    # Application entry point
├── config/                        # Configuration & DB connection
│   ├── DatabaseConfig.java        # Centralized DB settings
│   └── ConnectionManager.java     # JDBC connection factory
├── model/                         # Domain entities
│   ├── Account.java               # Account entity
│   ├── Transaction.java           # Transaction entity
│   └── TransactionType.java       # Type-safe enum
├── dto/                           # Data Transfer Objects
│   ├── LoginRequest.java          # Login input DTO
│   ├── LoginResponse.java         # Login output DTO (no sensitive data)
│   ├── TransferRequest.java       # Transfer input DTO
│   └── TransferResult.java        # Transfer output DTO
├── exception/                     # Custom exceptions
│   ├── BankingException.java      # Base exception
│   ├── AuthenticationException.java
│   ├── AccountNotFoundException.java
│   ├── InsufficientFundsException.java
│   └── TransferException.java
├── repository/                    # Data Access Layer
│   ├── AccountRepository.java     # Interface
│   ├── TransactionRepository.java # Interface
│   └── impl/                      # JDBC implementations
│       ├── AccountRepositoryImpl.java
│       └── TransactionRepositoryImpl.java
├── service/                       # Business Logic Layer
│   ├── AuthenticationService.java # Interface
│   ├── AccountService.java        # Interface
│   └── impl/                      # Implementations
│       ├── AuthenticationServiceImpl.java
│       └── AccountServiceImpl.java  # ACID transfer logic
└── ui/                            # Presentation Layer
    └── ConsoleUI.java             # User interaction
```

---

## 🛠️ Technology Stack

| Component | Technology | Purpose |
|-----------|------------|---------|
| Language | Java 17+ | Core application |
| Database | Oracle 11g XE | Persistent storage |
| Connectivity | JDBC | Database communication |
| Architecture | Layered/DDD | Enterprise patterns |

---

## ✨ Key Technical Features

### 1. ACID-Compliant Fund Transfers
```java
// Atomicity: All-or-nothing transactions
conn.setAutoCommit(false);  // Start transaction
// ... debit sender, credit receiver, log history ...
conn.commit();  // Success: persist all changes
// OR
conn.rollback();  // Failure: undo everything
```

### 2. SQL Injection Prevention
```java
// Using PreparedStatement with parameterized queries
PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM accounts WHERE email = ? AND pin = ?"
);
ps.setString(1, email);  // Safe parameter binding
ps.setInt(2, pin);
```

### 3. Separation of Concerns
- **UI Layer**: Only handles user interaction
- **Service Layer**: Contains all business logic
- **Repository Layer**: Data access abstraction
- **Model Layer**: Domain entities

### 4. DTO Pattern
Never expose entities directly - use DTOs to control data flow:
```java
// LoginResponse excludes sensitive PIN
return new LoginResponse(account.getNumber(), account.getName(), account.getBalance());
```

---

## 🚀 Quick Start

### Prerequisites
- Java JDK 17+
- Oracle Database 11g XE

### Step 1: Setup Database
```batch
setup_database.bat
```

### Step 2: Build & Run
```batch
build.bat    # Compile
run.bat      # Execute
```

### Test Credentials

| User | Email | PIN | Balance |
|------|-------|-----|---------|
| Ravi Kumar | ravi@gmail.com | 1234 | ₹5,000.00 |
| Priya Sharma | priya@gmail.com | 5678 | ₹2,000.00 |

---

## 📁 Project Structure

```
Core-Banking-Transaction-System/
├── src/
│   └── main/
│       └── java/
│           └── com/corebanking/    # All source code
├── lib/
│   └── ojdbc6.jar                  # Oracle JDBC driver
├── target/
│   └── classes/                    # Compiled bytecode
├── sql/
│   ├── schema.sql                  # Table definitions
│   └── seed.sql                    # Test data
├── build.bat                       # Build script
├── run.bat                         # Run script
├── setup_database.bat              # DB setup script
├── .gitignore
└── README.md
```

---

## 🔒 Security Highlights

1. **No hardcoded credentials in code** - Config class can be extended to use environment variables
2. **PreparedStatements everywhere** - No SQL injection vulnerabilities
3. **PIN never exposed in DTOs** - LoginResponse excludes sensitive data
4. **Input validation** - All user inputs validated before processing

---

## 📝 Resume Points

> "Designed and implemented a **Core Banking System** using **Java 17** with **Oracle DB** integration, featuring **ACID-compliant transactions**, **layered architecture** (Controller-Service-Repository pattern), and **SQL injection prevention** using PreparedStatements."

### Skills Demonstrated:
- Object-Oriented Design
- JDBC & Database Transactions
- Exception Handling
- Design Patterns (Repository, DTO, Factory)
- Clean Code Principles

---

## 📄 License

MIT License - See [LICENSE](LICENSE) file.
