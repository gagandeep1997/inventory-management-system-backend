package com.gagan.inventory.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }

    public ResourceNotFoundException(String resourceName,
                                     String fieldName,
                                     Object fieldValue) {
        super(resourceName + " not found with " + fieldName + ": " + fieldValue);
    }
}
