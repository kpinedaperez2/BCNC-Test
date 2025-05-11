package com.local.prices.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class PricesCollectionNotFoundException extends EntityNotFoundException {
    public PricesCollectionNotFoundException(String message) {
        super(message);
    }
}