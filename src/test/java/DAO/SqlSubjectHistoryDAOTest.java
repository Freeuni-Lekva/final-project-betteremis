package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.*;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SqlSubjectHistoryDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static SubjectDAO subjectDAO;
    static SubjectHistoryDAO subjectHistoryDAO;
    static LecturerDAO lecturerDAO;
    int ID, ID2, LecID;
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
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
        User u = new User("gmail@gmail.com", "passhash", USERTYPE.STUDENT);
        ID = userDAO.addUser(u);

        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        studentDAO.addStudent(st);

        u = new User("gmail2@gmail.com", "passhash", USERTYPE.STUDENT);
        ID2 = userDAO.addUser(u);

        Student st2 = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "a", "a", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID2);
        studentDAO.addStudent(st2);

        u = new User("totallylecturer@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        int added = userDAO.addUser(u);


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
    public void SqlSubjectHistoryDAOTest1(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
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
        Subject subject = new Subject("Computer Science 4", 6, LecID);
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
        Subject subject = new Subject("Computer Science 5", 6, LecID);
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
        Subject subject = new Subject("Computer Science 6", 6, LecID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        assertEquals(-1, index);
    }

    @Test
    public void SqlSubjectHistoryDAOTest6(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);
        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getAllSubjects(st, 0);
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
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);
        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getAllSubjects(st, 0);
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
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        int index = subjectHistoryDAO.addStudentAndSubject(st, subject);
        try{
            Connection conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            ResultSet rs = ps.executeQuery();
            rs.next();
            assertEquals(0, rs.getInt(5));
            assertEquals(0, rs.getInt(6));
            assertEquals(0, rs.getInt(7));
            assertEquals(0, rs.getInt(8));
            assertEquals(0, rs.getInt(9));
            assertEquals(0, rs.getInt(10));
            assertEquals(0, rs.getInt(11));
            pool.releaseConnection(conn);
            subjectHistoryDAO.updateStudentQuiz(st, subject, 1);
            subjectHistoryDAO.updateStudentHomework(st, subject, 1);
            subjectHistoryDAO.updateStudentProject(st, subject, 1);
            subjectHistoryDAO.updateStudentPresentation(st, subject, 1);
            subjectHistoryDAO.updateStudentMidterm(st, subject, 1);
            subjectHistoryDAO.updateStudentFinal(st, subject, 1);
            subjectHistoryDAO.updateStudentFX(st, subject, -1);
            conn = pool.getConnection();
            ps = conn.prepareStatement("SELECT * FROM SUBJECTS_HISTORY WHERE ID = ?");
            ps.setInt(1, index);
            rs = ps.executeQuery();
            rs.next();
            assertEquals(1, rs.getInt(5));
            assertEquals(1, rs.getInt(6));
            assertEquals(1, rs.getInt(7));
            assertEquals(1, rs.getInt(8));
            assertEquals(1, rs.getInt(9));
            assertEquals(1, rs.getInt(10));
            assertEquals(-1, rs.getInt(11));
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void SqlSubjectHistoryDAOTest9(){
        Student st = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);
        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getIncompleteSubjects(st);
        boolean first = false, second = false, third = false;
        for(ArrayList<Subject> s : map.values()){
            for(Subject sb : s) {
                assertFalse(subjectHistoryDAO.isCompleted(st, sb));
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
    public void SqlSubjectHistoryDAOTest10(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 5", 6, LecID);
        subjectHistoryDAO.addStudentAndSubject(st, subject);
        assertEquals(0, subjectHistoryDAO.getSumOfScores(st, subject));
        Map<String, Double> map = subjectHistoryDAO.getGrade(st, subject);
        assertEquals(0, map.get(Mapping.QUIZ));
        assertEquals(0, map.get(Mapping.HOMEWORK));
        assertEquals(0, map.get(Mapping.PROJECT));
        assertEquals(0, map.get(Mapping.PRESENTATION));
        assertEquals(0, map.get(Mapping.MIDTERM));
        assertEquals(0, map.get(Mapping.FINAL));
        assertEquals(0, map.get(Mapping.FX));
        subjectHistoryDAO.updateStudentQuiz(st, subject, 1);
        subjectHistoryDAO.updateStudentHomework(st, subject, 1);
        subjectHistoryDAO.updateStudentProject(st, subject, 1);
        subjectHistoryDAO.updateStudentPresentation(st, subject, 1);
        subjectHistoryDAO.updateStudentMidterm(st, subject, 1);
        subjectHistoryDAO.updateStudentFinal(st, subject, 1);
        subjectHistoryDAO.updateStudentFX(st, subject, -1);
        map = subjectHistoryDAO.getGrade(st, subject);
        assertEquals(1, map.get(Mapping.QUIZ));
        assertEquals(1, map.get(Mapping.HOMEWORK));
        assertEquals(1, map.get(Mapping.PROJECT));
        assertEquals(1, map.get(Mapping.PRESENTATION));
        assertEquals(1, map.get(Mapping.MIDTERM));
        assertEquals(1, map.get(Mapping.FINAL));
        assertEquals(-1, map.get(Mapping.FX));
        assertEquals(6, subjectHistoryDAO.getSumOfScores(st, subject));
        subjectHistoryDAO.updateStudentQuiz(st, subject, -1);
        subjectHistoryDAO.updateStudentFX(st, subject, 1);
        assertEquals(6, subjectHistoryDAO.getSumOfScores(st, subject));
        subjectHistoryDAO.updateStudentQuiz(st, subject, -1);
        subjectHistoryDAO.updateStudentFX(st, subject, -1);
        assertEquals(5, subjectHistoryDAO.getSumOfScores(st, subject));
    }

    @Test
    public void SqlSubjectHistoryDAOTest11(){
        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 5", 6, LecID);
        subjectHistoryDAO.addStudentAndSubject(st, subject);
        assertFalse(subjectHistoryDAO.isCompleted(st, subject));
        assertTrue(subjectHistoryDAO.updateCompletedColumn(st, subject, true));
        assertTrue(subjectHistoryDAO.isCompleted(st, subject));
        assertTrue(subjectHistoryDAO.updateCompletedColumn(st, subject, false));
        assertFalse(subjectHistoryDAO.isCompleted(st, subject));
    }

    @Test
    public void SqlSubjectHistoryDAOTest12(){
        Student st = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);

        subjectHistoryDAO.addStudentAndSubject(st, subject);
        subjectHistoryDAO.addStudentAndSubject(st, subject2);
        subjectHistoryDAO.addStudentAndSubject(st, subject3);

        subjectHistoryDAO.updateCompletedColumn(st, subject, true);

        Map<Integer, ArrayList<Subject>> map = subjectHistoryDAO.getCompletedSubjects(st);
        boolean first = false, second = false, third = false;
        for(ArrayList<Subject> s : map.values()){
            for(Subject sb : s) {
                assertTrue(subjectHistoryDAO.isCompleted(st, sb));
                if (sb.getName().equals("Computer Science 3"))
                    first = true;
                if (sb.getName().equals("Computer Science 4"))
                    second = true;
                if (sb.getName().equals("Computer Science 5"))
                    third = true;
            }
        }
        assertTrue(first && !second && !third);
        assertEquals(null, map.get(1));
        assertEquals(null, map.get(3));
    }

    @Test
    public void SqlSubjectHistoryDAOTest13(){
        Student st = new Student("gmail2@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);

        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject2) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject3) != -1);

        assertTrue(subjectHistoryDAO.removeStudentAndSubject(st, subject));
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject) != -1);
        assertTrue(subjectHistoryDAO.removeStudentAndSubject(st, subject));
        assertTrue(subjectHistoryDAO.removeStudentAndSubject(st, subject2));
        assertTrue(subjectHistoryDAO.removeStudentAndSubject(st, subject3));
    }

    @Test
    public void SqlSubjectHistoryDAOTest14(){
        User u = new User("gmail21@gmail.com", "passhash", USERTYPE.STUDENT);
        int newID = userDAO.addUser(u);
        Student st = new Student("gmail21@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);
        u = new User("gmail22@gmail.com", "passhash", USERTYPE.STUDENT);
        newID = userDAO.addUser(u);
        Student st2 = new Student("gmail22@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st2);

        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject2) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject3) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject2) != -1);

        Map<Integer, ArrayList<Student>> map1 = subjectHistoryDAO.getAllStudentsOfSubject(subject.getName());
        Map<Integer, ArrayList<Student>> map2 = subjectHistoryDAO.getAllStudentsOfSubject(subject2.getName());
        Map<Integer, ArrayList<Student>> map3 = subjectHistoryDAO.getAllStudentsOfSubject(subject3.getName());
        assertEquals(2, map1.keySet().size());
        assertTrue(map1.get(1) != null);
        assertTrue(map1.get(2) != null);
        assertEquals(1, map1.get(1).size());
        assertEquals(1, map1.get(2).size());
        assertEquals(2, map2.keySet().size());
        assertTrue(map2.get(1) != null);
        assertTrue(map2.get(2) != null);
        assertEquals(1, map2.get(1).size());
        assertEquals(1, map2.get(2).size());
        assertEquals(1, map3.keySet().size());
        assertTrue(map3.get(1) != null);
        assertTrue(map3.get(2) == null);
        assertEquals(1, map3.get(1).size());
    }

    @Test
    public void SqlSubjectHistoryDAOTest15(){
        User u = new User("gmail21@gmail.com", "passhash", USERTYPE.STUDENT);
        int newID = userDAO.addUser(u);
        Student st = new Student("gmail21@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);
        u = new User("gmail22@gmail.com", "passhash", USERTYPE.STUDENT);
        newID = userDAO.addUser(u);
        Student st2 = new Student("gmail22@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st2);

        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject2) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject3) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject2) != -1);

        assertTrue(subjectHistoryDAO.makeCompleted(1));
        assertTrue(subjectHistoryDAO.isCompleted(st, subject));
        assertTrue(subjectHistoryDAO.isCompleted(st, subject2));
        assertTrue(subjectHistoryDAO.isCompleted(st, subject3));
        assertFalse(subjectHistoryDAO.isCompleted(st2, subject));
        assertFalse(subjectHistoryDAO.isCompleted(st2, subject2));
    }

    @Test
    public void SqlSubjectHistoryDAOTest16(){
        User u = new User("gmail21@gmail.com", "passhash", USERTYPE.STUDENT);
        int newID = userDAO.addUser(u);
        Student st = new Student("gmail21@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st);
        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subject subject3 = new Subject("Computer Science 5", 6, LecID);
        u = new User("gmail22@gmail.com", "passhash", USERTYPE.STUDENT);
        newID = userDAO.addUser(u);
        Student st2 = new Student("gmail22@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 2, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", newID);
        studentDAO.addStudent(st2);

        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject2) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st, subject3) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject) != -1);
        assertTrue(subjectHistoryDAO.addStudentAndSubject(st2, subject2) != -1);

        assertEquals(1, subjectHistoryDAO.getSemester(st, subject));
        assertEquals(1, subjectHistoryDAO.getSemester(st, subject2));
        assertEquals(1, subjectHistoryDAO.getSemester(st, subject3));
        assertEquals(2, subjectHistoryDAO.getSemester(st2, subject));
        assertEquals(2, subjectHistoryDAO.getSemester(st2, subject2));

    }


}
