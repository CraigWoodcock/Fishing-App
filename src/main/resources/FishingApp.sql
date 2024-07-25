CREATE database if not exists fishing_app;
USE fishing_app;
drop table if exists users;
create table if not exists users(
id bigint primary key auto_increment not NULL,
username varchar(50) unique not null,
name varchar(50) not null,
password varchar(500) not null,
email varchar(100) unique not null,
role varchar(20) not null
) auto_increment = 31982;

select * from users;

