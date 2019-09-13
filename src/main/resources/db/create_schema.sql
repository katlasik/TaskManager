DROP DATABASE IF EXISTS tasks;
DROP USER IF EXISTS tasks_user;

USE mysql;
CREATE DATABASE tasks DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
CREATE USER 'tasks_user'@'%' IDENTIFIED BY 'pass';
GRANT ALL ON tasks.* TO 'tasks_user'@'%';
FLUSH PRIVILEGES;

USE tasks;

create table Category
(
    id   bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table Task
(
    id          bigint not null auto_increment,
    canceledAt  date,
    createdAt   date,
    deadline    date,
    description varchar(255),
    status      varchar(255),
    category_id bigint,
    primary key (id)
) engine = InnoDB;

alter table Task
    add constraint Task_Category_fk foreign key (category_id) references Category (id)