package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SqlStudentClassroomDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static SubjectDAO subjectDAO;
    static SubjectHistoryDAO subjectHistoryDAO;
    static LecturerDAO lecturerDAO;
    static ClassroomPostsDAO classroomPostsDAO;
    static StudentClassroomDAO studentClassroomDAO;
    int SUID1, SUID2, ID2, LecID, Subj1, Subj2, ClassroomID;
    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        userDAO = new SqlUserDAO(pool);
        studentDAO = new SqlStudentDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        subjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        lecturerDAO = new SqlLecturerDAO(pool);
        classroomPostsDAO = new SqlClassroomPostsDAO(pool);
        studentClassroomDAO = new SqlStudentClassroomDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
        User u = new User("gmail@gmail.com", "passhash", USERTYPE.STUDENT);
        SUID1 = userDAO.addUser(u);

        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", SUID1);
        studentDAO.addStudent(st);

        User u2 = new User("gmail2@gmail.com", "passhash", USERTYPE.STUDENT);
        SUID2 = userDAO.addUser(u2);

        Student st2 = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", SUID2);
        studentDAO.addStudent(st2);


        u = new User("totallylecturer@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        ID2 = userDAO.addUser(u);

        Lecturer lecturer = new Lecturer("totallylecturer@gmail.com","totallyhashedpassword",USERTYPE.LECTURER,ID2,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        LecID = lecturerDAO.addLecturer(lecturer);

        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subj1 = subjectDAO.addSubject(subject);

        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subj2 = subjectDAO.addSubject(subject2);

        Connection connection = pool.getConnection();
        try{
            String statement = "INSERT INTO CLASSROOMS (SubjectID, Semester, LecturerID) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Subj1);
            ps.setInt(2, 1);
            ps.setInt(3, LecID);
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                ClassroomID = keys.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            pool.releaseConnection(connection);
        }
    }

    @Test
    public void SqlClassroomPostsDAOTest1(){
        assertTrue(-1 != studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
        assertEquals(-1, studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
        assertEquals(-1, studentClassroomDAO.addStudentAndClassroom("totallylecturer@gmail.com", ClassroomID));
    }

    @Test
    public void SqlClassroomPostsDAOTest2(){
        assertTrue(-1 != studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
        assertTrue(studentClassroomDAO.removeStudentAndClassroom( "gmail@gmail.com", ClassroomID));
        assertFalse(studentClassroomDAO.removeStudentAndClassroom( "gmail@gmail.com", ClassroomID));
        assertTrue(-1 != studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
        assertEquals(-1, studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
    }

    @Test
    public void SqlClassroomPostsDAOTest3(){
        assertTrue(-1 != studentClassroomDAO.addStudentAndClassroom("gmail@gmail.com", ClassroomID));
        assertTrue(-1 != studentClassroomDAO.addStudentAndClassroom("gmail2@gmail.com", ClassroomID));
        List<Student> result = studentClassroomDAO.getStudentsInClassroom(ClassroomID);
        assertTrue(result.size() == 2);
        List<String> mapped = result.stream().map(s -> s.getEmail()).collect(Collectors.toList());
        assertTrue(mapped.contains("gmail@gmail.com"));
        assertTrue(mapped.contains("gmail2@gmail.com"));
    }
}
