package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.BucketResponse;
import com.counter.budget.budgetcounterbe.dto.BucketResponseMapper;
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

    private final BucketResponseMapper bucketResponseMapper;

    @Autowired
    public BucketService(
            BucketRepository bucketRepository,
            BucketResponseMapper bucketResponseMapper) {
        this.bucketRepository = bucketRepository;
        this.bucketResponseMapper = bucketResponseMapper;
    }

    public Bucket getBucketById(UUID id) {
        return bucketRepository.findById(id).orElseThrow(() -> new BucketNotFoundException(id));
    }

    public BucketResponse getBucketResponseById(UUID id) {
        return bucketResponseMapper.toResponse(getBucketById(id));
    }

    public List<Bucket> getBuckets() {
        return bucketRepository.findAll();
    }

    public List<BucketResponse> getBucketsResponse() {
        return getBuckets().stream().map(bucketResponseMapper::toResponse).toList();
    }

    public void createBucket(@NonNull SaveBucketRequest request) {
        bucketRepository.save(new Bucket(request.name(), request.percentage(), request.description()));
    }

    @Transactional
    public void updateBucket(@NonNull SaveBucketRequest request, UUID id) {
        Bucket bucket = getBucketById(id);
        bucket.setName(request.name());
        bucket.setPercentage(request.percentage());
        bucket.setDescription(request.description());
        bucketRepository.save(bucket);
    }

    public void deleteBucket(UUID id) {
        bucketRepository.deleteById(id);
    }


    public void processTransaction(@NonNull BucketTransaction bt) {
        switch (bt.getType()) {
            case ADDFUNDS -> addFunds(bt.getAmount(), bt.getBucket());
            case REMOVEFUNDS -> removeFunds(bt.getAmount(), bt.getBucket());
        }
    }

    private void addFunds(float amountToAdd, @NonNull Bucket bucket) {
        float currentAmount = bucket.getAmount();
        bucket.setAmount(currentAmount + amountToAdd);
        bucketRepository.save(bucket);
    }

    private void removeFunds(float amountToSubtract, @NonNull Bucket bucket){
        float currentAmount = bucket.getAmount();

        if(amountToSubtract>currentAmount) throw new NotEnoughFundsException(bucket.getId(), amountToSubtract, currentAmount);

        bucket.setAmount(currentAmount - amountToSubtract);
        bucketRepository.save(bucket);
    }
}
