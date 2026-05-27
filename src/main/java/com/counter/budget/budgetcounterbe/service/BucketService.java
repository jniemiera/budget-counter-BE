package com.counter.budget.budgetcounterbe.service;

import com.counter.budget.budgetcounterbe.dto.*;
import com.counter.budget.budgetcounterbe.exception.bucket.BucketNotFoundException;
import com.counter.budget.budgetcounterbe.exception.bucket.DefaultBucketNotFound;
import com.counter.budget.budgetcounterbe.exception.bucket.NotEnoughFundsException;
import com.counter.budget.budgetcounterbe.model.Bucket;
import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import com.counter.budget.budgetcounterbe.repository.BucketRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        return bucketRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new BucketNotFoundException(id));
    }

    public BucketResponse getBucketResponseById(UUID id) {
        return bucketResponseMapper.toResponse(getBucketById(id));
    }

    public List<Bucket> getBuckets() {
        return bucketRepository.findAllByDeletedFalse();
    }

    public List<BucketResponse> getBucketsResponse() {
        return getBuckets().stream().map(bucketResponseMapper::toResponse).toList();
    }

    public Bucket getDefaultBucket() {
        return bucketRepository.findByIsDefaultTrue().orElseThrow(DefaultBucketNotFound::new);
    }

    public BucketResponse createBucket(@NonNull SaveBucketRequest request) {
        Bucket bucket = bucketRepository.save(new Bucket(request));
        return bucketResponseMapper.toResponse(bucket);
    }

    @Transactional
    public BucketResponse patchBucket(@NonNull PatchBucketRequest request, UUID id) {
        Bucket bucket = getBucketById(id);
        if(request.name() != null) bucket.setName(request.name());
        if(request.percentage() != null) bucket.setPercentage(request.percentage());
        if(request.description() != null) bucket.setDescription(request.description());
        bucketRepository.save(bucket);
        return bucketResponseMapper.toResponse(bucket);
    }

    public void processTransaction(@NonNull BucketTransaction bt) {
        switch (bt.getType()) {
            case ADDFUNDS, TRANSFER_ADDFUNDS -> addFunds(bt.getAmount(), bt.getBucket());
            case REMOVEFUNDS, TRANSFER_REMOVEFUNDS -> removeFunds(bt.getAmount(), bt.getBucket());
            case UNDO_ADDFUNDS -> addFunds(bt.getAmount().negate(), bt.getBucket());
            case UNDO_REMOVEFUNDS -> removeFunds(bt.getAmount().negate(), bt.getBucket());
        }
    }

    private void addFunds(BigDecimal amountToAdd, @NonNull Bucket bucket) {
        BigDecimal currentAmount = bucket.getAmount();
        bucket.setAmount(currentAmount.add(amountToAdd));
        bucketRepository.save(bucket);
    }

    private void removeFunds(BigDecimal amountToSubtract, @NonNull Bucket bucket){
        BigDecimal currentAmount = bucket.getAmount();

        if(amountToSubtract.compareTo(currentAmount)>0) throw new NotEnoughFundsException(bucket.getId(), amountToSubtract, currentAmount);

        bucket.setAmount(currentAmount.subtract(amountToSubtract));
        bucketRepository.save(bucket);
    }

    public int sumBucketPercentages(List<Bucket> buckets) {
        return buckets.stream().map(Bucket::getPercentage).reduce(0, Integer::sum);
    }
}
