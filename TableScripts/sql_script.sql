DROP DATABASE IF EXISTS oopdb;

CREATE DATABASE oopdb;

USE oopdb;

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

DROP TABLE IF EXISTS STUDENTS;
 -- remove table if it already exists and start from scratch
 
CREATE TABLE STUDENTS (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UserID int NOT NULL UNIQUE,
	FirstName CHAR(64) NOT NULL,
    LastName CHAR(64) NOT NULL,
    Profession CHAR(255) NOT NULL,
    Degree CHAR(64) NOT NULL,
    CurrentSemester CHAR(10) NOT NULL,
    Gender CHAR(20) NOT NULL,
    Nationality CHAR(20) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Address CHAR(255) NOT NULL, -- Expected form (Country, City, Address).
    StudentStatus CHAR(64) NOT NULL,
    School CHAR(64) NOT NULL,
    Credits int NOT NULL,
    GPA DOUBLE NOT NULL,
    PhoneNumber CHAR(64) NOT NULL,
    GroupName CHAR(100) NOT NULL,

	CHECK (Gender in ('Female', 'Male', 'Other')),
	CHECK (Degree in ('Bachelor', 'Master', 'PhD')),
    CHECK (CurrentSemester in ('I', 'II', 'III', 'IV', 'V', 'VI', 'VII', 'VIII')),
	CHECK (StudentStatus in ('Active', 'Inactive')),
    CHECK (Credits >= 0),
    CHECK (GPA >= 0),
    
	FOREIGN KEY (UserID) REFERENCES USERS(ID)
);

DROP TABLE IF EXISTS LECTURERS;
 -- remove table if it already exists and start from scratch
 
CREATE TABLE LECTURERS (
	ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UserID int NOT NULL UNIQUE,
	FirstName CHAR(64) NOT NULL,
    LastName CHAR(64) NOT NULL,
    Profession CHAR(255) NOT NULL,
    Gender CHAR(20) NOT NULL,
    Nationality CHAR(20) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Address CHAR(255) NOT NULL, -- Expected form (Country, City, Address).
    LecturerStatus CHAR(64) NOT NULL,
    PhoneNumber CHAR(64) NOT NULL,
    
	CHECK (Gender in ('Female', 'Male', 'Other')),
	CHECK (LecturerStatus in ('Active', 'Inactive')),
    
	FOREIGN KEY (UserID) REFERENCES USERS(ID)
);