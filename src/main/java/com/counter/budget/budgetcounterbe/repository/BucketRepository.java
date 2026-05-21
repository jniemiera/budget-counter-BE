package com.counter.budget.budgetcounterbe.repository;

import com.counter.budget.budgetcounterbe.model.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, UUID> {
    Optional<Bucket> findByIdAndDeletedFalse(UUID id);

    List<Bucket> findAllByDeletedFalse();

    Optional<Bucket> findByIsDefaultTrue();
}
