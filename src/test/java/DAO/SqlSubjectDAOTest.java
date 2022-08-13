package DAO;

import Model.Subject;
import org.junit.jupiter.api.*;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Expects Users, Lecturers and Subjects tables to be empty.
 */
public class SqlSubjectDAOTest {
    static ConnectionPool pool;
    static SqlSubjectDAO sqlSubjectDAO;
    static int ID;

    @BeforeAll
    public static void runOnce() throws FileNotFoundException {
        pool = new ConnectionPool(1, true);
        ID = prepareTablesForAddingSubject();
        sqlSubjectDAO = new SqlSubjectDAO(pool);
    }

    public static int prepareTablesForAddingSubject() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        String statement = "insert into USERS (email, passwordhash, privilege) values ('gmail@gmail.com', 'totallyhashedpassword', 'Lecturer')";
        PreparedStatement ps;
        int ID = 0;
        try {
            ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            int result = ps.executeUpdate();
            ID = -1;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            ID = rs.getInt(1);

            assertEquals(1, result);

            statement = "insert into LECTURERS (userid, firstname, lastname, profession, gender, dateofbirth, address, lecturerstatus, phonenumber)" +
                    " values (?, 'a', 'b', 'c', 'Male', '2000-01-01', 'a', 'Active', '2' )";
            ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            /** Here an error is occurring (For some reason I don't know), but it doesn't stop program for running. */
            ps.setInt((int)1, ID);
            result = ps.executeUpdate();
            assertEquals(1, result);
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            ID = keys.getInt(1);

            pool.releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            pool.releaseConnection(conn);
        }
        return ID;
    }

    @Test
    public void SubjectDAOTest1(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Math", 6, ID);
        int res = sqlSubjectDAO.addSubject(subject);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Math"), res);

        Subject subject2 = new Subject("Math2", 6, ID);
        res = sqlSubjectDAO.addSubject(subject2);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Math2"), res);
    }

    @Test
    public void SubjectDAOTest2(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 1", 6, ID);
        int res = sqlSubjectDAO.addSubject(subject);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 1"), res);

        Subject subject2 = new Subject("Computer Science 2", 6, ID);
        res = sqlSubjectDAO.addSubject(subject2);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 2"), res);

        assertTrue(sqlSubjectDAO.removeSubject("Computer Science 1"));
        assertFalse(sqlSubjectDAO.removeSubject("Computer Science 1"));
        assertTrue(sqlSubjectDAO.removeSubject("Computer Science 2"));
        assertFalse(sqlSubjectDAO.removeSubject("Computer Science 2"));
    }

    @Test
    public void SubjectDAOTest3(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 3", 6, ID);
        int res =  sqlSubjectDAO.addSubject(subject);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 3"), res);

        Subject returned = sqlSubjectDAO.getSubjectByName("computer science 3");
        assertEquals(subject.getName(), returned.getName());
        assertEquals(subject.getNumCredits(), returned.getNumCredits());

        assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
        assertEquals(null, sqlSubjectDAO.getSubjectByName("computer science 3"));
    }

    @Test
    public void SubjectDAOTest4(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 3", 6, ID);
        int res = sqlSubjectDAO.addSubject(subject);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 3"), res);

        Subject subject2 = new Subject("Computer Science 4", 6, ID);
        res = sqlSubjectDAO.addSubject(subject2);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 4"), res);

        Subject subject3 = new Subject("Computer Science 5", 6, ID);
        res =  sqlSubjectDAO.addSubject(subject3);
        assertEquals(sqlSubjectDAO.getSubjectIDByName("Computer Science 5"), res);

        assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
        assertTrue(sqlSubjectDAO.removeSubject("computer science 4"));
        assertTrue(sqlSubjectDAO.removeSubject("computer science 5"));
    }

    @Test
    public void SubjectDAOTest5(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 1", 6, ID);
        sqlSubjectDAO.addSubject(subject);

        Subject subject2 = new Subject("Computer Science 2", 6, ID);
        sqlSubjectDAO.addSubject(subject2);

        Subject subject3 = new Subject("Computer Science 3", 6, ID);

        List<Subject> result = sqlSubjectDAO.getAllSubjects();
        assertTrue(result.contains(subject));
        assertTrue(result.contains(subject2));
        assertFalse(result.contains(subject3));
    }


}
