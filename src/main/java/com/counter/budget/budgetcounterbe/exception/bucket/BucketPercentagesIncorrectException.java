package com.counter.budget.budgetcounterbe.exception.bucket;

public class BucketPercentagesIncorrectException extends RuntimeException {
    public BucketPercentagesIncorrectException(int percentage) {
        super("BucketPercentagesIncorrectException: The sum of percentages in all buckets is %s, where it should be 100".formatted(percentage));
    }
}
