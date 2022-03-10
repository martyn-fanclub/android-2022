CREATE DATABASE ChineseCafeteria;
USE ChineseCafeteria;

/* MENU */
CREATE TABLE products(
	id SERIAL PRIMARY KEY,
	photo_uri VARCHAR(100),
	name VARCHAR(60); /* On several languages */
	consistuent TEXT,
	callories SMALLINT,
	price INT
);

/* ORDERS */
CREATE TABLE status(
	id SERIAL PRIMARY KEY,
	condition VARCHAR(20)
);

CREATE TABLE orders(
	id SERIAL PRIMARY KEY,
	status_id BIGINT,
	FOREIGN KEY(status_id) REFERENCES status(id),
	total TEXT
);

/* USERS */
CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	email VARCHAR(50),
	buy_total INT
);
