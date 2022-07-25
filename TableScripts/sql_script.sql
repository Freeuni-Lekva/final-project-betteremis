DROP DATABASE IF EXISTS oopdb;

CREATE DATABASE oopdb;

USE oopdb;

DROP TABLE IF EXISTS USERS;
 -- remove table if it already exists and start from scratch

CREATE TABLE USERS (
	ID int NOT NULL AUTO_INCREMENT,
    Email CHAR(255) NOT NULL UNIQUE,
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
    CurrentSemester int NOT NULL,
    Gender CHAR(20) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Address CHAR(255) NOT NULL, -- Expected form (Country, City, Address).
    StudentStatus CHAR(64) NOT NULL,
    School CHAR(64) NOT NULL,
    Credits int NOT NULL,
    GPA DOUBLE NOT NULL,
    PhoneNumber CHAR(64) NOT NULL,
    GroupName CHAR(100) NOT NULL,

	CHECK (Gender in ('Female', 'Male', 'Other')),
    CHECK (CurrentSemester >= 0),
	CHECK (StudentStatus in ('Active', 'Inactive')),
    CHECK (Credits >= 0),
    CHECK (GPA >= 0),
    
	FOREIGN KEY (UserID) REFERENCES USERS(ID) ON DELETE CASCADE
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
    DateOfBirth DATE NOT NULL,
    Address CHAR(255) NOT NULL, -- Expected form (Country, City, Address).
    LecturerStatus CHAR(64) NOT NULL,
    PhoneNumber CHAR(64) NOT NULL,
    
	CHECK (Gender in ('Female', 'Male', 'Other')),
	CHECK (LecturerStatus in ('Active', 'Inactive')),
    
	FOREIGN KEY (UserID) REFERENCES USERS(ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS SUBJECTS;
 -- remove table if it already exists and start from scratch

CREATE TABLE SUBJECTS (
	ID int NOT NULL AUTO_INCREMENT,
    SubjectName CHAR(255) NOT NULL UNIQUE,
    Credits int NOT NULL,
	LecturerID int NOT NULL,
	

	CHECK (Credits >= 0),
	
	FOREIGN KEY (LecturerID) REFERENCES LECTURERS(ID) ON DELETE CASCADE,
	PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS SUBJECTS_HISTORY;
 -- remove table if it already exists and start from scratch

CREATE TABLE SUBJECTS_HISTORY (
	ID int NOT NULL AUTO_INCREMENT,
    UserID int NOT NULL,
    SubjectID int NOT NULL,
	Semester int NOT NULL,
    Grade DOUBLE NOT NULL,
	IsCompleted BOOLEAN NOT NULL,
    
	CHECK (Semester >= 0),
	CHECK (Grade >= 0 AND Grade <= 100),
	
    FOREIGN KEY (UserID) REFERENCES STUDENTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (SubjectID) REFERENCES SUBJECTS(ID) ON DELETE CASCADE,
	PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS PREREQUISITES;
-- remove table if it already exists and start from scratch

CREATE TABLE PREREQUISITES (
    ID int NOT NULL AUTO_INCREMENT,
    SubjectID int NOT NULL,
    Prerequisites CHAR(255) NOT NULL,


    FOREIGN KEY (SubjectID) REFERENCES SUBJECTS(ID) ON DELETE CASCADE,
    PRIMARY KEY (ID)
);