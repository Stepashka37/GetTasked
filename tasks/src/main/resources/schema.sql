drop table if exists tasks;
drop table if exists users;
drop table if exists user_task;

create table  tasks
(
    id          serial
        primary key,
    deadline    timestamp     not null,
    description varchar(2000) not null,
    name        varchar(100)  not null,
    start       timestamp     not null
);


create table users
(
    id       serial
        primary key,
    email    varchar(50)  not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    grade    varchar(255),
    name     varchar(50)  not null,
    password varchar(255) not null,
    spec     varchar(255)
);


create table user_task
(
    responsible boolean,
    task_id     bigint not null
        constraint fkp7b0g1h9lxrklls4s1pw68nj2
            references tasks,
    user_id     bigint not null
        constraint fkj6lai3y87ttxldkysg1549etg
            references users,
    primary key (task_id, user_id)
);
