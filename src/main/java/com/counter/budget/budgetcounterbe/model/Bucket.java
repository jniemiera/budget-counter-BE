package com.counter.budget.budgetcounterbe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Buckets")
@NoArgsConstructor
@Getter
@Setter
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bucket_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int percentage;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column
    private String description;

    @OneToMany(mappedBy = "bucket")
    private List<BucketTransaction> bucketTransactions = new ArrayList<>();

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false, updatable = false)
    private boolean isDefault;

    public Bucket(String name, int percentage, String description){
        this.name = name;
        this.percentage = percentage;
        this.description = description;
        this.amount = BigDecimal.ZERO;
    }

    public BucketTransaction addBucketTransaction(Transaction transaction, BigDecimal amount, TransactionType type){
        BucketTransaction bt = new BucketTransaction(this, transaction, amount, type);
        this.bucketTransactions.add(bt);
        return bt;
    }
}
