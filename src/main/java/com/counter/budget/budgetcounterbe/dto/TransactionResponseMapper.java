package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionResponseMapper {
    public TransactionResponse toResponse(Transaction transaction) {
        List<UUID> bucketTransactions = transaction.getBucketTransactions().stream().map(BucketTransaction::getId).toList();
        float amount = transaction.getBucketTransactions().stream().map(BucketTransaction::getAmount).reduce(0F, Float::sum);

        return new TransactionResponse(
                transaction.getId(),
                transaction.getTimestamp().toString(),
                bucketTransactions,
                transaction.getBucketTransactions().getFirst().getType(),
                amount);
    }
}
