package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record BucketTransactionResponse(
        UUID id,
        BigDecimal amount,
        UUID bucket_id,
        UUID transaction_id,
        TransactionType type
) {}
