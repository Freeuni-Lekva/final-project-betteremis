package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.*;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlPrerequisitesDAOTest {
    static ConnectionPool pool;
    static Subject Subject;
    static Subject Subject1;
    static Subject Subject2;
    static Subject Subject3;
    static SqlPrerequisitesDAO sqlPrerequisitesDAO;
    static Student student;
    static SubjectDAO SubjectDAO;
    static User user;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static LecturerDAO leqturerDAO;
    static Lecturer userLeqturer;
    static SubjectHistoryDAO subjectHistory;
    @BeforeAll
    static void initAll() {
        pool = new ConnectionPool(10, true);
        try {
            SqlScriptRunner.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        student = new Student("nitsim20","gela",USERTYPE.STUDENT,"Nicolas","Tsima","MACS",5,GENDER.MALE,Date.valueOf("2002-08-06"),"GLdani",STATUS.ACTIVE,"MACS",69,3.69, new BigInteger("569696969"),"206",1);
        userDAO=new SqlUserDAO(pool);
        SubjectDAO=new SqlSubjectDAO(pool);
        studentDAO=new SqlStudentDAO(pool);
        user=new User("nitsim20","gela",USERTYPE.STUDENT);
        userDAO.addUser(user);
        studentDAO.addStudent(student);
        leqturerDAO=new SqlLecturerDAO(pool);
        userLeqturer = new Lecturer("user1.getEmail()","user2.getPasswordHash()",USERTYPE.LECTURER,1,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        userDAO.addUser(userLeqturer);
        subjectHistory=new SqlSubjectHistoryDAO(pool);
        leqturerDAO.addLecturer(userLeqturer);
        Subject = new Subject("AssMath", 6, 1);;
        Subject1 = new Subject("LinearMath", 6, 1);;
        Subject2 = new Subject("GayMath", 6, 1);;
        Subject3 = new Subject("StraightMath", 6, 1);;
        sqlPrerequisitesDAO=new SqlPrerequisitesDAO(pool);
        assertEquals(1,SubjectDAO.addSubject(Subject));
        assertEquals(2, SubjectDAO.addSubject(Subject1));
        assertEquals(3,SubjectDAO.addSubject(Subject2));
        assertEquals(4, SubjectDAO.addSubject(Subject3));
    }
    @Test
    @Order(1)
    public void testPrerequisites1() {
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "LinearMath");
        assertEquals("LinearMath", sqlPrerequisitesDAO.getSubjectPrerequisitesByName("AssMath"));
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "GayMath");
        assertEquals("GayMath", sqlPrerequisitesDAO.getSubjectPrerequisitesByName("AssMath"));
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "(GayMath|LinearMath)");
        assertEquals("(GayMath|LinearMath)", sqlPrerequisitesDAO.getSubjectPrerequisitesByName("AssMath"));
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "(GayMath|LinearMath)&(StraightMath)");
        assertEquals("(GayMath|LinearMath)&(StraightMath)", sqlPrerequisitesDAO.getSubjectPrerequisitesByName("AssMath"));
    }
    @Test
    @Order(2)
    public void testPrerequisites2() {
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "LinearMath");

        subjectHistory.addStudentAndSubject(student,Subject1);
        subjectHistory.updateCompletedColumn(student,Subject1,true);
        assertEquals(true, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(),Subject.getName()));
        subjectHistory.removeStudentAndSubject(student,Subject1);
        subjectHistory.updateCompletedColumn(student,Subject1,false);
        assertEquals(false, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(),Subject.getName()));
        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "");
        assertEquals(true, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(),Subject.getName()));

        sqlPrerequisitesDAO.updatePrerequisite("AssMath", "(GayMath|LinearMath)&(StraightMath)");
        assertEquals(false, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(), Subject.getName()));
        subjectHistory.addStudentAndSubject(student, Subject2);
        subjectHistory.updateCompletedColumn(student,Subject2,true);
        subjectHistory.addStudentAndSubject(student, Subject3);
        subjectHistory.updateCompletedColumn(student,Subject3,true);
        assertEquals(true, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(), Subject.getName()));
        subjectHistory.removeStudentAndSubject(student, Subject2);
        subjectHistory.updateCompletedColumn(student,Subject2,false);
        assertEquals(false, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(), Subject.getName()));
        subjectHistory.addStudentAndSubject(student, Subject1);
        subjectHistory.updateCompletedColumn(student,Subject1,true);
        assertEquals(true, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(), Subject.getName()));
        subjectHistory.removeStudentAndSubject(student, Subject3);
        subjectHistory.updateCompletedColumn(student,Subject3,false);
        subjectHistory.addStudentAndSubject(student, Subject2);
        subjectHistory.updateCompletedColumn(student,Subject2,true);
        assertEquals(false, sqlPrerequisitesDAO.canThisSubjectChosenByStudent(student.getEmail(), Subject.getName()));
    }
}
