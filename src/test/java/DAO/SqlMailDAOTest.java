package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlMailDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static SubjectDAO subjectDAO;
    static SubjectHistoryDAO subjectHistoryDAO;
    static LecturerDAO lecturerDAO;
    static MailDAO mailDAO;
    int ID, ID2, LecID;

    Student st;
    Student st2;

    User u;
    User u2;
    User u3;

    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        userDAO = new SqlUserDAO(pool);
        studentDAO = new SqlStudentDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        subjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        lecturerDAO = new SqlLecturerDAO(pool);
        mailDAO = new SqlMailDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        TestingUtils.emptyTables(conn);
        pool.releaseConnection(conn);
        u = new User("gmail@gmail.com", "passhash", USERTYPE.STUDENT);
        ID = userDAO.addUser(u);

        st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        studentDAO.addStudent(st);

        u2 = new User("gmail2@gmail.com", "passhash", USERTYPE.STUDENT);
        ID2 = userDAO.addUser(u2);

        st2 = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "a", "a", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID2);
        studentDAO.addStudent(st2);

        u3 = new User("totallylecturer@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        int added = userDAO.addUser(u3);

        Lecturer lecturer = new Lecturer("totallylecturer@gmail.com","totallyhashedpassword",USERTYPE.LECTURER,added,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        LecID = lecturerDAO.addLecturer(lecturer);

        Subject subject = new Subject("Computer Science 3", 6, LecID);
        subjectDAO.addSubject(subject);

        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        subjectDAO.addSubject(subject2);

        Subject subject3 = new Subject("Computer Science 5", 6, LecID);
        subjectDAO.addSubject(subject3);
    }

    @Test
    public void SqlMailDAOTest1(){
        assertTrue(mailDAO.addMail(u, u2, "gamarjoba"));
        assertTrue(mailDAO.addMail(u2, u, "gagimarjos"));
        assertTrue(mailDAO.addMail(u, u2, "rogor xar?"));
        assertTrue(mailDAO.addMail(u2, u, "kargad shen?"));
        assertTrue(mailDAO.addMail(u, u2, "mec kargad"));
    }

    @Test
    public void SqlMailDAOTest2(){
        assertTrue(mailDAO.addMail(u, u2, "gamarjoba"));
        assertTrue(mailDAO.addMail(u2, u, "gagimarjos"));
        assertTrue(mailDAO.addMail(u, u2, "rogor xar?"));
        assertTrue(mailDAO.addMail(u2, u, "kargad shen?"));
        assertTrue(mailDAO.addMail(u, u2, "mec kargad"));

        List<String> result = mailDAO.getAllMails(u, u2, true);
        assertEquals(5, result.size());
        assertTrue(result.contains("gamarjoba"));
        assertTrue(result.contains("gagimarjos"));
        assertTrue(result.contains("rogor xar?"));
        assertTrue(result.contains("kargad shen?"));
        assertTrue(result.contains("mec kargad"));
    }

    @Test
    public void SqlMailDAOTest3(){
        assertTrue(mailDAO.addMail(u, u2, "gamarjoba"));
        assertTrue(mailDAO.addMail(u2, u, "gagimarjos"));
        assertTrue(mailDAO.addMail(u, u2, "rogor xar?"));
        assertTrue(mailDAO.addMail(u2, u, "kargad shen?"));
        assertTrue(mailDAO.addMail(u, u2, "mec kargad"));

        List<String> result = mailDAO.getAllMails(u, u2, true);
        assertEquals(5, result.size());
        assertTrue(result.contains("gamarjoba"));
        assertTrue(result.contains("gagimarjos"));
        assertTrue(result.contains("rogor xar?"));
        assertTrue(result.contains("kargad shen?"));
        assertTrue(result.contains("mec kargad"));

        assertTrue(mailDAO.deleteAllMails(u, u2) == 5);
        result = mailDAO.getAllMails(u, u2, true);
        assertEquals(0, result.size());

    }

}
