package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import org.springframework.stereotype.Component;

@Component
public class BucketTransactionMapper {
    public BucketTransactionResponse toResponse(BucketTransaction bt) {
        return new BucketTransactionResponse(
                bt.getId(),
                bt.getAmount(),
                bt.getBucket().getId(),
                bt.getTransaction().getId(),
                bt.getType()
        );
    }
}
