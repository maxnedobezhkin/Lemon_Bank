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

--changeset Nedobezhkin.M.I.:drop_orders_table
drop table if exists orders;
--rollback ;

--changeset Nedobezhkin.M.I.:create_new_orders_table
create table if not exists orders
(
    id int primary key generated ALWAYS AS IDENTITY,
    employee_id int references users(id),
    date_order varchar,
    total int,
    items varchar,
    status varchar(10),
    tilda_id varchar
    );
--rollback drop table orders;

--changeset Nedobezhkin.M.I.:create_new_history_table
create table if not exists history
(
    id int primary key generated ALWAYS AS IDENTITY,
    user_id int references users(id),
    admin_id int references users(id),
    date_ timestamp,
    type_ varchar,
    comment varchar,
    order_id int references orders(id),
    currency varchar,
    value_ int
    );
--rollback drop table history;
