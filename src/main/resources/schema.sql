CREATE SCHEMA IF NOT EXISTS requests;

CREATE TABLE requests.requests
(
    id         SERIAL PRIMARY KEY       NOT NULL,
    Date       TIMESTAMP with time zone NOT NULL,
    Status     VARCHAR                  NOT NULL,
    Originator VARCHAR                  NOT NULL,
    Type       VARCHAR                  NOT NULL,
    Mass       DECIMAL                  NOT NULL,
    Deadline   TIMESTAMP with time zone NOT NULL,
    Objective  VARCHAR                  NOT NULL,
    Comments   VARCHAR

);

CREATE TABLE requests.auth_user
(
    userId     SERIAL PRIMARY KEY NOT NULL,
    createdAt  TIMESTAMP with time zone NULL,
    firstName  VARCHAR            NOT NULL,
    secondName VARCHAR            NOT NULL,
    email      VARCHAR            NOT NULL,
    login      VARCHAR            NOT NULL,
    role       VARCHAR            NOT NULL,
    FOREIGN KEY (role) REFERENCES auth_role (roleName);

);

CREATE TABLE requests.auth_role
(
    roleId   SERIAL PRIMARY KEY NOT NULL,
    roleName VARCHAR            NOT NULL
);