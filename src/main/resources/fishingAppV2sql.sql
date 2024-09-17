drop database if exists fishing_demo;

CREATE database if not exists fishing_demo;

USE fishing_demo;

drop table if exists users;

create table if not exists users(
id bigint primary key auto_increment not NULL,
username varchar(50) unique not null,
name varchar(50) not null,
password varchar(500) not null,
email varchar(100) unique not null,
role varchar(20) not null,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) auto_increment = 31982;

drop table if exists sessions;

CREATE TABLE if not exists sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    venue VARCHAR(100) NOT NULL,
    start_date DATETIME NOT NULL,
    duration_hours INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_id) references users(id)
);

CREATE TABLE anglers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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

CREATE TABLE angler_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    angler_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (angler_id) REFERENCES anglers(id),
    FOREIGN KEY (session_id) REFERENCES sessions(id)
);

-- drop table angler_session; 
-- drop table sessions;
-- drop table catches;
