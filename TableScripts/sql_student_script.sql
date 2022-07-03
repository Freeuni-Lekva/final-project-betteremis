USE db;

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
    
	FOREIGN KEY (UserID) REFERENCES users(ID)
);