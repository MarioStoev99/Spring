DROP TABLE IF EXISTS medical-record;
CREATE TABLE medical-record
(
    id        UUID         NOT NULL PRIMARY KEY,
    patientId UUID         NOT NULL,
    diagnosis VARCHAR(255) NOT NULL,
    treatment VARCHAR(255) NOT NULL,
    doctorIds UUID         NOT NULL,
    sickDays  int          NOT NULL,
    createdAt TIMESTAMP    NOT NULL,
);