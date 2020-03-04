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
    pass_hash VARCHAR(320) NOT NULL,
    pass_salt CHAR(44) NOT NULL,
    title VARCHAR(5),
    creation_date DATETIME NOT NULL,
    fax CHAR(15),
    postal_code CHAR(6),
    city VARCHAR(100),
    country VARCHAR(100),
    province VARCHAR(100),
    street_address_1 VARCHAR(320),
    street_address_2 VARCHAR(320),
    language CHAR(3) NOT NULL,
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
ADD CONSTRAINT CHK_user_language CHECK (language IN ('eng', 'spn', 'fre'));

COMMIT;

CREATE TABLE tax_return (
    email VARCHAR(320) NOT NULL,
    household_id INT UNSIGNED,
    status VARCHAR(20) NOT NULL,
    cost DOUBLE(10,2),
    year YEAR NOT NULL,
	PRIMARY KEY (email, year),
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE parcel (
    parcel_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(100),
    message VARCHAR(10000),
    sender VARCHAR(320) NOT NULL,
    receiver VARCHAR(320),
    date_sent DATETIME NOT NULL,
    expiration_date DATETIME,
	tax_return_year YEAR NOT NULL,
    FOREIGN KEY (sender) REFERENCES user(email),
    FOREIGN KEY (receiver) REFERENCES user(email),
	FOREIGN KEY (receiver, tax_return_year) REFERENCES tax_return(email, year));
	
COMMIT;
	
CREATE TABLE document (
    file_path VARCHAR(30) PRIMARY KEY,
    requires_signature CHAR(1) NOT NULL,
    is_signed CHAR(1) NOT NULL,
    is_stored CHAR(1) NOT NULL,
    parcel_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (parcel_id) REFERENCES parcel(parcel_id));
    
ALTER TABLE document
ADD CONSTRAINT CHK_document_requires_signature CHECK (requires_signature = 'T' OR requires_signature = 'F');

ALTER TABLE document
ADD CONSTRAINT CHK_document_is_signed CHECK (is_signed = 'T' OR is_signed = 'F');

ALTER TABLE document
ADD CONSTRAINT CHK_document_is_stored CHECK (is_stored = 'T' OR is_stored = 'F');

COMMIT;

CREATE TABLE log (
    log_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(320),
    type CHAR(1) NOT NULL,
    message VARCHAR(200),
    date DATETIME NOT NULL);
    
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
    household_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    household_name VARCHAR(30) NOT NULL);
	
COMMIT;

CREATE TABLE preparer_tax_return (
    tax_return_email VARCHAR(320) NOT NULL,
	tax_return_year YEAR NOT NULL,
    tax_preparer_email VARCHAR(320),
    PRIMARY KEY (tax_return_email, tax_return_year, tax_preparer_email),
    FOREIGN KEY (tax_preparer_email, tax_return_year) REFERENCES tax_return(email, year),
    FOREIGN KEY (tax_preparer_email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE payment (
    payment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
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

SET GLOBAL event_scheduler = ON;

/* The following section is for testing purposes only!
 * TODO: Remove before deployment.
 */

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("test@test.com", "Timmy", "Turner", 1, "7e25593a4fd6e8ea3847694d367e386f50f11826eb9d7249f02a634e065ba221", "TLfqFkURJ5/lSyDlp1EOP7etmbM4CgqjPM4Hfp9g/AU=", CURDATE(), "T", "eng", "T");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("example@test.com", "Roger", "Rabbit", 1, "5b38e98e36f7316530be21f4ac1089d5689010ce55469f8d22c52053e32e1ea6", "EUmgQiGBpAy+pcPdAAVR1e2zd4xl8fcz0tF3sQOd5uI=", CURDATE(), "T", "eng", "T");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("jdgoerzen@gmail.com", "Jesse", "Goerzen", 1, "1aeabca5c2298936def8a3be36fc04329a850dc3e64af379221b523b34a7785e", "w/RM8dL10GWXEETXyatiIhbB+SHcMWirDt54sYNfVxU=", CURDATE(), "T", "eng", "T");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("proton1guzman@gmail.com", "Cesar", "Guzman", 1, "1aeabca5c2298936def8a3be36fc04329a850dc3e64af379221b523b34a7785e", "AkJv9U1EomdagYn0SF/Hl9XiKDt2s2dLHL0TukPO3+k=", CURDATE(), "T", "eng", "T");

INSERT INTO tax_return (email, status, year)
VALUES ("test@test.com", "new", 2019);

INSERT INTO parcel (subject, message, sender, receiver, date_sent, tax_return_year)
VALUES ("Welcome", "Hello Timmy! I am Roger and I will be your bimbo for this year.", "example@test.com", "test@test.com", CURDATE(), 2019);

INSERT INTO parcel (subject, message, sender, receiver, date_sent, tax_return_year)
VALUES ("Regarding Your Return", "Good Afternoon, Timmy. Looking over your form, you seem to have forgotten literally everything. Please fix.", "example@test.com", "test@test.com", CURDATE(), 2019);
