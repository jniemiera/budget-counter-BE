package com.counter.budget.budgetcounterbe.exception.bucket;

public class DefaultBucketNotFound extends RuntimeException {
    public DefaultBucketNotFound() {
        super("DefaultBucketNotFound: The default bucket for this account was not found");
    }
}
