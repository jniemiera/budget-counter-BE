package com.counter.budget.budgetcounterbe.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferMoneyRequest(
        @NotNull UUID sourceBucketId,
        @NotNull UUID targetBucketId,
        @NotNull BigDecimal amount

) {}