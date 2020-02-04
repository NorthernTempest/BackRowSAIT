CREATE TABLE document (
    file_path VARCHAR(30) PRIMARY KEY,
    requires_signature CHAR(1) NOT NULL,
    is_signed CHAR(1) NOT NULL,
    is_stored CHAR(1) NOT NULL,
    parcel_id INT NOT NULL,
    FOREIGN KEY (parcel_id) REFERENCES parcel(parcel_id));
    
ALTER TABLE document
ADD CONSTRAINT CHK_document_requires_signature CHECK (requires_signature = 'T' OR requires_signature = 'F');

ALTER TABLE document
ADD CONSTRAINT CHK_document_is_signed CHECK (is_signed = 'T' OR is_signed = 'F');

ALTER TABLE document
ADD CONSTRAINT CHK_document_is_stored CHECK (is_stored = 'T' OR is_stored = 'F');

COMMIT;