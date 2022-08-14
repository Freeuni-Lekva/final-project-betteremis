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

    QUIZ DOUBLE NOT NULL,
    HOMEWORK DOUBLE NOT NULL,
    PROJECT DOUBLE NOT NULL,
    PRESENTATION DOUBLE NOT NULL,
    MIDTERM DOUBLE NOT NULL,
    FINAL DOUBLE NOT NULL,
    FX DOUBLE NOT NULL,
	IsCompleted BOOLEAN NOT NULL,
    
	CHECK (Semester >= 0),
	CHECK ((QUIZ >= 0 AND QUIZ <= 100) or QUIZ = -1),
    CHECK ((HOMEWORK >= 0 AND HOMEWORK <= 100) or HOMEWORK = -1),
    CHECK ((PROJECT >= 0 AND PROJECT <= 100) or PROJECT = -1),
    CHECK ((PRESENTATION >= 0 AND PRESENTATION <= 100) or PRESENTATION = -1),
    CHECK ((MIDTERM >= 0 AND MIDTERM <= 100) or MIDTERM = -1),
    CHECK ((FINAL >= 0 AND FINAL <= 100) or FINAL = -1),
    CHECK ((FX >= 0 AND FX <= 100) or FX = -1),
	
    FOREIGN KEY (UserID) REFERENCES STUDENTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (SubjectID) REFERENCES SUBJECTS(ID) ON DELETE CASCADE,
    CONSTRAINT unique_userID_and_subjectID_pair UNIQUE(UserID, SubjectID),
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

DROP TABLE IF EXISTS TOKENS;
-- remove table if it already exists and start from scratch

CREATE TABLE TOKENS(
  ID int NOT NULL AUTO_INCREMENT,
  Token CHAR(64) NOT NULL,
  DateAdded TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID),
  CONSTRAINT unique_token unique (Token)
);


DROP TABLE IF EXISTS FRIENDS;
-- remove table if it already exists and start from scratch

CREATE TABLE FRIENDS(
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  UserID int NOT NULL,
  FriendID int NOT NULL,


  CONSTRAINT uniq unique (UserID, FriendID),
  FOREIGN KEY (UserID) REFERENCES USERS(ID) ON DELETE CASCADE,
  FOREIGN KEY (FriendID) REFERENCES USERS(ID) ON DELETE CASCADE

);

DROP TABLE IF EXISTS FRIEND_REQS;
-- remove table if it already exists and start from scratch

CREATE TABLE FRIEND_REQS(
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  UserID int NOT NULL,
  FriendID int NOT NULL,

  CONSTRAINT uniq unique (UserID, FriendID),
  FOREIGN KEY (UserID) REFERENCES USERS(ID) ON DELETE CASCADE,
  FOREIGN KEY (FriendID) REFERENCES USERS(ID) ON DELETE CASCADE

);

DROP TABLE IF EXISTS REGISTRATION_STATUS;
-- remove table if it already exists and start from scratch

CREATE TABLE REGISTRATION_STATUS(
  INTEGRITY_KEEPER ENUM('Integrity Keeper') NOT NULL PRIMARY KEY,
  IsOpen BOOLEAN NOT NULL
);

INSERT INTO REGISTRATION_STATUS (IsOpen) VALUES (false);

DROP TABLE IF EXISTS MAIL;
-- remove table if it already exists and start from scratch

CREATE TABLE MAIL(
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    UserIDFrom int NOT NULL,
    UserIDTo int NOT NULL,
    SendDate DATETIME DEFAULT CURRENT_TIMESTAMP(),
    Message MEDIUMTEXT NOT NULL,

    CHECK (UserIDFrom != UserIDTo),
    FOREIGN KEY (UserIDFrom) REFERENCES USERS(ID) ON DELETE CASCADE,
    FOREIGN KEY (UserIDTo) REFERENCES USERS(ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS CLASSROOMS;
-- remove table if it already exists and start from scratch

CREATE TABLE CLASSROOMS(
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    SubjectID int NOT NULL,
    Semester int NOT NULL,
    LecturerID int NOT NULL,  -- ID of the lecturer that created the classroom
    CreationDate DATETIME DEFAULT CURRENT_TIMESTAMP(),

    CONSTRAINT unique_constraint unique (SubjectID, Semester),
    FOREIGN KEY (SubjectID) REFERENCES SUBJECTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (LecturerID) REFERENCES LECTURERS(ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS CLASSROOM_POSTS;
-- remove table if it already exists and start from scratch

CREATE TABLE CLASSROOM_POSTS(
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ClassroomID int NOT NULL,
    UserID int NOT NULL, -- ID of user who created the post
    Message MEDIUMTEXT NOT NULL,
    PostDate DATETIME DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY (UserID) REFERENCES USERS(ID) on DELETE CASCADE,
    FOREIGN KEY (ClassroomID) REFERENCES CLASSROOMS(ID) ON DELETE CASCADE
);

DROP TABLE IF EXISTS POST_COMMENTS;
-- remove table if it already exists and start from scratch

CREATE TABLE POST_COMMENTS(
    ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PostID int NOT NULL,
    UserID int NOT NULL, -- ID of user who commented on a post
    Message MEDIUMTEXT NOT NULL,
    PostDate DATETIME DEFAULT CURRENT_TIMESTAMP(),

    FOREIGN KEY (UserID) REFERENCES USERS(ID) on DELETE CASCADE,
    FOREIGN KEY (PostID) REFERENCES CLASSROOM_POSTS(ID) ON DELETE CASCADE
);

