DROP TABLE IF EXISTS director;
CREATE TABLE director
(
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    age        int,
    address    VARCHAR(255) NULL
);