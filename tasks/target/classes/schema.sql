drop table if exists tasks;
drop table if exists users;
drop table if exists requests;
drop table if exists invites;
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

create table requests
(
    id           serial
        primary key,
    comment      varchar(500),
    requester_id integer
        constraint fkeoax2t4j9i61p9lmon3009tr4
            references users,
    task_id      integer
        constraint fkqri6ch0bqo3518odone7o7m30
            references tasks
);

create table invites
(
    id         serial
        primary key,
    accepted   boolean,
    comment    varchar(500),
    invited_id integer
        constraint fkoghy46jb8uwtm09codd7gad7d
            references users,
    inviter_id integer
        constraint fk7g695j19v6a7911weymlf5dlj
            references users,
    task_id    integer
        constraint fk84pdgxfoa9ivnuombqxq9ge3c
            references tasks
);
