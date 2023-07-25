CREATE TABLE position
(
    id   BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    position varchar(100) NOT NULL
);

CREATE TABLE employee
(
    id            BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    first_name    varchar(100) NOT NULL,
    last_name     varchar(100) NOT NULL,
    date_of_birth date         NOT NULL,
    position_id   BIGINT REFERENCES position (id)
);

CREATE TABLE project
(
    id   BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    project_name varchar(100) NOT NULL
);

CREATE TABLE employee_project
(
    employee_id BIGINT REFERENCES employee (id),
    project_id  BIGINT REFERENCES project (id)
);


-- Тестовые данные --
INSERT INTO position(position)
VALUES ('Разработчик'),
       ('Тестировщик');
INSERT INTO employee(first_name, last_name, date_of_birth, position_id)
VALUES ('Василий', 'Васильев', '1995-01-08', 1),
       ('Алексей', 'Алексеев', '1991-04-23', 2),
       ('Андрей', 'Андреев', '1980-08-12', 2),
       ('Оксана', 'Архипова', '1997-10-15', 1);
INSERT INTO project(project_name)
VALUES ('Банк'),
       ('Сеть магазинов');
INSERT INTO employee_project(employee_id, project_id)
VALUES (1, 1),
       (2, 2),
       (3, 1),
       (4, 2);