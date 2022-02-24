DROP TABLE IF EXISTS school;
CREATE TABLE school
(
    id          UUID NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NULL,
    address     VARCHAR(255) NULL,
    student_id  INT,
    director_id INT
);

ALTER TABLE school
    ADD CONSTRAINT student_fkey FOREIGN KEY (student_id) REFERENCES student (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE school
    ADD CONSTRAINT director_fkey FOREIGN KEY (director_id) REFERENCES director (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

-- ALTER TABLE school
--     ADD CONSTRAINT curriculum_fkey FOREIGN KEY (curriculum_id) REFERENCES curriculum (id)
--         ON DELETE CASCADE
--         ON UPDATE CASCADE;