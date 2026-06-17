package com.counter.budget.budgetcounterbe.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@JsonTypeName("transfer")
public record TransferFundsRequest(
        @NotNull UUID sourceBucketId,
        @NotNull UUID targetBucketId,
        @NotNull BigDecimal amount,
        @Nullable String description

) implements CreateTransaction {}