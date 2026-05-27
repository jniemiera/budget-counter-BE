package com.counter.budget.budgetcounterbe.dto;

import jakarta.validation.constraints.*;

public record SaveBucketRequest(
        @NotBlank String name,
        @NotNull  @Min(1) @Max(100) Integer percentage,
        @Size(min = 0, max = 255) String description
) {}
