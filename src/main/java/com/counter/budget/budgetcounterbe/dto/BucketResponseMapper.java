package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.Bucket;
import org.springframework.stereotype.Service;

@Service
public class BucketResponseMapper {
    public BucketResponse toResponse (Bucket bucket) {
        return new BucketResponse(
                bucket.getId(),
                bucket.getName(),
                bucket.getDescription(),
                bucket.getPercentage(),
                bucket.getAmount()
        );
    }
}
