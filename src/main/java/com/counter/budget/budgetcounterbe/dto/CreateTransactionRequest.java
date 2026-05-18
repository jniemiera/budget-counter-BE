package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull float amount,
        @NotNull TransactionType type,
        UUID bucketId
) {}
