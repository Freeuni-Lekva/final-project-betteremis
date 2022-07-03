USE db;

DROP TABLE IF EXISTS USERS;
 -- remove table if it already exists and start from scratch

CREATE TABLE USERS (
	ID int NOT NULL AUTO_INCREMENT,
    Username CHAR(255) NOT NULL UNIQUE,
    PasswordHash CHAR(255) NOT NULL,
    Privilege CHAR(64) NOT NULL,
	
	CHECK (Privilege in ('Student', 'Lecturer', 'Admin')),
	
	PRIMARY KEY (ID)
);