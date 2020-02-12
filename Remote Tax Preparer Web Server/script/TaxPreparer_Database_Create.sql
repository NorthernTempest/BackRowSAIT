DROP DATABASE IF EXISTS tax_preparer_DB;

CREATE DATABASE tax_preparer_DB;
USE tax_preparer_DB;

CREATE TABLE user (
    email VARCHAR(320) PRIMARY KEY,
    f_name VARCHAR(30) NOT NULL,
    l_name VARCHAR(30) NOT NULL,
    permission_level INT NOT NULL,
    phone CHAR(10),
    pass_hash CHAR(32) NOT NULL,
    pass_salt CHAR(32) NOT NULL,
    title VARCHAR(5),
    creation_date DATE NOT NULL,
    fax CHAR(10),
    postal_code CHAR (6),
    city VARCHAR (32),
    country VARCHAR (32),
    street_address_1 VARCHAR (320),
    street_address_2 VARCHAR (320),
    active CHAR(1) NOT NULL);
    
ALTER TABLE user 
ADD CONSTRAINT CHK_user_permission_level CHECK (permission_level BETWEEN 0 AND 3);

ALTER TABLE user
ADD CONSTRAINT CHK_user_active CHECK (active = 'T' OR active = 'F');

COMMIT;

CREATE TABLE parcel (
    parcel_id INT PRIMARY KEY,
    subject VARCHAR(100),
    message VARCHAR(10000),
    sender VARCHAR(320) NOT NULL,
    receiver VARCHAR(320),
    date_sent DATE NOT NULL,
    expiration_date DATE,
    FOREIGN KEY (sender) REFERENCES user(email),
    FOREIGN KEY (receiver) REFERENCES user(email));
	
COMMIT;
	
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

CREATE TABLE log (
    log_id INT PRIMARY KEY,
    email VARCHAR(320),
    type CHAR(1) NOT NULL,
    message VARCHAR(200),
    date DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));
    
ALTER TABLE log 
ADD CONSTRAINT CHK_log_type CHECK (type IN ('L', 'E', 'D', 'U'));

COMMIT;

CREATE TABLE session (
    session_id CHAR(32) PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    timeout DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE household (
    household_id INT PRIMARY KEY,
    household_name VARCHAR(30) NOT NULL);
	
COMMIT;

CREATE TABLE tax_return (
    return_id INT PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    household_id INT,
    status VARCHAR(20) NOT NULL,
    cost DOUBLE(10,2),
    year INT(4) NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE preparer_tax_return (
    return_id INT,
    email VARCHAR(320),
    PRIMARY KEY (return_id, email),
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id),
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE payment (
    payment_id INT PRIMARY KEY,
    return_id INT NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    amount DOUBLE(10,2) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id));
	
COMMIT;

/* The following section is for testing purposes only!
 * TODO: Remove before deployment.
 */

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active)
VALUES ("test@test.com", "Timmy", "Turner", 1, "70617373776f7264", "word", CURDATE(), "y");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active)
VALUES ("example@test.com", "Roger", "Rabbit", 1, "70617373776f7264", "word", CURDATE(), "y");