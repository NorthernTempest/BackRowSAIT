CREATE TABLE log (
    log_id INT PRIMARY KEY,
    email VARCHAR(320),
    type CHAR(1) NOT NULL,
    message VARCHAR(200),
    date DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));
    
ALTER TABLE log 
ADD CONSTRAINT CHK_log_type CHECK (type IN ('L', 'E', 'D', 'U'));