package com.gagan.inventory.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String sku) {
        super("SKU already exists with sku " + sku);
    }
}
