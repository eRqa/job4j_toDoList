CREATE DATABASE toDoList;

CREATE TABLE ITEMS (
    ID SERIAL PRIMARY KEY,
    DESCRIPTION TEXT,
    CREATED TIMESTAMP,
    DONE boolean
);