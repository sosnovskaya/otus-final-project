create table person
(
    id   bigserial   not null primary key,
    name varchar(50) not null
);

create table task
(
    id          bigserial   not null primary key,
    name        varchar(50) not null,
    description varchar(500),
    person_id   bigserial   not null references person (id),
    start_time  timestamp,
    end_time    timestamp
);
