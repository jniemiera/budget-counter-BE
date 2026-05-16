ALTER TABLE Buckets ADD description varchar(255);

ALTER TABLE Buckets RENAME COLUMN value to amount;