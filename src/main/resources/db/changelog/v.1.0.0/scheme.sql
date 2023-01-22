CREATE SCHEMA IF NOT EXISTS requests;

CREATE TABLE IF NOT EXISTS requests.requests
(
    id         SERIAL PRIMARY KEY,
    Date       TIMESTAMP with time zone NOT NULL,
    Status     VARCHAR                  NOT NULL,
    Originator VARCHAR                  NOT NULL,
    Type       VARCHAR                  NOT NULL,
    Mass       DECIMAL                  NOT NULL,
    Deadline   TIMESTAMP with time zone NOT NULL,
    Objective  VARCHAR                  NOT NULL,
    Comments   VARCHAR

);

CREATE TABLE IF NOT EXISTS requests.auth_user
(
    id         SERIAL PRIMARY KEY       NOT NULL,
    created_at  TIMESTAMP with time zone NOT NULL,
    first_name  VARCHAR                  NOT NULL,
    last_name VARCHAR                  NOT NULL,
    email      VARCHAR                  NOT NULL,
    login      VARCHAR                  NOT NULL,
    role       INT                      NOT NULL
);

CREATE TABLE IF NOT EXISTS requests.auth_role
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR            NOT NULL
);

ALTER TABLE requests.auth_user
    ADD CONSTRAINT FK_role
        FOREIGN KEY (role) REFERENCES requests.auth_role (id);
