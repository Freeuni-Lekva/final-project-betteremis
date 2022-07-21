package DAO;
import DAO.Interfaces.UserDAO;
import Model.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import static Model.USERTYPE.*;

public class SqlUserDAOTest extends TestCase {

    /**
     * User objects that will be used in the tests.
     */
    private User student, student2, lecturer, admin;

    // UserDAO object
    private UserDAO userDAO;

    /**
     * Creates instances of all the objects needed.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ConnectionPool pool = new ConnectionPool(5);
        userDAO = new SqlUserDAO(pool);
        student = new User("student@gmail.com", BCrypt.withDefaults().hashToString(12, "hash".toCharArray()), STUDENT); //hash
        student2 = new User("student2@gmail.com", BCrypt.withDefaults().hashToString(12, "xAE12$#%".toCharArray()), STUDENT); //xAE12$#%
        lecturer = new User("lecturer@gmail.com", BCrypt.withDefaults().hashToString(12, "dzlierihashi123".toCharArray()), LECTURER); //dzlierihashi123
        admin = new User("admin@gmail.com", BCrypt.withDefaults().hashToString(12, "vergatexav123".toCharArray()), ADMIN); //vergatexav123
        emptyTables(pool.getConnection());
    }

    /**
     * Utility to empty all tables in the database before each test.
     * This makes sure that tests should not interfere with each other.
     */
    private void emptyTables(Connection conn) throws FileNotFoundException {
        ScriptRunner runner = new ScriptRunner(conn);
        // Disable log writer, we don't want to see console full of sql scripts.
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("TableScripts/sql_script.sql"));
        // Run the script!
        runner.runScript(reader);
    }

    // simple add/contains test
    public void testAddAndIsValid(){
        int first = userDAO.addUser(student);
        assertEquals(true, userDAO.isValidUser(student.getEmail(), "hash"));
        assertEquals(false, userDAO.isValidUser(student.getEmail(), "wrongpass"));
        assertEquals(false, userDAO.isValidUser("wrongEmail", "hash"));
        int second = userDAO.addUser(student2);
        int third = userDAO.addUser(lecturer);
        assertEquals(true, userDAO.isValidUser(student2.getEmail(), "xAE12$#%"));
        assertEquals(true, userDAO.isValidUser(lecturer.getEmail(), "dzlierihashi123"));
        assertEquals(false, userDAO.isValidUser(student2.getEmail(), "dzlierihashi123"));
        assertEquals(false, userDAO.isValidUser("admin@gmail.com", "vergatexav123"));
        assertNotSame(-1, first);
        assertNotSame(-1, second);
        assertNotSame(-1, third);
        assertEquals(-1, userDAO.addUser(student));
        assertEquals(-1, userDAO.addUser(student2));
        assertEquals(-1, userDAO.addUser(lecturer));
        assertNotSame(-1, userDAO.addUser(admin));
    }


    public void testGetUserByEmail(){
        userDAO.addUser(student);
        User user = userDAO.getUserByEmail(student.getEmail());

        assertEquals(student.getEmail(), user.getEmail());
        assertEquals(student.getPasswordHash(), user.getPasswordHash());
        assertEquals(student.getType(), user.getType());

        User notFound = userDAO.getUserByEmail(student2.getEmail());
        assertNull(notFound);

        userDAO.addUser(student2);
        userDAO.addUser(lecturer);
        user = userDAO.getUserByEmail(student2.getEmail());
        assertEquals(student2.getEmail(), user.getEmail());
        assertEquals(student2.getPasswordHash(), user.getPasswordHash());
        assertEquals(student2.getType(), user.getType());
        User leqtori = userDAO.getUserByEmail(lecturer.getEmail());
        assertEquals(lecturer.getPasswordHash(), leqtori.getPasswordHash());
        assertEquals(LECTURER, leqtori.getType());
    }

    public void testRemove1(){
        int id = userDAO.addUser(student);
        assertNotSame(-1, id);
        assertTrue(userDAO.removeUser(student));
        assertFalse(userDAO.isValidUser(student.getEmail(), student.getPasswordHash()));
        assertFalse(userDAO.removeUser(student));
        userDAO.addUser(student);
        assertTrue(userDAO.removeUser(student));
    }


    public void testRemove2(){
        userDAO.addUser(student);
        assertNotSame(-1, userDAO.addUser(student2));

        User user = new User(student.getEmail(), student.getPasswordHash(), student.getType());
        int result = userDAO.addUser(user);
        assertEquals(-1, result);
        boolean actual = userDAO.removeUser(user);
        assertEquals(true, actual);

        userDAO.addUser(admin);
        User adminUser = userDAO.getUserByEmail(admin.getEmail());
        assertEquals(admin.getPasswordHash(), adminUser.getPasswordHash());
        assertEquals(admin.getEmail(), adminUser.getEmail());
        assertEquals(admin.getType(), adminUser.getType());

        actual = userDAO.removeUser(admin);
        assertEquals(true, actual);
        assertEquals(false, userDAO.removeUser(admin));
        assertEquals(false, userDAO.removeUser(student));
        assertEquals(false, userDAO.removeUser(user));

        User adminAfterDeletion = userDAO.getUserByEmail(admin.getEmail());
        assertNull(adminAfterDeletion);
    }


    // Tests every single method of SqlUserDAO class.
    public void testMiscellaneous(){
        // add everyone first
        userDAO.addUser(admin);
        userDAO.addUser(student);
        userDAO.addUser(student2);
        userDAO.addUser(lecturer);
        assertEquals(admin.getPasswordHash(), userDAO.getUserByEmail(admin.getEmail()).getPasswordHash());
        assertEquals(student2.getType(), userDAO.getUserByEmail(student2.getEmail()).getType());

        // now remove one of them
        assertTrue(userDAO.removeUser(student));
        assertFalse(userDAO.isValidUser(student.getEmail(), "hash"));
        assertTrue(userDAO.isValidUser(admin.getEmail(), "vergatexav123"));

        // can't remove student anymore
        assertFalse(userDAO.removeUser(student));

        // can't add student2 and lecturer
        assertEquals(-1, userDAO.addUser(student2));
        assertEquals(-1, userDAO.addUser(lecturer));

        userDAO.removeUser(student2);

        // remove lecturer, and add again, check getUserByEmail method in both cases.
        userDAO.removeUser(lecturer);
        assertNull(userDAO.getUserByEmail(lecturer.getEmail()));
        userDAO.addUser(lecturer);
        assertNotNull(userDAO.getUserByEmail(lecturer.getEmail()));
    }
}