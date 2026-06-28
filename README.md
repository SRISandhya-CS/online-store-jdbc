# Online Store Inventory Manager

A simple console-based inventory management system built in Java. It connects to a PostgreSQL database via JDBC and lets users manage a product catalog through a menu-driven interface.

## Features

- View all products in the inventory
- Add a new product (name, price, stock level)
- Search for products by name (case-insensitive partial match)
- Exit the application

## Tech Stack

- **Java** — core application logic
- **PostgreSQL** — database
- **JDBC** — database connectivity
- **PreparedStatement** — used for all queries to prevent SQL injection

## Database Schema

```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_level INT NOT NULL
);
```

## Prerequisites

- Java JDK 8 or higher
- PostgreSQL installed and running
- PostgreSQL JDBC driver (`postgresql-x.x.x.jar`) added to your classpath

## Setup

1. **Create the database and table:**
   ```sql
   CREATE DATABASE online_store;
   \c online_store;

   CREATE TABLE products (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       price DECIMAL(10,2) NOT NULL,
       stock_level INT NOT NULL
   );
   ```

2. **Configure the connection** in `DatabaseConnection.java`:
   ```java
   String url = "jdbc:postgresql://localhost:5432/online_store";
   String username = "postgres";
   String password = "your_password";
   ```

3. **Compile and run:**
   ```
   javac -cp .:postgresql-x.x.x.jar DatabaseConnection.java
   java -cp .:postgresql-x.x.x.jar DatabaseConnection
   ```
   *(On Windows, use `;` instead of `:` in the classpath.)*

## Usage

Once running, you'll see a menu:

```
--- Online Store Menu ---
1. View all products
2. Add a new product
3. Search for a product
4. Exit
```

Enter the number corresponding to your choice and follow the prompts.

## Project Structure

```
.
├── DatabaseConnection.java   # Main application file
└── README.md
```
anaging an online store's product inventory using JDBC and PostgreSQL.
[README.md](https://github.com/user-attachments/files/29429054/README.md)
