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
    pass_salt CHAR(32) NOT NULL,
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
    return_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    household_id INT,
    status VARCHAR(20) NOT NULL,
    cost DOUBLE(10,2),
    year INT(4) NOT NULL,
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
    return_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (sender) REFERENCES user(email),
    FOREIGN KEY (receiver) REFERENCES user(email),
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id));
	
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
    household_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    household_name VARCHAR(30) NOT NULL);
	
COMMIT;

CREATE TABLE preparer_tax_return (
    return_id INT UNSIGNED,
    email VARCHAR(320),
    PRIMARY KEY (return_id, email),
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id),
    FOREIGN KEY (email) REFERENCES user(email));
	
COMMIT;

CREATE TABLE payment (
    payment_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    return_id INT UNSIGNED NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    amount DOUBLE(10,2) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id));
	
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
VALUES ("test@test.com", "Timmy", "Turner", 1, "70617373776f7264", "word", CURDATE(), "T", "eng", "T");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("example@test.com", "Roger", "Rabbit", 1, "70617373776f7264", "word", CURDATE(), "T", "eng", "T");

INSERT INTO user (email, f_name, l_name, permission_level, pass_hash, pass_salt, creation_date, active, language, verified)
VALUES ("jdgoerzen@gmail.com", "Jesse", "Goerzen", 1, "70617373776f7264", "word", CURDATE(), "T", "eng", "T");

INSERT INTO tax_return (email, status, year)
VALUES ("test@test.com", "new", 2019);

INSERT INTO parcel (subject, message, sender, receiver, date_sent, return_id)
VALUES ("Welcome", "Hello Timmy! I am Roger and I will be your bimbo for this year.", "example@test.com", "test@test.com", CURDATE(), 1);

INSERT INTO parcel (subject, message, sender, receiver, date_sent, return_id)
VALUES ("Regarding Your Return", "Good Afternoon, Timmy. Looking over your form, you seem to have forgotten literally everything. Please fix.", "example@test.com", "test@test.com", CURDATE(), 1);
