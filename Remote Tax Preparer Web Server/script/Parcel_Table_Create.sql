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