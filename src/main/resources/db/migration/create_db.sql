--liquibase formatted sql
--changeset Nedobezhkin.M.I.:create_user_table
create table if not exists users
(
    id int primary key,
    fullname varchar(50) not null,
    email varchar(50) unique not null,
    password varchar(100),
    lemons int,
    diamonds int,
    user_role varchar(25)
    );
--rollback drop table users;
