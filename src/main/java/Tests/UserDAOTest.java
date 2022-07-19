package Tests;

import DAO.ConnectionPool;
import DAO.SubjectDAO;
import DAO.UserDAO;
import Model.USERTYPE;
import Model.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;


/**
 * Expects Users table to be empty.
 */
public class UserDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    @BeforeClass
    public static void setUp(){
        pool = new ConnectionPool(1);
        userDAO = new UserDAO(pool);
    }

    public int getID(String email){
        Connection conn = pool.getConnection();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?;");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                pool.releaseConnection(conn);
                return rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        pool.releaseConnection(conn);
        return -1;
    }

    @Test
    public void UserDAOTest1(){
        User user = new User("gmail@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        int ID = userDAO.addUser(user);
        assertEquals(getID("gmail@gmail.com"), ID);
        assertEquals(null, userDAO.getUser("gmail@gmail.com", "whut?"));
        assertEquals(null, userDAO.getUser("gmail?@gmail.com", "totallyhashedpassword"));
        User returned = userDAO.getUser("gmail@gmail.com", "totallyhashedpassword");
        assertEquals(user.getEmail(), returned.getEmail());
        assertEquals(user.getPasswordHash(), returned.getPasswordHash());
        assertEquals(user.getType(), returned.getType());
        ID = userDAO.addUser(user);
        assertEquals(-1, ID);
    }

    @Test
    public void UserDAOTest2(){
        User user = new User("gmail1@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        int ID = userDAO.addUser(user);
        assertEquals(getID("gmail1@gmail.com"), ID);
        User returned = userDAO.getUser("gmail1@gmail.com", "totallyhashedpassword");
        assertEquals(user.getEmail(), returned.getEmail());
        assertEquals(user.getPasswordHash(), returned.getPasswordHash());
        assertEquals(user.getType(), returned.getType());
        assertTrue(userDAO.removeUser(user));
        assertFalse(userDAO.removeUser(user));
    }


}
