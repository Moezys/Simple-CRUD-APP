# Contact Manager (Command-Line) in Java

A simple console application written in Java that performs CRUD (Create, Read, Update, Delete) operations on a MySQL `contacts` table via JDBC.

## Prerequisites
- Java 11 or newer
- MySQL server running locally
- Maven (or Gradle)

## Database Setup
```sql
CREATE DATABASE qa_contacts;
USE qa_contacts;

CREATE TABLE contacts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  email VARCHAR(100)
);

## How to Build & Run
1. Clone the repository:
Edit the JDBC_URL, DB_USER, and DB_PASS in ContactManager.java to match your local MySQL credentials.

2. Compile with Maven:
mvn clean compile

3. Run the application:
mvn exec:java -Dexec.mainClass="com.mycompany.contacts.ContactManager"

Follow the on-screen menu to list, add, update, and delete contacts.

