DROP DATABASE IF EXISTS tax_preparer_DB;

CREATE DATABASE tax_preparer_DB;
USE tax_preparer_DB;

CREATE TABLE user (
    email VARCHAR(100) PRIMARY KEY,
    f_name VARCHAR(25) NOT NULL,
    m_name VARCHAR(25),
    l_name VARCHAR(25) NOT NULL,
    permission_level INT NOT NULL,
    phone CHAR(15),
    pass_hash VARCHAR(64) NOT NULL,
    pass_salt CHAR(44) NOT NULL,
    title VARCHAR(5) NOT NULL,
    creation_date DATETIME NOT NULL,
    fax CHAR(15),
    postal_code CHAR(10),
    city VARCHAR(100),
    country CHAR(2),
    province VARCHAR(20),
    street_address_1 VARCHAR(320),
    street_address_2 VARCHAR(320),
    language CHAR(2) NOT NULL,
    active CHAR(1) NOT NULL,
    verified CHAR(1) NOT NULL,
    verification_id CHAR(36),
    last_verification_attempt DATETIME,
    last_verification_type INT);
    
ALTER TABLE user 
ADD CONSTRAINT CHK_user_permission_level CHECK (permission_level BETWEEN 0 AND 3);

ALTER TABLE user
ADD CONSTRAINT CHK_user_active CHECK (active = 'T' OR active = 'F');

ALTER TABLE user
ADD CONSTRAINT CHK_user_title CHECK (title IN ('NA', 'Mr', 'Mrs', 'Ms', 'Mx'));

ALTER TABLE user
ADD CONSTRAINT CHK_user_language CHECK (language IN ('en', 'es'));

COMMIT;

CREATE TABLE tax_return (
    email VARCHAR(100) NOT NULL,
    household_id INT UNSIGNED,
    status VARCHAR(20) NOT NULL,
    cost DOUBLE(10,2),
    year YEAR NOT NULL,
	PRIMARY KEY (email, year),
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE parcel (
    parcel_id CHAR(36) PRIMARY KEY,
    subject VARCHAR(100),
    message VARCHAR(10000),
    sender VARCHAR(100) NOT NULL,
    receiver VARCHAR(100),
    date_sent DATETIME NOT NULL,
    expiration_date DATETIME,
	tax_return_year YEAR NOT NULL,
	requires_signature CHAR(1) NOT NULL,
    FOREIGN KEY (sender) REFERENCES user(email),
    FOREIGN KEY (receiver) REFERENCES user(email),
	FOREIGN KEY (receiver, tax_return_year) REFERENCES tax_return(email, year));
	
ALTER TABLE parcel
ADD CONSTRAINT CHK_parcel_requires_signature CHECK (requires_signature = 'T' OR requires_signature = 'F');
	
COMMIT;
	
CREATE TABLE document (
    file_path VARCHAR(420) PRIMARY KEY,
    parcel_id CHAR(36) NOT NULL,
    file_name VARCHAR(69) NOT NULL,
    file_size LONG NOT NULL,
    FOREIGN KEY (parcel_id) REFERENCES parcel(parcel_id));
    
COMMIT;

CREATE TABLE log (
    log_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100),
    type CHAR(1) NOT NULL,
    message VARCHAR(2000),
    date DATETIME NOT NULL);
    
ALTER TABLE log 
ADD CONSTRAINT CHK_log_type CHECK (type IN ('L', 'E', 'D', 'U', 'B', 'R'));

COMMIT;

CREATE TABLE session (
    session_id CHAR(32) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    timeout DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE preparer_tax_return (
    tax_return_email VARCHAR(100) NOT NULL,
	tax_return_year YEAR NOT NULL,
    tax_preparer_email VARCHAR(100),
    PRIMARY KEY (tax_return_email, tax_return_year, tax_preparer_email),
    FOREIGN KEY (tax_preparer_email, tax_return_year) REFERENCES tax_return(email, year),
    FOREIGN KEY (tax_preparer_email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE payment (
    payment_id CHAR(36) PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
	year YEAR NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    amount DOUBLE(10,2) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (email, year) REFERENCES tax_return(email, year));
	
COMMIT;

CREATE EVENT terminate_sessions
	ON SCHEDULE
	EVERY 5 MINUTE
	DO
		DELETE FROM session WHERE timeout < NOW();
		
COMMIT;

CREATE EVENT delete_expired_parcels
	ON SCHEDULE
	EVERY 1 DAY
	STARTS (CURDATE())
	DO
		DELETE FROM parcel WHERE expiration_date < CURDATE();
		
COMMIT;

SET GLOBAL event_scheduler = ON;
