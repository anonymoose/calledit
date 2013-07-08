/*
createdb -U postgres calledit
psql -U postgres -c "create user calledit with password 'calledit';"
psql -U postgres -c "alter database calledit owner to calledit;"
psql -U postgres -d calledit -c 'create extension "uuid-ossp";'
*/

drop table if exists ci_user cascade;
drop table if exists ci_call cascade;

create table ci_user (
    user_id serial not null primary key,
    email varchar(50) not null,
    password varchar(50),
    create_dt timestamp without time zone default now(),
    delete_dt timestamp without time zone

);


create table ci_call (
    call_id serial not null primary key,
    user_id integer not null,
    title varchar(140) not null,
    details varchar(2000),
    create_dt timestamp without time zone default now(),
    delete_dt timestamp without time zone
);



