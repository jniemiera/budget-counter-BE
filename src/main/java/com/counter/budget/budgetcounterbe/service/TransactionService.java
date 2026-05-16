package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.CreateTransactionRequest;
import com.counter.budget.budgetcounterbe.exception.transaction.TransactionBucketNotSpecifiedException;
import com.counter.budget.budgetcounterbe.exception.transaction.TransactionFailedException;
import com.counter.budget.budgetcounterbe.exception.transaction.TransactionNotFoundException;
import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.model.Transaction;
import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.counter.budget.budgetcounterbe.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final BucketService bucketService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, BucketService bucketService) {
        this.transactionRepository = transactionRepository;
        this.bucketService = bucketService;
    }

    public Transaction getTransactionById(UUID id) {
        return this.transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public List<Transaction> getTransactions() {
        return this.transactionRepository.findAll();
    }

    @Transactional
    public void createTransaction(CreateTransactionRequest request) {
        Transaction transaction = transactionRepository.save(new Transaction());
        switch (request.type()){
            case REMOVEFUNDS -> {
                UUID bucketId = request.bucketId().orElseThrow(TransactionBucketNotSpecifiedException::new);
                Bucket bucket = bucketService.getBucketById(bucketId);
                BucketTransaction bt = bucket.addBucketTransaction(transaction, request.amount(), request.type());
                transaction.addBucketTransaction(bt);
                bucketService.processTransaction(bt);
            }
            case ADDFUNDS -> {
                List<Bucket> buckets = bucketService.getBuckets();
                float splitAmountSum = 0;
                for(Bucket bucket: buckets) {
                    float amountForBucket = request.amount()*bucket.getPercentage()/100;
                    splitAmountSum += amountForBucket;
                    BucketTransaction bt = bucket.addBucketTransaction(transaction, amountForBucket, request.type());
                    transaction.addBucketTransaction(bt);
                }

                //Check if money was split between buckets correctly (protection from division errors) and fix difference if it exists
                // amountDifference will be positive, if there was not enough money put into buckets. Negative, if we put too much into buckets
                float amountDifference = request.amount() - splitAmountSum;
                if (amountDifference != 0) {
                    BucketTransaction btToEdit = transaction.getBucketTransactions().getFirst();
                    btToEdit.setAmount(btToEdit.getAmount() + amountDifference);
                    transaction.patchBucketTransaction(btToEdit);
                }

                for(BucketTransaction bt: transaction.getBucketTransactions()) {
                    bucketService.processTransaction(bt);
                }
            }
        }
        transactionRepository.save(transaction);
    }
}
