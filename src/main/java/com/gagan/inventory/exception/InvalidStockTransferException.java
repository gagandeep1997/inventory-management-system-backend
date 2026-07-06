package com.gagan.inventory.exception;

public class InvalidStockTransferException extends RuntimeException {
    public InvalidStockTransferException(String message) {
        super(message);
    }
}