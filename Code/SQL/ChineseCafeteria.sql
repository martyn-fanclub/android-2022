CREATE DATABASE ChineseCafeteria;
USE ChineseCafeteria;

CREATE TABLE products(
	id SERIAL PRIMARY KEY,
	name VARCHAR(20);
	consistuent TEXT,
	callories INT,
	price INT
);

CREATE TABLE orders(
	id SERIAL PRIMARY KEY,
	status_id BIGINT,
	FOREIGN KEY(status_id) REFERENCES status(id),
	total TEXT
);

CREATE TABLE status(
	id SERIAL PRIMARY KEY,
	condition TEXT
);
