ALTER TABLE Buckets ADD deleted BOOLEAN NOT NULL default false;

ALTER TABLE Transactions ADD deleted BOOLEAN NOT NULL default false;

ALTER TABLE bucket_transactions ADD deleted BOOLEAN NOT NULL default false;