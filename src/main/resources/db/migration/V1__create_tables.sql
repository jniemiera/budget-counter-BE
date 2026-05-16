CREATE TABLE Buckets (
    bucket_id uuid PRIMARY KEY,
    name varchar(255) NOT NULL,
    percentage int NOT NULL,
    value float NOT NULL
);

CREATE TABLE Transactions (
    transaction_id uuid PRIMARY KEY,
    timestamp timestamp NOT NULL
);

CREATE TABLE BucketTransactions (
    bucket_transaction_id uuid PRIMARY KEY,
    value float NOT NULL,
    bucket_id uuid NOT NULL,
    transaction_id uuid NOT NULL,
    CONSTRAINT fk_bucket FOREIGN KEY (bucket_id) REFERENCES Buckets(bucket_id),
    CONSTRAINT fk_transaction FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id)
);