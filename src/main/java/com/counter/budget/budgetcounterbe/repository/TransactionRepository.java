package com.counter.budget.budgetcounterbe.repository;

import com.counter.budget.budgetcounterbe.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.bucketTransactions WHERE t.id = :id")
    Optional<Transaction> findByIdWithBucketTransactions(UUID id);

    @Query("SELECT t FROM Transaction t LEFT JOIN FETCH t.bucketTransactions")
    List<Transaction> findAllWithBucketTransactions();
}
