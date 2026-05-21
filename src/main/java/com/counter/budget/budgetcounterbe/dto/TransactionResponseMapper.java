package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionResponseMapper {
    public TransactionResponse toResponse(Transaction transaction) {
        List<UUID> bucketTransactions = transaction.getBucketTransactions().stream().map(BucketTransaction::getId).toList();
        BigDecimal amount = transaction.getBucketTransactions().stream().map(BucketTransaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TransactionResponse(
                transaction.getId(),
                transaction.getTimestamp().toString(),
                bucketTransactions,
                transaction.getBucketTransactions().getFirst().getType(),
                amount);
    }
}
