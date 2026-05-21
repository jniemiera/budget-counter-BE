package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        String timestamp,
        List<UUID> bucketTransactions,
        TransactionType type,
        BigDecimal amount
) {}
