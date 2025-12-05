# Core Banking Transaction System

Console-based banking application with Oracle Database integration.

## Requirements

- Java 17+
- Maven 3.6+
- Oracle Database 11g XE

## Build & Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.corebanking.CoreBankingApplication"
```

## Database Setup

```sql
-- Run as SYSDBA
CREATE USER banking_user IDENTIFIED BY pass123;
GRANT CONNECT, RESOURCE TO banking_user;

-- Run as banking_user (see sql/ for full schema)
```

## License

[MIT](LICENSE)
