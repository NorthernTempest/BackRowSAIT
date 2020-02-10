CREATE TABLE session (
    session_id CHAR(32) PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    timeout DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));