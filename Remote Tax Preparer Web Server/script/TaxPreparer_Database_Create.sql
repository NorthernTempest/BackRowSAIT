DROP DATABASE IF EXISTS tax_preparer_DB;

CREATE DATABASE tax_preparer_DB;
USE tax_preparer_DB;

SOURCE user_table_create.sql;
SOURCE parcel_table_create.sql;
SOURCE document_table_create.sql;
SOURCE log_table_create.sql;
SOURCE session_table_create.sql;
SOURCE household_table_create.sql;
SOURCE tax_return_table_create.sql;
SOURCE preparer_tax_return_table_create.sql;
SOURCE payment_table_create.sql;