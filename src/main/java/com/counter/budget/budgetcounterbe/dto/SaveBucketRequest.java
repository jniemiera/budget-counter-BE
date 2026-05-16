package com.counter.budget.budgetcounterbe.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveBucketRequest(
        @NotBlank String name,
        @Min(0) @Max(100) int percentage,
        @Size(min = 0, max = 255) String description
) {}
