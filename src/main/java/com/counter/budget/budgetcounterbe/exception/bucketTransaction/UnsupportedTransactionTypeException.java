package com.counter.budget.budgetcounterbe.exception.bucketTransaction;

import java.util.UUID;

public class UnsupportedTransactionTypeException extends RuntimeException {
    public UnsupportedTransactionTypeException(UUID bucketId) {
        super("UnsupportedTransactionTypeException: tried to process a transaction on bucket of id %s but its type was not specified".formatted(bucketId));
    }
}
