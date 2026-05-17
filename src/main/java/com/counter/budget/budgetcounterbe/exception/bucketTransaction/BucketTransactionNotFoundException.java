package com.counter.budget.budgetcounterbe.exception.bucketTransaction;

import java.util.UUID;

public class BucketTransactionNotFoundException extends RuntimeException {
    public BucketTransactionNotFoundException(UUID id) {
        super("BucketTransactionNotFoundException: Couldn't find bucket transaction of id %s".formatted(id));
    }
}
