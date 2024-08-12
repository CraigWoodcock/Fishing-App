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

CREATE TABLE sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    venue VARCHAR(100) NOT NULL,
    start_date DATETIME NOT NULL,
    duration_hours INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE anglers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    session_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    FOREIGN KEY (session_id) REFERENCES sessions(id)
);

CREATE TABLE catches (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    angler_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    catch_time DATETIME NOT NULL,
    peg_or_swim VARCHAR(50),
    fish_type VARCHAR(50) NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    notes TEXT,
    photo_url VARCHAR(255),
    FOREIGN KEY (angler_id) REFERENCES anglers(id),
    FOREIGN KEY (session_id) REFERENCES sessions(id)
);