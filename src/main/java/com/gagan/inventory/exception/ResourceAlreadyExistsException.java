package com.gagan.inventory.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(resourceName + " already exists with " + fieldName + ": " + fieldValue);
    }
}
