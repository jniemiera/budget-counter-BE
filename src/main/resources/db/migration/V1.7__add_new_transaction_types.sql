ALTER TABLE bucket_transactions DROP CONSTRAINT bucket_transactions_type_check;

ALTER TABLE bucket_transactions ADD CONSTRAINT check_type CHECK ( type IN ('ADDFUNDS', 'REMOVEFUNDS', 'UNDO_ADDFUNDS', 'UNDO_REMOVEFUNDS'));