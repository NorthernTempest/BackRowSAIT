CREATE TABLE tax_return (
    return_id INT PRIMARY KEY,
    email VARCHAR(320) NOT NULL,
    household_id INT,
    status VARCHAR(20) NOT NULL,
    cost DOUBLE(10,2),
    year INT(4) NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email));