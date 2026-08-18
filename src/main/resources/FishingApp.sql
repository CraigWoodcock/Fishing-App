-- drop database if exists fishing_app;

CREATE database if not exists fishing_app;

USE fishing_app;

-- drop table if exists users;

create table if not exists users
(
    id         bigint primary key auto_increment not NULL,
    username   varchar(50) unique                not null,
    name       varchar(50)                       not null,
    password   varchar(500)                      not null,
    email      varchar(100) unique               not null,
    role       varchar(20)                       not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) auto_increment = 31982;

-- drop table if exists sessions;

CREATE TABLE if not exists sessions
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id        BIGINT                            NOT NULL,
    venue          VARCHAR(100)                      NOT NULL,
    start_date     DATE                          NOT NULL,
    duration_hours INT                               NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (user_id) references users (id)
);

-- drop table if exists anglers;
CREATE TABLE if not exists anglers
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name       VARCHAR(50)                       NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    email 	   VARCHAR(100) unique NOT NULL
);

-- drop table if exists catches;

CREATE TABLE if not exists catches
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    angler_id   BIGINT                            NOT NULL,
    session_id  BIGINT                            NOT NULL,
    catch_time  DATETIME                          NOT NULL,
    peg_or_swim VARCHAR(50),
    fish_type   VARCHAR(50)                       NOT NULL,
    weight      DECIMAL(5, 2)                     NOT NULL,
    notes       TEXT,
    photo_url   VARCHAR(255),
    FOREIGN KEY (angler_id) REFERENCES anglers (id),
    FOREIGN KEY (session_id) REFERENCES sessions (id)
);

-- drop table if exists angler_session;
CREATE TABLE if not exists angler_session
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    angler_id  BIGINT                            NOT NULL,
    session_id BIGINT                            NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (angler_id) REFERENCES anglers (id),
    FOREIGN KEY (session_id) REFERENCES sessions (id)
);

-- drop table if exists jwt_tokens;

CREATE TABLE IF NOT EXISTS jwt_tokens
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    token       VARCHAR(255) NOT NULL,
    user_id     BIGINT       NOT NULL,
    expiry_date TIMESTAMP    NOT NULL,
    revoked     BOOLEAN      NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE if not exists audit_logs (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    performed_by        VARCHAR(50)  NOT NULL,
    action              VARCHAR(50)  NOT NULL,
    target_description  VARCHAR(255) NOT NULL,
    timestamp           DATETIME     NOT NULL,
    details				VARCHAR(255)	NULL
);

CREATE INDEX idx_audit_logs_timestamp ON audit_logs (timestamp DESC);
CREATE INDEX idx_audit_logs_performed_by ON audit_logs (performed_by);



-- INSERT INTO users (username, name, password, email, role)
-- VALUES ('craig', 'craig woodcock', 'craig', 'craig@example.com', 'USER');
-- drop table angler_session; 
-- drop table sessions;
-- drop table catches;

select * from audit_logs;

