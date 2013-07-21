/*
createdb -U postgres calledit
psql -U postgres -c "create user calledit with password 'calledit';"
psql -U postgres -c "alter database calledit owner to calledit;"
psql -U postgres -d calledit -c 'create extension "uuid-ossp";'
*/

drop table if exists ci_user cascade;
drop table if exists ci_call cascade;

create table ci_user (
    email varchar(50) not null primary key,
    password varchar(50),
    create_dt timestamp without time zone default now(),
    delete_dt timestamp without time zone

);


create table ci_call (
    call_id varchar(25) not null primary key,
    email varchar(50),
    prediction varchar(500) not null,
    prediction_dt date,
    create_dt timestamp without time zone default now(),
    delete_dt timestamp without time zone
);

create index ci_call_email on ci_call (email);



