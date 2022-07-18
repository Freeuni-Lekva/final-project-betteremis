package Tests;

import DAO.ConnectionPool;
import DAO.SubjectDAO;
import DAO.UserDAO;
import Model.USERTYPE;
import Model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Expects Users table to be empty.
 */
public class UserDAOTest {
    ConnectionPool pool;
    UserDAO userDAO;
    @Before
    public void setUp(){
        pool = new ConnectionPool(1);
        userDAO = new UserDAO(pool);
    }

    @Test
    public void UserDAOTest1(){
        User user = new User("gmail@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        assertTrue(userDAO.addUser(user));
        assertEquals(null, userDAO.getUser("gmail@gmail.com", "whut?"));
        assertEquals(null, userDAO.getUser("gmail?@gmail.com", "totallyhashedpassword"));
        User returned = userDAO.getUser("gmail@gmail.com", "totallyhashedpassword");
        assertEquals(user.getEmail(), returned.getEmail());
        assertEquals(user.getPasswordHash(), returned.getPasswordHash());
        assertEquals(user.getType(), returned.getType());
        assertFalse(userDAO.addUser(user));
    }

    @Test
    public void UserDAOTest2(){
        User user = new User("gmail@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        assertTrue(userDAO.addUser(user));
        User returned = userDAO.getUser("gmail@gmail.com", "totallyhashedpassword");
        assertEquals(user.getEmail(), returned.getEmail());
        assertEquals(user.getPasswordHash(), returned.getPasswordHash());
        assertEquals(user.getType(), returned.getType());
        assertTrue(userDAO.removeUser(user));
        assertFalse(userDAO.removeUser(user));
    }


}
