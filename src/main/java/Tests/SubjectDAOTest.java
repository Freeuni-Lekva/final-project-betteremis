package Tests;

import DAO.ConnectionPool;
import DAO.SubjectDAO;
import Model.Lecturer;
import Model.Subject;
import org.junit.*;

import javax.xml.transform.Result;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tables need to be cleared for the tests to be relevant.
 */
public class SubjectDAOTest {
    ConnectionPool pool;
    SubjectDAO subjectDAO;
    @Before
    public void setUp(){
        pool = new ConnectionPool(1);
        subjectDAO = new SubjectDAO(pool);
    }

    @Test
    public void SubjectDAOTest1(){
        Connection conn = pool.getConnection();
        try{
            String statement = "insert into users (email, passwordhash, privilege) values ('gmail@gmail.com', 'totallyhashedpassword', 'Lecturer')";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            int result = ps.executeUpdate();
            int ID = -1;
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            ID = rs.getInt(1);

            assertEquals(1, result);

            statement = "insert into lecturers (userid, firstname, lastname, profession, gender, dateofbirth, address, lecturerstatus, phonenumber)" +
                    " values (?, 'a', 'b', 'c', 'Male', '2000-01-01', 'a', 'Active', '2' )";
            ps = conn.prepareStatement(statement);
            /** Here first parameter is identified as error (For some reason I don't know), but it doesn't stop program for running. */
            ps.setInt(1, ID);
            result = ps.executeUpdate();
            assertEquals(1, result);

            pool.releaseConnection(conn);

            Subject subject = new Subject("Math", 6, 1);
            assertTrue(subjectDAO.addSubject(subject, ID));

            Subject subject2 = new Subject("Math2", 6, 2);
            assertTrue(subjectDAO.addSubject(subject2, ID));


        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
