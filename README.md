# Core Banking Transaction System

A console-based banking application built with Java and Oracle Database.

## Features

- Secure authentication with SQL injection prevention
- ACID-compliant fund transfers with rollback support  
- Transaction history tracking
- Layered architecture (Repository-Service-UI)

## Requirements

- Java 17+
- Oracle Database 11g XE

## Quick Start

```bash
# Setup database
sqlplus sys/password@XE as sysdba @sql/schema.sql
sqlplus banking_user/pass123@XE @sql/seed.sql

# Build and run
build.bat
run.bat
```

## Project Structure

```
src/main/java/com/corebanking/
|-- config/        # Database configuration
|-- model/         # Domain entities  
|-- dto/           # Data transfer objects
|-- exception/     # Custom exceptions
|-- repository/    # Data access layer
|-- service/       # Business logic
+-- ui/            # Console interface
```

## License

[MIT](LICENSE)
