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

--changeset Nedobezhkin.M.I.:create_orders_table
create table if not exists orders
(
    id int primary key generated ALWAYS AS IDENTITY,
    admin_id int references users(id),
    emploee_id int references users(id),
    date_order timestamp,
    products jsonb,
    status varchar(10)
    );
--rollback drop table orders;
