package com.local.prices.application.exception;

public class PricesServiceException extends RuntimeException {
    public PricesServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
