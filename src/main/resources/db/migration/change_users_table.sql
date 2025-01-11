--liquibase formatted sql
--changeset Nedobezhkin.M.I.:change_user_table
alter table users
rename column fullname to first_name;

alter table users
add column last_name varchar(255);

alter table users
add column job_title varchar(1024);

alter table users
alter user_role set default 'USER';
--rollback rename column first_name to fullname;

--changeset Nedobezhkin.M.I.:add_auto_generation_id
ALTER TABLE users
alter id add generated ALWAYS AS IDENTITY;
--rollback ;

--changeset Nedobezhkin.M.I.:add_is_active_column
ALTER TABLE users
add column is_active boolean default true;
--rollback ;

