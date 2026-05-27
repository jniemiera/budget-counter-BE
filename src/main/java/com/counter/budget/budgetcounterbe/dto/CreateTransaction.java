package com.counter.budget.budgetcounterbe.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddFundsRequest.class, name = "add"),
        @JsonSubTypes.Type(value = RemoveFundsRequest.class, name = "remove"),
        @JsonSubTypes.Type(value = TransferFundsRequest.class, name = "transfer")
})
public interface CreateTransaction {}
