package com.counter.budget.budgetcounterbe.exception.bucket;

import java.util.UUID;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(UUID id, float amountToSubtract, float amountInBucket) {
        super("NotEnoughFundsException: Tried to remove %s from bucket of ID %s, but it only has %s in it".formatted(amountToSubtract, id, amountInBucket));
    }
}
