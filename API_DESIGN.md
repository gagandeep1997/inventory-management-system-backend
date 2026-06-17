# API Design Document

## Project: Inventory Management System

### Base URL

```http
/api/v1
```

---

# 1. Authentication APIs

## Register User

### Endpoint

```http
POST /api/v1/auth/register
```

### Description

Creates a new user account.

### Request

```json
{
  "name": "Gagan Deep",
  "email": "gagan@gmail.com",
  "password": "Password@123",
  "role": "MANAGER"
}
```

### Success Response

```json
{
  "message": "User registered successfully"
}
```

### Status Codes

| Status Code | Meaning                   |
| ----------- | ------------------------- |
| 201         | User created successfully |
| 400         | Validation failed         |
| 409         | Email already exists      |

---

## Login User

### Endpoint

```http
POST /api/v1/auth/login
```

### Description

Authenticates a user and returns a JWT token.

### Request

```json
{
  "email": "gagan@gmail.com",
  "password": "Password@123"
}
```

### Success Response

```json
{
  "accessToken": "jwt-token"
}
```

### Status Codes

| Status Code | Meaning             |
| ----------- | ------------------- |
| 200         | Login successful    |
| 401         | Invalid credentials |

---

# 2. Product APIs

## Create Product

### Endpoint

```http
POST /api/v1/products
```

### Authorization

ADMIN

### Request

```json
{
  "sku": "LAPTOP-001",
  "name": "MacBook Pro",
  "description": "16 inch laptop",
  "price": 2500
}
```

### Success Response

```json
{
  "id": 1,
  "sku": "LAPTOP-001",
  "name": "MacBook Pro",
  "description": "16 inch laptop",
  "price": 2500
}
```

### Status Codes

| Status Code | Meaning            |
| ----------- | ------------------ |
| 201         | Product created    |
| 400         | Invalid request    |
| 409         | SKU already exists |

---

## Get Product By Id

### Endpoint

```http
GET /api/v1/products/{id}
```

### Example

```http
GET /api/v1/products/1
```

### Success Response

```json
{
  "id": 1,
  "sku": "LAPTOP-001",
  "name": "MacBook Pro",
  "description": "16 inch laptop",
  "price": 2500
}
```

### Status Codes

| Status Code | Meaning           |
| ----------- | ----------------- |
| 200         | Success           |
| 404         | Product not found |

---

## Get All Products

### Endpoint

```http
GET /api/v1/products
```

### Pagination

```http
GET /api/v1/products?page=0&size=10
```

### Sorting

```http
GET /api/v1/products?sort=name,asc
```

### Filter By SKU

```http
GET /api/v1/products?sku=LAPTOP-001
```

### Success Response

```json
{
  "content": [
    {
      "id": 1,
      "sku": "LAPTOP-001",
      "name": "MacBook Pro",
      "price": 2500
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1
}
```

---

## Update Product

### Endpoint

```http
PUT /api/v1/products/{id}
```

### Request

```json
{
  "sku": "LAPTOP-001",
  "name": "MacBook Pro M5",
  "description": "Updated Model",
  "price": 2800
}
```

### Status Codes

| Status Code | Meaning           |
| ----------- | ----------------- |
| 200         | Updated           |
| 404         | Product not found |

---

## Delete Product

### Endpoint

```http
DELETE /api/v1/products/{id}
```

### Status Codes

| Status Code | Meaning              |
| ----------- | -------------------- |
| 204         | Deleted successfully |
| 404         | Product not found    |

---

# 3. Warehouse APIs

## Create Warehouse

### Endpoint

```http
POST /api/v1/warehouses
```

### Authorization

ADMIN

### Request

```json
{
  "name": "Delhi Warehouse",
  "location": "Delhi"
}
```

### Success Response

```json
{
  "id": 1,
  "name": "Delhi Warehouse",
  "location": "Delhi"
}
```

---

## Get Warehouse By Id

### Endpoint

```http
GET /api/v1/warehouses/{id}
```

### Success Response

```json
{
  "id": 1,
  "name": "Delhi Warehouse",
  "location": "Delhi"
}
```

---

## Get All Warehouses

### Endpoint

```http
GET /api/v1/warehouses
```

### Success Response

```json
[
  {
    "id": 1,
    "name": "Delhi Warehouse",
    "location": "Delhi"
  }
]
```

---

## Update Warehouse

### Endpoint

```http
PUT /api/v1/warehouses/{id}
```

### Request

```json
{
  "name": "New Delhi Warehouse",
  "location": "Delhi NCR"
}
```

---

# 4. Inventory APIs

Inventory represents stock available for a product inside a warehouse.

Example:

MacBook Pro + Delhi Warehouse + Quantity 100

---

## Add Inventory

### Endpoint

```http
POST /api/v1/inventory
```

### Request

```json
{
  "productId": 1,
  "warehouseId": 1,
  "quantity": 100
}
```

### Meaning

Add 100 MacBooks to Delhi Warehouse.

### Success Response

```json
{
  "id": 1,
  "productId": 1,
  "warehouseId": 1,
  "quantity": 100
}
```

---

## Get All Inventory

### Endpoint

```http
GET /api/v1/inventory
```

### Success Response

```json
[
  {
    "productId": 1,
    "warehouseId": 1,
    "quantity": 100
  }
]
```

---

## Get Inventory By Product

### Endpoint

```http
GET /api/v1/inventory?productId=1
```

### Purpose

Shows stock of a specific product across warehouses.

---

## Get Inventory By Warehouse

### Endpoint

```http
GET /api/v1/inventory?warehouseId=1
```

### Purpose

Shows all products stored in a warehouse.

---

## Update Inventory Quantity

### Endpoint

```http
PATCH /api/v1/inventory/{id}
```

### Request

```json
{
  "quantity": 120
}
```

### Purpose

Updates stock quantity.

---

# 5. Stock Transfer APIs

Stock Transfer moves inventory from one warehouse to another.

Example:

Move 20 MacBooks from Delhi Warehouse to Mumbai Warehouse.

---

## Create Transfer

### Endpoint

```http
POST /api/v1/transfers
```

### Request

```json
{
  "sourceWarehouseId": 1,
  "destinationWarehouseId": 2,
  "productId": 1,
  "quantity": 20
}
```

### Success Response

```json
{
  "transferId": 1,
  "status": "COMPLETED"
}
```

### Business Flow

1. Verify source warehouse exists.
2. Verify destination warehouse exists.
3. Verify product exists.
4. Verify sufficient stock.
5. Deduct stock from source warehouse.
6. Add stock to destination warehouse.
7. Save transfer record.
8. Return response.

---

## Get Transfer By Id

### Endpoint

```http
GET /api/v1/transfers/{id}
```

### Success Response

```json
{
  "transferId": 1,
  "sourceWarehouseId": 1,
  "destinationWarehouseId": 2,
  "productId": 1,
  "quantity": 20,
  "status": "COMPLETED"
}
```

---

## Get All Transfers

### Endpoint

```http
GET /api/v1/transfers
```

### Purpose

Returns transfer history.

---

# 6. Validation Rules

## Product

* SKU is required
* SKU must be unique
* Name is required
* Price must be greater than zero

---

## Warehouse

* Name is required
* Location is required

---

## Inventory

* Product must exist
* Warehouse must exist
* Quantity must be greater than zero

---

## Transfer

* Source warehouse must exist
* Destination warehouse must exist
* Product must exist
* Quantity must be greater than zero
* Source warehouse cannot equal destination warehouse
* Source warehouse must have sufficient stock

---

# 7. Standard Error Response

All API errors should follow the same structure.

Example:

```json
{
  "timestamp": "2026-06-17T12:00:00",
  "status": 404,
  "message": "Product not found"
}
```

---

# 8. Security

Protected APIs require JWT token.

Example Header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Roles:

* ADMIN
* MANAGER

Authorization Rules:

* ADMIN can manage products and warehouses.
* MANAGER can manage inventory and view data.
* Both roles can access authorized resources according to permissions.
