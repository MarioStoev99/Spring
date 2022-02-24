DROP TABLE IF EXISTS parent;
CREATE TABLE parent
(
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    age        int NULL,
    address    VARCHAR(255) NULL
);

ALTER TABLE parent
    ADD CONSTRAINT student_fkey FOREIGN KEY (student_id) REFERENCES student (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
