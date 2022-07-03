USE db;

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
    
	FOREIGN KEY (UserID) REFERENCES users(ID)
);