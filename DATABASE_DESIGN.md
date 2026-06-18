# Database Design Document

## Project: Inventory Management System

---

# 1. Overview

The Inventory Management System manages:

* Users
* Products
* Warehouses
* Inventory
* Stock Transfers

The system is designed using a relational database model and follows normalization principles to avoid data duplication and maintain data integrity.

---

# 2. Database Tables

The system contains the following tables:

1. users
2. products
3. warehouses
4. inventory
5. stock_transfers
6. transfer_items

---

# 3. Users Table

## Purpose

Stores application users who can access the system.

## Table Name

```sql
users
```

## Columns

| Column     | Type         | Constraints      |
| ---------- | ------------ | ---------------- |
| id         | BIGINT       | Primary Key      |
| name       | VARCHAR(50)  | Not Null         |
| email      | VARCHAR(255) | Unique, Not Null |
| password   | VARCHAR(255) | Not Null         |
| role       | VARCHAR(20)  | Not Null         |
| created_at | TIMESTAMP    | Not Null         |
| updated_at | TIMESTAMP    | Not Null         |

## Sample Data

| id | name         | email                                     | role    |
| -- | ------------ | ----------------------------------------- | ------- |
| 1  | Gagan Deep   | [gagan@gmail.com](mailto:gagan@gmail.com) | ADMIN   |
| 2  | Rahul Sharma | [rahul@gmail.com](mailto:rahul@gmail.com) | MANAGER |

---

# 4. Products Table

## Purpose

Stores information about products managed by the inventory system.

## Table Name

```sql
products
```

## Columns

| Column      | Type          | Constraints      |
| ----------- | ------------- | ---------------- |
| id          | BIGINT        | Primary Key      |
| sku         | VARCHAR(50)   | Unique, Not Null |
| name        | VARCHAR(255)  | Not Null         |
| description | TEXT          | Nullable         |
| price       | DECIMAL(10,2) | Not Null         |
| created_at  | TIMESTAMP     | Not Null         |
| updated_at  | TIMESTAMP     | Not Null         |

## Sample Data

| id | sku        | name           | price   |
| -- | ---------- | -------------- | ------- |
| 1  | LAPTOP-001 | MacBook Pro    | 2500.00 |
| 2  | MOUSE-001  | Logitech Mouse | 30.00   |

---

# 5. Warehouses Table

## Purpose

Stores warehouse information where inventory is physically stored.

## Table Name

```sql
warehouses
```

## Columns

| Column     | Type         | Constraints |
| ---------- | ------------ | ----------- |
| id         | BIGINT       | Primary Key |
| name       | VARCHAR(50)  | Not Null    |
| location   | VARCHAR(255) | Not Null    |
| created_at | TIMESTAMP    | Not Null    |
| updated_at | TIMESTAMP    | Not Null    |

## Sample Data

| id | name             | location |
| -- | ---------------- | -------- |
| 1  | Delhi Warehouse  | Delhi    |
| 2  | Mumbai Warehouse | Mumbai   |

---

# 6. Inventory Table

## Purpose

Stores stock quantity for a product inside a specific warehouse.

Inventory represents the relationship between Product and Warehouse.

## Table Name

```sql
inventory
```

## Columns

| Column       | Type      | Constraints |
| ------------ | --------- | ----------- |
| id           | BIGINT    | Primary Key |
| product_id   | BIGINT    | Foreign Key |
| warehouse_id | BIGINT    | Foreign Key |
| quantity     | INTEGER   | Not Null    |
| created_at   | TIMESTAMP | Not Null    |
| updated_at   | TIMESTAMP | Not Null    |

## Sample Data

| id | product_id | warehouse_id | quantity |
| -- | ---------- | ------------ | -------- |
| 1  | 1          | 1            | 100      |
| 2  | 1          | 2            | 50       |
| 3  | 2          | 1            | 500      |

## Meaning

| Product        | Warehouse        | Quantity |
| -------------- | ---------------- | -------- |
| MacBook Pro    | Delhi Warehouse  | 100      |
| MacBook Pro    | Mumbai Warehouse | 50       |
| Logitech Mouse | Delhi Warehouse  | 500      |

---

# 7. Stock Transfers Table

## Purpose

Stores information about stock movements between warehouses.

## Table Name

```sql
stock_transfers
```

## Columns

| Column                   | Type        | Constraints |
| ------------------------ | ----------- | ----------- |
| id                       | BIGINT      | Primary Key |
| source_warehouse_id      | BIGINT      | Foreign Key |
| destination_warehouse_id | BIGINT      | Foreign Key |
| status                   | VARCHAR(30) | Not Null    |
| created_at               | TIMESTAMP   | Not Null    |

## Status Values

```text
PENDING
COMPLETED
FAILED
```

## Sample Data

| id | source          | destination      | status    |
| -- | --------------- | ---------------- | --------- |
| 1  | Delhi Warehouse | Mumbai Warehouse | COMPLETED |

---

# 8. Transfer Items Table

## Purpose

Stores products included in a stock transfer.

One transfer can contain multiple products.

## Table Name

```sql
transfer_items
```

## Columns

| Column      | Type    | Constraints |
| ----------- | ------- | ----------- |
| id          | BIGINT  | Primary Key |
| transfer_id | BIGINT  | Foreign Key |
| product_id  | BIGINT  | Foreign Key |
| quantity    | INTEGER | Not Null    |

## Sample Data

| id | transfer_id | product_id | quantity |
| -- | ----------- | ---------- | -------- |
| 1  | 1           | 1          | 20       |
| 2  | 1           | 2          | 50       |

## Meaning

Transfer #1 contains:

* 20 MacBooks
* 50 Logitech Mice

---

# 9. Entity Relationships

## Product → Inventory

One Product can exist in many inventory records.

Relationship:

```text
Product (1) -------- (*) Inventory
```

Example:

```text
MacBook Pro

Delhi Warehouse → 100

Mumbai Warehouse → 50

Bangalore Warehouse → 200
```

---

## Warehouse → Inventory

One Warehouse can contain many inventory records.

Relationship:

```text
Warehouse (1) -------- (*) Inventory
```

---

## Stock Transfer → Transfer Item

One Transfer can contain multiple products.

Relationship:

```text
StockTransfer (1) -------- (*) TransferItem
```

---

## Product → Transfer Item

One Product can appear in many transfer records.

Relationship:

```text
Product (1) -------- (*) TransferItem
```

---

# 10. Database Constraints

## Users

```text
Email must be unique.
```

---

## Products

```text
SKU must be unique.
Price must be greater than zero.
```

---

## Warehouses

```text
Warehouse name cannot be empty.
Location cannot be empty.
```

---

## Inventory

```text
Quantity cannot be negative.
```

---

## Stock Transfers

```text
Source warehouse must exist.
Destination warehouse must exist.
Source warehouse cannot equal destination warehouse.
```

---

# 11. Business Rules

## BR-001

A product can be stored in multiple warehouses.

---

## BR-002

A warehouse can store multiple products.

---

## BR-003

Inventory quantity must never become negative.

---

## BR-004

A stock transfer requires sufficient inventory in the source warehouse.

---

## BR-005

Stock transfer operations must be atomic.

Meaning:

Either:

* Stock is deducted from source warehouse.
* Stock is added to destination warehouse.

Both operations succeed.

Or both operations fail.

---

# 12. ER Diagram

```text
+------------------+
|      USERS       |
+------------------+

+------------------+
|    PRODUCTS      |
+------------------+
          |
          |
          | 1
          |
          | *
+------------------+
|    INVENTORY     |
+------------------+
          *
          |
          |
          | 1
          |
+------------------+
|   WAREHOUSES     |
+------------------+

+------------------+
| STOCK_TRANSFERS  |
+------------------+
          |
          |
          | 1
          |
          | *
+------------------+
| TRANSFER_ITEMS   |
+------------------+
          *
          |
          |
          | 1
          |
+------------------+
|    PRODUCTS      |
+------------------+
```

---

# 13. Future Microservices Mapping

The monolithic application can later be split into:

### Auth Service

Tables:

```text
users
```

### Product Service

Tables:

```text
products
```

### Inventory Service

Tables:

```text
warehouses
inventory
stock_transfers
transfer_items
```

This separation supports future migration to a microservices architecture using Spring Boot, Eureka Server, API Gateway, and OpenFeign.
