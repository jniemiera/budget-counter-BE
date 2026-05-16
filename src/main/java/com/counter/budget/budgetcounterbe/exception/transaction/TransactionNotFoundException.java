package com.counter.budget.budgetcounterbe.exception.transaction;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(UUID id) {
        super("TransactionNotFoundException: Couldn't find transaction of id %s".formatted(id));
    }
}
