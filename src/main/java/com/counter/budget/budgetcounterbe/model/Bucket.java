package com.counter.budget.budgetcounterbe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int percentage;

    @Column(nullable = false)
    private float value;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BucketTransaction> bucketTransactions;
}
