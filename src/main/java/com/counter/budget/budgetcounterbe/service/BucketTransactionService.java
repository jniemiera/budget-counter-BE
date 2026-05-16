package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.model.Transaction;
import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.counter.budget.budgetcounterbe.repository.BucketTransactionRepository;

import java.util.UUID;

public class BucketTransactionService {
    private final BucketTransactionRepository bucketTransactionRepository;
    private final BucketService bucketService;

    public BucketTransactionService(BucketTransactionRepository bucketTransactionRepository, BucketService bucketService) {
        this.bucketTransactionRepository = bucketTransactionRepository;
        this.bucketService = bucketService;
    }

    public BucketTransaction createBucketTransaction(UUID bucketId, Transaction transaction, float amount, TransactionType type) {
        Bucket bucket = this.bucketService.getBucketById(bucketId);
        BucketTransaction bt = bucket.addBucketTransaction(transaction, amount, type);
        this.bucketTransactionRepository.save(bt);
        this.bucketService.processTransaction(bt);
        return bt;
    }
}
