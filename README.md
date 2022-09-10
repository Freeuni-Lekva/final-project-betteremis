# BetterEmis
Platform for students to easily see their grades and completed courses, register on different courses and interact with 
other students/lecturers either by public/private chat, or by using classrooms. Also useful for lecturers as they can manage their students'
grades easily.

## Requirements
- Tomcat 9.0.64
- JDK 17+
- Maven 3.3.2 
- Microsoft SQL Server <br> For more detailed information about the versions of different libraries, 
see [pom.xml](https://github.com/Freeuni-Lekva/final-project-betteremis/blob/master/pom.xml) file.

## Compile and run
- Compile SQL scripts first.
- Change database creditentials [here](https://github.com/Freeuni-Lekva/final-project-betteremis/blob/master/src/main/java/DAO/DatabaseInfo.java)
- Add [pom.xml](https://github.com/Freeuni-Lekva/final-project-betteremis/blob/master/pom.xml) dependencies in your project.

## Functionality
There are three different types of users: Students, Lecturers and Admins.
### Admin
- Can add/remove subjects, or change the prerequisites for the subject.
- Can see and ban/unban any user.
- Can open/close registration, which means that students will/won't be able to register on subjects.
- Can start/end new semester.

### Student
- Able to see his grades and completed/incompleted list of subjects.
- Can have public/private chats with other students and lecturers.
- Can add/remove friends and see list of friends and friend requests.
- Can register/unregister on different courses if they are available and if the registration is open.

### Lecturer
- Able to chat with students/lecturers publicly and privately, and also via classroom.
- Able to change grades for his/her students (This feature is very flexible for lecturers, you can upload a file containing all the grades and the website does 
the work for you automatically).
- Can add/remove friends and see list of friends and friend requests.
