package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.BucketTransactionMapper;
import com.counter.budget.budgetcounterbe.dto.BucketTransactionResponse;
import com.counter.budget.budgetcounterbe.exception.bucketTransaction.BucketTransactionNotFoundException;
import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.repository.BucketTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BucketTransactionService {
    private final BucketTransactionRepository bucketTransactionRepository;

    private final BucketTransactionMapper bucketTransactionMapper;

    public BucketTransactionService(
            BucketTransactionRepository bucketTransactionRepository,
            BucketTransactionMapper bucketTransactionMapper) {
        this.bucketTransactionRepository = bucketTransactionRepository;
        this.bucketTransactionMapper = bucketTransactionMapper;
    }

    public BucketTransactionResponse getBTById (UUID id) {
        BucketTransaction bt = bucketTransactionRepository.findById(id).orElseThrow(() -> new BucketTransactionNotFoundException(id));
        return bucketTransactionMapper.toResponse(bt);
    }

    public List<BucketTransactionResponse> getBTByBucket (UUID bucketId) {
        return bucketTransactionRepository.findAllByBucketId(bucketId).stream().map(bucketTransactionMapper::toResponse).toList();
    }

    public List<BucketTransactionResponse> getBTByTransaction (UUID transactionId) {
        return bucketTransactionRepository.findAllByTransactionId(transactionId).stream().map(bucketTransactionMapper::toResponse).toList();
    }

    public void deleteBucketTransaction(UUID id) {
        bucketTransactionRepository.deleteById(id);
    }
}
