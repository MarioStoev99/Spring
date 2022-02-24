DROP TABLE IF EXISTS patient;
CREATE TABLE patient
(
    identical_number VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    paidMedicalInsurance BOOLEAN NOT NULL,
);