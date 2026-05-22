package com.counter.budget.budgetcounterbe.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PatchBucketRequest(
        String name,
        @Min(0) @Max(100) Integer percentage,
        @Size(min = 0, max = 255) String description
) {}
