DROP TABLE IF EXISTS subject;
CREATE TABLE subject
(
    id      UUID NOT NULL PRIMARY KEY,
    name    VARCHAR(255) NULL,
    grade   int NULL,
    absence int NULL
);

ALTER TABLE subject
    ADD CONSTRAINT teacher_fkey FOREIGN KEY (teacher_id) REFERENCES teacher (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
