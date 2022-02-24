DROP TABLE IF EXISTS student;
CREATE TABLE student
(
    id           UUID NOT NULL PRIMARY KEY,
    first_name   VARCHAR(255) NULL,
    last_name    VARCHAR(255) NULL,
    age          int NULL,
    address      VARCHAR(255) NULL,
    yearAtSchool int NULL,
    parent_id    INTEGER REFERENCES parent (id)
);

ALTER TABLE student
    ADD CONSTRAINT subject_fkey FOREIGN KEY (subject_id) REFERENCES subject (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
