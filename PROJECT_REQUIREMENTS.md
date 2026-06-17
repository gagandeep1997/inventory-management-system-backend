# Inventory Management System

## 1. Project Overview

The Inventory Management System is a backend application designed to manage products, warehouses, inventory levels, and stock transfers between warehouses.

The system allows administrators and managers to maintain accurate inventory records, monitor stock availability, and perform inventory operations securely through RESTful APIs.

The application will initially be developed as a monolithic Spring Boot application and later converted into a microservices architecture.

---

# 2. Business Problem

Organizations that operate multiple warehouses need a centralized system to:

* Manage products
* Track inventory levels
* Store inventory in multiple warehouses
* Transfer stock between warehouses
* Prevent inventory inconsistencies
* Maintain accurate stock records

The system should ensure that inventory data remains accurate and that stock movements are properly tracked.

---

# 3. Actors

## 3.1 Admin

The Admin has full access to the system.

Responsibilities:

* Create products
* Update products
* Delete products
* Create warehouses
* Update warehouses
* View inventory
* Transfer stock between warehouses
* View transfer history
* Manage users

---

## 3.2 Manager

The Manager has limited access.

Responsibilities:

* View products
* View warehouses
* View inventory
* Add stock
* Remove stock
* View transfer history

Managers cannot:

* Delete products
* Create users
* Manage system settings

---

# 4. Functional Requirements

## 4.1 Authentication Module

The system must support user authentication and authorization.

Features:

* User registration
* User login
* JWT token generation
* Role-based access control
* Protected APIs

Roles:

* ADMIN
* MANAGER

---

## 4.2 Product Management Module

The system must allow product management.

### Product Attributes

* Product ID
* SKU
* Name
* Description
* Price
* Created Date
* Updated Date

### Features

* Create Product
* Update Product
* Delete Product
* Get Product By ID
* Get All Products
* Search Products

### Business Rules

* SKU must be unique
* Product name cannot be empty
* Product price must be greater than zero

---

## 4.3 Warehouse Management Module

The system must allow warehouse management.

### Warehouse Attributes

* Warehouse ID
* Name
* Location
* Created Date

### Features

* Create Warehouse
* Update Warehouse
* Get Warehouse By ID
* Get All Warehouses

### Business Rules

* Warehouse name cannot be empty
* Warehouse location cannot be empty

---

## 4.4 Inventory Management Module

The system must track inventory levels for each product in each warehouse.

### Inventory Attributes

* Inventory ID
* Product
* Warehouse
* Quantity
* Last Updated Date

### Features

* Add Stock
* Remove Stock
* View Inventory
* View Inventory By Warehouse
* View Inventory By Product

### Business Rules

* Inventory quantity cannot be negative
* Inventory quantity must always be maintained accurately
* Stock removal should fail if available stock is insufficient

---

## 4.5 Stock Transfer Module

The system must support stock transfers between warehouses.

### Transfer Attributes

* Transfer ID
* Source Warehouse
* Destination Warehouse
* Product
* Quantity
* Status
* Created Date

### Features

* Create Transfer
* View Transfer By ID
* View Transfer History

### Business Rules

* Source warehouse must contain sufficient stock
* Source and destination warehouses must exist
* Transfer quantity must be greater than zero
* Inventory deduction and inventory addition must occur in a single transaction
* Transfer records must be stored for auditing purposes

---

# 5. Business Rules

## BR-001

Product SKU must be unique.

---

## BR-002

Product price must be greater than zero.

---

## BR-003

Inventory quantity cannot become negative.

---

## BR-004

Stock transfer can only occur if sufficient stock exists.

---

## BR-005

Stock transfer must be atomic.

Meaning:

Either:

* Deduct stock from source warehouse
* Add stock to destination warehouse

Both operations succeed.

Or:

* Both operations fail.

No partial updates are allowed.

---

## BR-006

Only authenticated users can access protected APIs.

---

## BR-007

Users can only access functionality permitted by their role.

---

# 6. Non-Functional Requirements

## Security

The system must:

* Use Spring Security
* Use JWT Authentication
* Protect sensitive endpoints
* Implement role-based authorization

---

## Performance

The system must support:

* Pagination
* Sorting
* Efficient database queries

---

## Reliability

The system must:

* Return meaningful error responses
* Handle exceptions gracefully
* Maintain transaction integrity

---

## Maintainability

The application must follow a layered architecture:

* Controller Layer
* Service Layer
* Repository Layer

The application must use:

* DTOs
* Global Exception Handling
* Validation

---

## Scalability

The application should be designed so it can later be converted into:

* Auth Service
* Product Service
* Inventory Service

using a microservices architecture.

---

# 7. Technology Stack

## Backend

* Java 25
* Spring Boot 4

## Database

* MySQL

## Security

* Spring Security
* JWT

## ORM

* Spring Data JPA
* Hibernate

## API Documentation

* Swagger / OpenAPI

## Containerization

* Docker
* Docker Compose

## Microservices (Future Phase)

* Eureka Server
* Spring Cloud Gateway
* OpenFeign

---

# 8. Project Goals

By completing this project, the developer should demonstrate knowledge of:

* Java
* Object-Oriented Programming
* RESTful API Design
* Spring Boot
* Spring Security
* JWT Authentication
* JPA/Hibernate
* Database Design
* Transaction Management
* Exception Handling
* Validation
* Docker
* Microservices
* Service Discovery
* API Gateway
* Inter-Service Communication
