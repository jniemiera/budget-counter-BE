package com.counter.budget.budgetcounterbe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="BucketTransactions")
@NoArgsConstructor
@Getter
@Setter
public class BucketTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bucket_transaction_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    public BucketTransaction(Bucket bucket, Transaction transaction, BigDecimal amount, TransactionType type) {
        this.bucket = bucket;
        this.transaction = transaction;
        this.amount = amount;
        this.type = type;
    }
}
