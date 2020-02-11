CREATE TABLE user (
    email VARCHAR(320) PRIMARY KEY,
    f_name VARCHAR(30) NOT NULL,
    m_name VARCHAR(30) NOT NULL,
    l_name VARCHAR(30) NOT NULL,
    permission_level INT NOT NULL,
    phone CHAR(10),
    pass_hash CHAR(32) NOT NULL,
    salt CHAR(32) NOT NULL,
    title VARCHAR(5),
    creation_date DATE NOT NULL,
    fax CHAR(10),
    active CHAR(1) NOT NULL,
    verification_id CHAR(36),
    last_verification_date DATE,
    last_verification_type INT);
    verified CHAR(1) NOT NULL);
    
ALTER TABLE user 
ADD CONSTRAINT CHK_user_permission_level CHECK (permission_level BETWEEN 0 AND 3);

ALTER TABLE user
ADD CONSTRAINT CHK_user_active CHECK (active = 'T' OR active = 'F');

ALTER TABLE user
    ADD CONSTRAINT CHK_user_verified CHECK (verified = 'T' OR verified = 'F');

COMMIT;