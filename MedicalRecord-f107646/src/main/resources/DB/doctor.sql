DROP TABLE IF EXISTS doctor;
CREATE TABLE doctor
(
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NULL,
);

ALTER TABLE doctor
    ADD CONSTRAINT patient_fkey FOREIGN KEY (patient_id) REFERENCES patient (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
