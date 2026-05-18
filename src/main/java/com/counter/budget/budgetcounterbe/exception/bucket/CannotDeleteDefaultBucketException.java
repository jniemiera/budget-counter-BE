package com.counter.budget.budgetcounterbe.exception.bucket;

public class CannotDeleteDefaultBucketException extends RuntimeException {
    public CannotDeleteDefaultBucketException() {
        super("CannotDeleteDefaultBucketException: An account cannot delete the default bucket assigned to it");
    }
}
