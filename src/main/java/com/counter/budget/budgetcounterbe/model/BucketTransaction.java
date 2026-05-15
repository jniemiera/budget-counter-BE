package com.counter.budget.budgetcounterbe.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="BucketTransactions")
@NoArgsConstructor
public class BucketTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @Column(nullable = false)
    private float value;
}
