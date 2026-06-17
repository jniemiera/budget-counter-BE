package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.TransferFundsRequest;
import com.counter.budget.budgetcounterbe.exception.bucket.CannotDeleteDefaultBucketException;
import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.Transaction;
import com.counter.budget.budgetcounterbe.repository.BucketRepository;
import com.counter.budget.budgetcounterbe.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BucketDeletionService {
    private final BucketService bucketService;

    private final BucketRepository bucketRepository;

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    @Autowired
    public BucketDeletionService(BucketService bucketService,
                                 BucketRepository bucketRepository,
                                 TransactionService transactionService,
                                 TransactionRepository transactionRepository) {
        this.bucketService = bucketService;
        this.bucketRepository = bucketRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void deleteBucket(UUID id) {
        Bucket bucket = bucketService.getBucketById(id);
        Bucket defaultBucket = bucketService.getDefaultBucket();
        if (bucket == defaultBucket) throw new CannotDeleteDefaultBucketException();

        Transaction transaction = transactionRepository.save(new Transaction());
        transactionService.transferFunds(new TransferFundsRequest(
                bucket.getId(),
                defaultBucket.getId(),
                bucket.getAmount(),
                "Transferring funds to default bucket in order to delete bucket %s".formatted(bucket.getName())
        ), transaction);
        transactionRepository.save(transaction);

        bucket.setDeleted(true);
        bucketRepository.save(bucket);
    }
}
