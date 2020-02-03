CREATE TABLE payment (
    payment_id INT PRIMARY KEY,
    return_id INT NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    amount DOUBLE(10,2) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (return_id) REFERENCES tax_return(return_id));