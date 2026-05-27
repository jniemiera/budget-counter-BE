package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.model.Transaction;
import com.counter.budget.budgetcounterbe.model.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionResponseMapper {
    public TransactionResponse toResponse(Transaction transaction) {
        List<UUID> bucketTransactions = transaction.getBucketTransactions().stream().map(BucketTransaction::getId).toList();
        BigDecimal amount = transaction.getBucketTransactions().stream().map(BucketTransaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        TransactionType type = transaction.getBucketTransactions().getFirst().getType();
        if(type.equals(TransactionType.TRANSFER_REMOVEFUNDS) || type.equals(TransactionType.TRANSFER_ADDFUNDS)) {
            type = TransactionType.TRANSFER;
            amount = transaction.getBucketTransactions().getFirst().getAmount();
        }

        return new TransactionResponse(
                transaction.getId(),
                transaction.getTimestamp().toString(),
                bucketTransactions,
                type,
                amount);
    }
}
