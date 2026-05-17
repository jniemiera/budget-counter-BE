package com.counter.budget.budgetcounterbe.repository;

import com.counter.budget.budgetcounterbe.model.BucketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BucketTransactionRepository extends JpaRepository<BucketTransaction, UUID> {
    List<BucketTransaction> findAllByBucketId(UUID bucketId);

    List<BucketTransaction> findAllByTransactionId(UUID transactionId);
}
