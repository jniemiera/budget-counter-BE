package com.counter.budget.budgetcounterbe.dto;

import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@JsonTypeName("remove")
public record RemoveFundsRequest(
        @NotNull BigDecimal amount,
        @NotNull TransactionType type,
        @NotNull UUID bucketId
) implements CreateTransaction {}
