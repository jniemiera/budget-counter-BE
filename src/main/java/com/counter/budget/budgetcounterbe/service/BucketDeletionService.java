package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.CreateTransactionRequest;
import com.counter.budget.budgetcounterbe.exception.bucket.CannotDeleteDefaultBucketException;
import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.counter.budget.budgetcounterbe.repository.BucketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BucketDeletionService {
    private final BucketService bucketService;

    private final BucketRepository bucketRepository;

    private final TransactionService transactionService;

    @Autowired
    public BucketDeletionService(BucketService bucketService,
                                 BucketRepository bucketRepository,
                                 TransactionService transactionService) {
        this.bucketService = bucketService;
        this.bucketRepository = bucketRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public void deleteBucket(UUID id) {
        Bucket bucket = bucketService.getBucketById(id);
        Bucket defaultBucket = bucketService.getDefaultBucket();
        if (bucket == defaultBucket) throw new CannotDeleteDefaultBucketException();

        transactionService.createTransaction(new CreateTransactionRequest(
                bucket.getAmount(),
                TransactionType.TRANSFER_ADDFUNDS,
                defaultBucket.getId()
        ));
        transactionService.createTransaction(new CreateTransactionRequest(
                bucket.getAmount(),
                TransactionType.TRANSFER_REMOVEFUNDS,
                bucket.getId()
        ));

        bucket.setDeleted(true);
        bucketRepository.save(bucket);
    }
}
