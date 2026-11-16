# Bank Management System (BMS)

A Java-based command-line banking application built with **Java, JDBC, PostgreSQL, and Maven**. The application provides customer authentication, account management, financial transactions, loan repayment, and transaction history while emphasizing secure data handling and transactional integrity.

## Overview

The Bank Management System (BMS) simulates the core functionality of a banking platform through an interactive CLI.

Customers can register, authenticate, manage multiple account types, perform financial transactions, and review their transaction history.

The application follows a layered architecture that separates user interaction, business logic, database access, and domain models.

## Features

### Customer Management
- Customer registration and password management.
- Username and password authentication.
- Password hashing using a salted adaptive hashing algorithm.
- Secure logout functionality.

### Account Management
- Create and manage multiple accounts.
- Support for Savings, Current, Deposit, and Loan accounts.
- Account-specific business rules and balance restrictions.
- Account status management.
- Account balance inquiries.

### Financial Transactions
- Deposits and withdrawals.
- Transfers between eligible accounts.
- Loan repayments.
- Transaction history.
- Transaction records containing account references, amounts, timestamps, and resulting balances.

### Loan Management
- Loan account creation.
- Loan principal and repayment tracking.
- Partial and full loan repayments.
- Automatic account closure when the outstanding loan balance reaches zero.
- Prevention of loan overpayments.

### Database and Transaction Integrity
- PostgreSQL relational database.
- JDBC-based database access.
- Foreign key constraints and relational integrity.
- Parameterized SQL queries.
- JDBC transactions for operations involving multiple database writes.
- Rollback handling when transactional operations fail.

## Account Types

| Account Type | Capabilities |
|---|---|
| Savings | Deposits, withdrawals, and transfers |
| Current | Deposits, withdrawals, and transfers with an overdraft limit |
| Deposit | Fixed-term deposit with maturity restrictions |
| Loan | Loan balance tracking and repayments |

Each account type follows its own business rules enforced by the service layer.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| JDBC | Database connectivity |
| PostgreSQL | Relational database |
| Maven | Dependency and build management |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies and isolating service tests |
| Git | Version control |

## Architecture

The application uses a layered architecture to separate responsibilities and improve maintainability.

```text
                Application
                     |
                  Menus
                     |
                  Services
                     |
                   DAOs
                     |
                  JDBC
                     |
                PostgreSQL
```
### Project Structure

```text
bank-management-system/
├── sql/
│   └── schema.sql
├── src/
│   ├── main/java/com/sam/bank/
│   │   ├── app/
│   │   ├── dao/
│   │   ├── enums/
│   │   ├── exception/
│   │   ├── interfaces/
│   │   ├── model/
│   │   ├── security/
│   │   ├── service/
│   │   ├── util/
│   │   └── validation/
│   └── test/java/com/sam/bank/
├── .env.example
├── .gitignore
└── pom.xml
```

### Layer Responsibilities

**Application / Menu Layer**
- Handles CLI navigation and user input.
- Displays information and prompts.
- Coordinates application workflows.

**Service Layer**
- Enforces business rules.
- Validates financial operations.
- Coordinates multiple DAO operations.
- Manages transactional workflows.

**DAO Layer**
- Executes SQL statements.
- Retrieves and persists application data.
- Maps database records to Java models.

**Model Layer**
- Represents domain entities such as Customers, Accounts, and Transactions.

**Utility Layer**
- Provides database connection management.
- Handles JDBC transaction execution.
- Supports shared application functionality.

## Database Design

The application uses PostgreSQL to maintain relational data.

Primary tables include:

| Table | Responsibility |
|---|---|
| customers | Customer profiles and authentication data |
| accounts | Account information, balances, and statuses |
| deposit_details | Fixed-term deposit information |
| loan_details | Loan-specific information |
| transactions | Financial transaction records |
| login_audit | Login activity records | 
| transaction_audit | Transaction audit information |

Foreign keys maintain relationships between customers, accounts, and their associated records.

## Testing

The project uses JUnit 5 and Mockito to test application behavior.

Current testing includes:
- Service-level unit tests.
- Deposit and withdrawal validation.
- Transfer balance calculations.
- Loan repayment calculations.
- Transaction record creation.
- Invalid transaction handling.
- Loan account closure.
- Transaction history retrieval.

Run the test suite:

```bash
mvn clean test
```

## Getting Started

### Prerequisites

Make sure you have the following installed:

- JDK 21
- Maven
- PostgreSQL
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/Samueljkauff/P1-Sam-Kauffman-BMS.git

cd P1-Sam-Kauffman-BMS/bank-management-system
```

### 2. Create the Database

Create a PostgreSQL database:

```sql
CREATE DATABASE bank_management;
```

### 3. Configure Database Connectivity

Create a `.env` file in the `bank-management-system` project root, alongside `pom.xml`.

Add the following configuration:

```env
DB_URL=jdbc:postgresql://localhost:5432/bank_management
DB_USERNAME=YOUR_USERNAME
DB_PASSWORD=YOUR_PASSWORD
```

Replace the placeholders with your PostgreSQL credentials.

### 4. Initialize the Database

Execute the project's database schema script against the `bank_management` database.

For example:

```bash
psql -U YOUR_USERNAME -d bank_management -f sql/schema.sql
```

### 5. Build and Run

From the `bank-management-system` directory, build the application:

```bash
mvn clean package
```

Run the application:

```bash
mvn exec:java
```

The Maven Exec plugin is configured to launch `com.sam.bank.app.App`.

## Project Scope

This project focuses on implementing core banking operations and demonstrating object-oriented programming, relational database design, JDBC, and transactional application development.

The application is a simulation and does not connect to real financial institutions or process actual monetary transactions.

## Future Enhancements

Potential additions include:

- **Financial Intelligence:** Monthly financial summaries, cash-flow analysis, and transaction trends.
- **Suspicious Activity Detection:** Rule-based identification of unusual transaction patterns.
- **Scheduled Transfers:** Recurring and future-dated transactions.
- **Savings Goals:** Customer-defined savings targets and progress tracking.
- **Loan Amortization:** Detailed repayment schedules and interest calculations.

## Author

**Sam Kauffman**

Computer Science Graduate | Java Developer

GitHub: [Samueljkauff](https://github.com/Samueljkauff)
