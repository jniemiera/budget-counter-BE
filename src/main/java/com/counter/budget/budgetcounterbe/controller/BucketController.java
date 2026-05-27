package com.counter.budget.budgetcounterbe.controller;

import com.counter.budget.budgetcounterbe.dto.BucketResponse;
import com.counter.budget.budgetcounterbe.dto.BucketTransactionResponse;
import com.counter.budget.budgetcounterbe.dto.SaveBucketRequest;
import com.counter.budget.budgetcounterbe.dto.PatchBucketRequest;
import com.counter.budget.budgetcounterbe.service.BucketDeletionService;
import com.counter.budget.budgetcounterbe.service.BucketService;
import com.counter.budget.budgetcounterbe.service.BucketTransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("buckets")
public class BucketController {
    private final BucketService bucketService;

    private final BucketDeletionService bucketDeletionService;

    private final BucketTransactionService bucketTransactionService;

    public BucketController(
            BucketService bucketService,
            BucketDeletionService bucketDeletionService,
            BucketTransactionService bucketTransactionService) {
        this.bucketService = bucketService;
        this.bucketDeletionService = bucketDeletionService;
        this.bucketTransactionService = bucketTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<BucketResponse>> getBuckets() {
        List<BucketResponse> response = bucketService.getBucketsResponse();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "{bucketId}")
    public ResponseEntity<BucketResponse> getBucketById(@PathVariable UUID bucketId) {
        BucketResponse response = bucketService.getBucketResponseById(bucketId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "{bucketId}/transactions")
    public ResponseEntity<List<BucketTransactionResponse>> getTransactionsByBucket(@PathVariable UUID bucketId) {
        List<BucketTransactionResponse> response =  bucketTransactionService.getBTByBucket(bucketId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<BucketResponse> createBucket(@RequestBody @Valid SaveBucketRequest request) {
        BucketResponse response = bucketService.createBucket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping(value = "{bucketId}")
    public ResponseEntity<BucketResponse> patchBucket(@RequestBody @Valid PatchBucketRequest request, @PathVariable UUID bucketId) {
        BucketResponse response = bucketService.patchBucket(request, bucketId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "{bucketId}")
    public ResponseEntity<Void> deleteBucket(@PathVariable UUID bucketId) {
        bucketDeletionService.deleteBucket(bucketId);
        return ResponseEntity.noContent().build();
    }
}
