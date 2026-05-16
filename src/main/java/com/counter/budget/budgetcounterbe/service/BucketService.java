package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.SaveBucketRequest;
import com.counter.budget.budgetcounterbe.exception.bucket.BucketNotFoundException;
import com.counter.budget.budgetcounterbe.exception.bucket.NotEnoughFundsException;
import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.repository.BucketRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BucketService {
    private final BucketRepository bucketRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    public Bucket getBucketById(UUID id) {
        return this.bucketRepository.findById(id).orElseThrow(() -> new BucketNotFoundException(id));
    }

    public List<Bucket> getBuckets() {
        return this.bucketRepository.findAll();
    }

    public void createBucket(@NonNull SaveBucketRequest request) {
        this.bucketRepository.save(new Bucket(request.name(), request.percentage(), request.description()));
    }

    @Transactional
    public void updateBucket(@NonNull SaveBucketRequest request, UUID id) {
        Bucket bucket = getBucketById(id);
        bucket.setName(request.name());
        bucket.setPercentage(request.percentage());
        bucket.setDescription(request.description());
        this.bucketRepository.save(bucket);
    }

    public void deleteBucket(UUID id) {
        this.bucketRepository.deleteById(id);
    }


    public void processTransaction(BucketTransaction bt) {
        switch (bt.getType()) {
            case ADDFUNDS -> addFunds(bt.getAmount(), bt.getBucket());
            case REMOVEFUNDS -> removeFunds(bt.getAmount(), bt.getBucket());
        }
    }

    private void addFunds(float amountToAdd, Bucket bucket) {
        float currentAmount = bucket.getAmount();
        bucket.setAmount(currentAmount + amountToAdd);
        this.bucketRepository.save(bucket);
    }

    private void removeFunds(float amountToSubtract, Bucket bucket){
        float currentAmount = bucket.getAmount();

        if(amountToSubtract>currentAmount) throw new NotEnoughFundsException(bucket.getId(), amountToSubtract, currentAmount);

        bucket.setAmount(currentAmount - amountToSubtract);
        this.bucketRepository.save(bucket);
    }
}
