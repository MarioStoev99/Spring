DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher
(
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    age        INT NULL,
    address    VARCHAR(255) NULL,
    school_id  INT
);

