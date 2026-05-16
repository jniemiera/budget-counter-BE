package com.counter.budget.budgetcounterbe.exception.transaction;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(Exception e) {
        super(e.getMessage());
    }
}
