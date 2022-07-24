create extension hstore;

create schema logindatabase;
create schema reviewdatabase;

CREATE TABLE logindatabase."LoginDatabase" (
	id SERIAL PRIMARY KEY,
	username varchar(20) NOT NULL UNIQUE,
	password varchar(20) NOT NULL
);

CREATE TABLE reviewdatabase."ReviewDatabase" (
    id SERIAL PRIMARY KEY,
	ratings varchar(20),
	username varchar(20) REFERENCES logindatabase."LoginDatabase"(username) ON DELETE CASCADE,
	review varchar(2000) NOT NULL
);