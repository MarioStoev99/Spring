DROP TABLE IF EXISTS curriculum;
CREATE TABLE curriculum
(
    id   UUID NOT NULL PRIMARY KEY,
    term int NULL
);

ALTER TABLE curriculum
    ADD CONSTRAINT teacher_fkey FOREIGN KEY (teacher_id) REFERENCES teacher (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE curriculum
    ADD CONSTRAINT subject_fkey FOREIGN KEY (subject_id) REFERENCES subject (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
