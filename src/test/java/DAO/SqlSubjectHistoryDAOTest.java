package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlSubjectHistoryDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static SubjectDAO subjectDAO;
    static SubjectHistoryDAO subjectHistoryDAO;
    static LecturerDAO lecturerDAO;
    int ID, ID2;
    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        userDAO = new SqlUserDAO(pool);
        studentDAO = new SqlStudentDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        subjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        lecturerDAO = new SqlLecturerDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        TestingUtils.emptyTables(conn);
        pool.releaseConnection(conn);
        User u = new User("gmail@gmail.com", "passhash", USERTYPE.STUDENT);
        ID = userDAO.addUser(u);

        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        studentDAO.addStudent(st);

        u = new User("gmail2@gmail.com", "passhash", USERTYPE.STUDENT);
        ID2 = userDAO.addUser(u);

        Student st2 = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "a", "a", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID2);
        studentDAO.addStudent(st2);

        Lecturer lecturer = new Lecturer("totallylecturer@gmail.com","totallyhashedpassword",USERTYPE.LECTURER,1,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        int added = lecturerDAO.addLecturer(lecturer);

        Subject subject = new Subject("Computer Science 3", 6, added);
        subjectDAO.addSubject(subject);

        Subject subject2 = new Subject("Computer Science 4", 6, added);
        subjectDAO.addSubject(subject2);

        Subject subject3 = new Subject("Computer Science 5", 6, added);
        subjectDAO.addSubject(subject3);

    }

    @Test
    public void SqlSubjectHistoryDAOTest1(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, ID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        try{
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            ResultSet rs = ps.executeQuery();
            rs.next();
            assertEquals(st.getUserID(), rs.getInt(2));
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void SqlSubjectHistoryDAOTest2(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 4", 6, ID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        try{
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            ResultSet rs = ps.executeQuery();
            rs.next();
            assertEquals(st.getUserID(), rs.getInt(2));
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void SqlSubjectHistoryDAOTest3(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 5", 6, ID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        try{
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            ResultSet rs = ps.executeQuery();
            rs.next();
            assertEquals(st.getUserID(), rs.getInt(2));
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void SqlSubjectHistoryDAOTest5(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 6", 6, ID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        assertEquals(-1, index);
    }

    @Test
    public void SqlSubjectHistoryDAOTest6(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, ID);
        Subject subject2 = new Subject("Computer Science 4", 6, ID);
        Subject subject3 = new Subject("Computer Science 5", 6, ID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);
        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getAllSubjects(st);
        boolean first = false, second = false, third = false;
        for(ArrayList<Subject> s : map.values()){
            for(Subject sb : s) {
                if (sb.getName().equals("Computer Science 3"))
                    first = true;
                if (sb.getName().equals("Computer Science 4"))
                    second = true;
                if (sb.getName().equals("Computer Science 5"))
                    third = true;
            }
        }
        assertTrue(first && second && third);
        assertEquals(null, map.get(2));
        assertEquals(null, map.get(3));
    }

    @Test
    public void SqlSubjectHistoryDAOTest7(){
        Student st = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, ID);
        Subject subject2 = new Subject("Computer Science 4", 6, ID);
        Subject subject3 = new Subject("Computer Science 5", 6, ID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);
        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getAllSubjects(st);
        boolean first = false, second = false, third = false;
        for(ArrayList<Subject> s : map.values()){
            for(Subject sb : s) {
                if (sb.getName().equals("Computer Science 3"))
                    first = true;
                if (sb.getName().equals("Computer Science 4"))
                    second = true;
                if (sb.getName().equals("Computer Science 5"))
                    third = true;
            }
        }
        assertTrue(first && second && third);
        assertEquals(null, map.get(1));
        assertEquals(null, map.get(3));
    }

    @Test
    public void SqlSubjectHistoryDAOTest8(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, ID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        try{
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            ResultSet rs = ps.executeQuery();
            rs.next();
            assertEquals(0, rs.getInt(5));
            pool.releaseConnection(conn);
            subjectHistoryDAO.updateStudentGrade(st, subject, 1);
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            rs = ps.executeQuery();
            rs.next();
            assertEquals(1, rs.getInt(5));
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
