CREATE TABLE preparer_tax_return (
    return_id INT,
    email VARCHAR(320),
    PRIMARY KEY (return_id, email),
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id),
    FOREIGN KEY (email) REFERENCES user(email));