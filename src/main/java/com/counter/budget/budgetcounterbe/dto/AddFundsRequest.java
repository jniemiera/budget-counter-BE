package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonTypeName("add")
public record AddFundsRequest(
        @NotNull BigDecimal amount,
        @NotNull TransactionType type,
        @Nullable String description
) implements CreateTransaction {}
