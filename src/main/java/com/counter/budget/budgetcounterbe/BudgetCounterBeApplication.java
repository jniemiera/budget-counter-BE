package com.counter.budget.budgetcounterbe;

import com.counter.budget.budgetcounterbe.dto.CreateTransactionRequest;
import com.counter.budget.budgetcounterbe.dto.SaveBucketRequest;
import com.counter.budget.budgetcounterbe.model.Transaction;
import com.counter.budget.budgetcounterbe.model.TransactionType;
import com.counter.budget.budgetcounterbe.service.BucketService;
import com.counter.budget.budgetcounterbe.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class BudgetCounterBeApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BudgetCounterBeApplication.class, args);
//        BucketService bucketService = context.getBean(BucketService.class);
//        bucketService.createBucket(new SaveBucketRequest("Important", 40, "For buying important things, like food, petrol, and other"));
//        bucketService.createBucket(new SaveBucketRequest("For fun", 20, "For personal pleasure and things that aren't absolutely necessary"));
//        TransactionService transactionService = context.getBean(TransactionService.class);
//        transactionService.createTransaction(new CreateTransactionRequest(13.0F, TransactionType.REMOVEFUNDS, Optional.of(UUID.fromString("4db86085-703d-420b-8a89-7cae76813e9d"))));
    }

}
