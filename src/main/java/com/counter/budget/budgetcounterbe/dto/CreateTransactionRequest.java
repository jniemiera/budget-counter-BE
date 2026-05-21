package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull BigDecimal amount,
        @NotNull TransactionType type,
        UUID bucketId
) {}
