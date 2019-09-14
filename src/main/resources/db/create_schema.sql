DROP DATABASE IF EXISTS tasks;
DROP USER IF EXISTS tasks_user;

USE mysql;
CREATE DATABASE tasks DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
CREATE USER 'tasks_user'@'%' IDENTIFIED BY 'pass';
GRANT ALL ON tasks.* TO 'tasks_user'@'%';
FLUSH PRIVILEGES;

USE tasks;

create table tasks.category
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

create table tasks.task
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    canceled_at  DATE,
    created_at   DATE,
    deadline     DATE,
    description  VARCHAR(255),
    status       VARCHAR(255),
    category_id  BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES tasks.category(id) ON DELETE RESTRICT
);
