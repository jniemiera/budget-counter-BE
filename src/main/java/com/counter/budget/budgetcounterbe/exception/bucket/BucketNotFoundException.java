package com.counter.budget.budgetcounterbe.exception.bucket;

import java.util.UUID;

public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(UUID id) {
        super("BucketNotFoundException: Couldn't find bucket of id %s".formatted(id));
    }
}
