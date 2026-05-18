package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.CreateTransactionRequest;
import com.counter.budget.budgetcounterbe.dto.TransactionResponse;
import com.counter.budget.budgetcounterbe.dto.TransactionResponseMapper;
import com.counter.budget.budgetcounterbe.exception.bucket.BucketPercentagesIncorrectException;
import com.counter.budget.budgetcounterbe.exception.transaction.TransactionBucketNotSpecifiedException;
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
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final BucketService bucketService;

    private final TransactionResponseMapper transactionResponseMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              BucketService bucketService,
                              TransactionResponseMapper transactionResponseMapper) {
        this.transactionRepository = transactionRepository;
        this.bucketService = bucketService;
        this.transactionResponseMapper = transactionResponseMapper;
    }

    public Transaction getTransaction(UUID id) {
        return transactionRepository.findByIdWithBucketTransactions(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public TransactionResponse getTransactionById(UUID id) {
        Transaction transaction = getTransaction(id);
        return transactionResponseMapper.toResponse(transaction);
    }

    public List<TransactionResponse> getTransactions() {
        List<Transaction> transactions =  transactionRepository.findAllWithBucketTransactions();
        return transactions.stream().map((transactionResponseMapper::toResponse)).toList();
    }

    @Transactional
    public void createTransaction(CreateTransactionRequest request) {
        validateBucketPercentages();

        Transaction transaction = transactionRepository.save(new Transaction());
        switch (request.type()){
            case REMOVEFUNDS, UNDO_REMOVEFUNDS -> {
                if (request.bucketId() == null) throw new TransactionBucketNotSpecifiedException();
                UUID bucketId = request.bucketId();
                Bucket bucket = bucketService.getBucketById(bucketId);
                BucketTransaction bt = bucket.addBucketTransaction(transaction, request.amount(), request.type());
                transaction.addBucketTransaction(bt);
                bucketService.processTransaction(bt);
            }
            case ADDFUNDS, UNDO_ADDFUNDS -> {
                List<Bucket> buckets = bucketService.getBuckets();
                float splitAmountSum = 0;
                for(Bucket bucket: buckets) {
                    float amountForBucket = request.amount()*bucket.getPercentage()/100;
                    splitAmountSum += amountForBucket;
                    BucketTransaction bt = bucket.addBucketTransaction(transaction, amountForBucket, request.type());
                    transaction.addBucketTransaction(bt);
                }

                //Check if money was split between buckets correctly (protection from division errors) and fix the difference if there is any
                //amountDifference will be positive if there was not enough money put into buckets. Negative, if we put too much into buckets
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

    @Transactional
    public void deleteTransaction(UUID id) {
        Transaction transaction = getTransaction(id);
        TransactionType transactionType = transaction.getBucketTransactions().getFirst().getType();
        TransactionType undoType = TransactionType.UNDO_REMOVEFUNDS;
        UUID bucketId = null;
        if (transactionType.equals(TransactionType.ADDFUNDS)) {
            undoType = TransactionType.UNDO_ADDFUNDS;
        }
        if (transactionType.equals(TransactionType.REMOVEFUNDS)) {
            bucketId = transaction.getBucketTransactions().getFirst().getBucket().getId();
        }

        createTransaction(new CreateTransactionRequest(
                transaction.getBucketTransactions().stream().map(BucketTransaction::getAmount).reduce(0F, Float::sum),
                undoType,
                bucketId
        ));
      
    private void validateBucketPercentages() {
        int bucketPercentages = bucketService.sumBucketPercentages(bucketService.getBuckets());
        if (bucketPercentages != 100) {
            throw new BucketPercentagesIncorrectException(bucketPercentages);
        }
    }
}
