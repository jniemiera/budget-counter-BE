package com.counter.budget.budgetcounterbe.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BucketResponse(
        UUID id,
        String name,
        String description,
        int percentage,
        BigDecimal amount
) {}
