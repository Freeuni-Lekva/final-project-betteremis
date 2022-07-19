package java.dao;

import DAO.ConnectionPool;
import DAO.SqlSubjectDAO;
import Model.Subject;
import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Expects Users, Lecturers and Subjects tables to be empty.
 */
public class SqlSubjectDAOTest {
    static ConnectionPool pool;
    static SqlSubjectDAO sqlSubjectDAO;
    static int ID;

    @BeforeClass
    public static void runOnce(){
        pool = new ConnectionPool(1);
        ID = prepareTablesForAddingSubject();
        sqlSubjectDAO = new SqlSubjectDAO(pool);
    }

    public static int prepareTablesForAddingSubject(){
        Connection conn = pool.getConnection();
        String statement = "insert into users (email, passwordhash, privilege) values ('gmail@gmail.com', 'totallyhashedpassword', 'Lecturer')";
        PreparedStatement ps;
        int ID = 0;
        try {
            ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            int result = ps.executeUpdate();
            ID = -1;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            ID = rs.getInt(1);

            Assert.assertEquals(1, result);

            statement = "insert into lecturers (userid, firstname, lastname, profession, gender, dateofbirth, address, lecturerstatus, phonenumber)" +
                    " values (?, 'a', 'b', 'c', 'Male', '2000-01-01', 'a', 'Active', '2' )";
            ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            /** Here an error is occurring (For some reason I don't know), but it doesn't stop program for running. */
            ps.setInt(1, ID);
            result = ps.executeUpdate();
            Assert.assertEquals(1, result);
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
        Subject subject = new Subject("Math", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject));

        Subject subject2 = new Subject("Math2", 6, 2, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject2));
    }

    @Test
    public void SubjectDAOTest2(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 1", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject));

        Subject subject2 = new Subject("Computer Science 2", 6, 2, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject2));

        Assert.assertTrue(sqlSubjectDAO.removeSubject("Computer Science 1"));
        Assert.assertFalse(sqlSubjectDAO.removeSubject("Computer Science 1"));
        Assert.assertTrue(sqlSubjectDAO.removeSubject("Computer Science 2"));
        Assert.assertFalse(sqlSubjectDAO.removeSubject("Computer Science 2"));
    }

    @Test
    public void SubjectDAOTest3(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 3", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject));

        Subject returned = sqlSubjectDAO.getSubjectByName("computer science 3");
        Assert.assertEquals(subject.getName(), returned.getName());
        Assert.assertEquals(subject.getNumCredits(), returned.getNumCredits());
        Assert.assertEquals(subject.getSemester(), returned.getSemester());

        Assert.assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
        Assert.assertEquals(null, sqlSubjectDAO.getSubjectByName("computer science 3"));
    }

    @Test
    public void SubjectDAOTest4(){
        sqlSubjectDAO.removeAll();
        Subject subject = new Subject("Computer Science 3", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject));

        Subject subject2 = new Subject("Computer Science 4", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject2));

        Subject subject3 = new Subject("Computer Science 5", 6, 1, ID);
        Assert.assertTrue(sqlSubjectDAO.addSubject(subject3));

        Assert.assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
        Assert.assertTrue(sqlSubjectDAO.removeSubject("computer science 4"));
        Assert.assertTrue(sqlSubjectDAO.removeSubject("computer science 5"));
    }



}
