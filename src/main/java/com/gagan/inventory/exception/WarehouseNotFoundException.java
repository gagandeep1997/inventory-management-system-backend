package com.gagan.inventory.exception;

public class WarehouseNotFoundException extends RuntimeException {

    public WarehouseNotFoundException(Long id) {
        super("Warehouse not found with id: " + id);
    }

}
