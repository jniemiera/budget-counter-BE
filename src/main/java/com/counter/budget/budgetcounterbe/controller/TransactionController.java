package com.counter.budget.budgetcounterbe.controller;

import com.counter.budget.budgetcounterbe.dto.CreateTransaction;
import com.counter.budget.budgetcounterbe.dto.TransactionResponse;
import com.counter.budget.budgetcounterbe.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(){
        List<TransactionResponse> response = transactionService.getTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID transactionId) {
        TransactionResponse response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid CreateTransaction request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
