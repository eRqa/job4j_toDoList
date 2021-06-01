CREATE DATABASE toDoList;

CREATE TABLE ITEMS (
    ID SERIAL PRIMARY KEY,
    DESCRIPTION TEXT,
    CREATED TIMESTAMP,
    DONE boolean
);

create table users (
    id serial primary key,
    name varchar(2000)
);

ALTER TABLE items
    ADD author_id INTEGER REFERENCES USERS (Id);

ALTER TABLE users
    ADD email TEXT UNIQUE;

ALTER TABLE users
    ADD password TEXT;

CREATE TABLE BRANDS (
                        ID SERIAL PRIMARY KEY,
                        NAME TEXT
);

CREATE TABLE MODELS (
                        ID SERIAL PRIMARY KEY,
                        NAME TEXT
);

