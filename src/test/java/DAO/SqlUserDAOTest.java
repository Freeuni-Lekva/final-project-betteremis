package DAO;
import DAO.Interfaces.UserDAO;
import Model.User;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static utility.SqlScriptRunner.emptyTables;
import static Model.USERTYPE.*;
import static org.junit.jupiter.api.Assertions.*;

public class SqlUserDAOTest {

    /**
     * User objects that will be used in the tests.
     */
    private User student, student2, lecturer, admin;

    // UserDAO object
    private UserDAO userDAO;

    /**
     * Creates instances of all the objects needed.
     */
    public SqlUserDAOTest() throws FileNotFoundException {
        ConnectionPool pool = new ConnectionPool(5, true);
        userDAO = new SqlUserDAO(pool);
        student = new User("student@gmail.com", BCrypt.withDefaults().hashToString(10, "hash".toCharArray()), STUDENT); //hash
        student2 = new User("student2@gmail.com", BCrypt.withDefaults().hashToString(10, "xAE12$#%".toCharArray()), STUDENT); //xAE12$#%
        lecturer = new User("lecturer@gmail.com", BCrypt.withDefaults().hashToString(10, "dzlierihashi123".toCharArray()), LECTURER); //dzlierihashi123
        admin = new User("admin@gmail.com", BCrypt.withDefaults().hashToString(10, "vergatexav123".toCharArray()), ADMIN); //vergatexav123
        emptyTables(pool.getConnection());
    }

    // simple add/contains test
    @Test
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


    @Test
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

    @Test
    public void testRemove1(){
        int id = userDAO.addUser(student);
        assertNotSame(-1, id);
        assertTrue(userDAO.removeUser(student));
        assertFalse(userDAO.isValidUser(student.getEmail(), student.getPasswordHash()));
        assertFalse(userDAO.removeUser(student));
        userDAO.addUser(student);
        assertTrue(userDAO.removeUser(student));
    }


    @Test
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

    @Test
    public void testSetPassword(){
        userDAO.addUser(student);
        String newPassword = "newPassword";
        boolean set = userDAO.setPassword(student.getEmail(), "newPassword");
        assertEquals(true, set);
        User newStudent = userDAO.getUserByEmail(student.getEmail());
        BCrypt.Verifyer verifier = BCrypt.verifyer();
        BCrypt.Result res = verifier.verify(newPassword.toCharArray(), newStudent.getPasswordHash().toCharArray());
        assertTrue(res.verified);
        assertFalse(verifier.verify("hash".toCharArray(), newStudent.getPasswordHash().toCharArray()).verified);

        assertNotSame(-1, userDAO.addUser(student2));
        set = userDAO.setPassword(student2.getEmail(),"xAE12$#%");
        newStudent = userDAO.getUserByEmail(student2.getEmail());
        assertEquals(true, userDAO.isValidUser(student2.getEmail(), "xAE12$#%"));
        assertEquals(true, set);
        BCrypt.Result answer = verifier.verify("araswori".toCharArray(), newStudent.getPasswordHash().toCharArray());
        assertTrue(res.verified);

        userDAO.removeUser(student);
        userDAO.removeUser(student2);
        userDAO.addUser(admin);
        userDAO.addUser(student2);

        userDAO.setPassword(admin.getEmail(), "oe");
        assertEquals(false, userDAO.setPassword("invalidMail", "oe"));
        User user = userDAO.getUserByEmail(admin.getEmail());
        verifier.verify("ae".toCharArray(), user.getPasswordHash().toCharArray());
        assertEquals(true, userDAO.removeUser(admin));
        assertEquals(false, userDAO.isValidUser(user.getEmail(), user.getPasswordHash()));
    }

    @Test
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

    @Test
    public void testIdByEmail(){
        int id = userDAO.addUser(student);
        int id2 = userDAO.addUser(student2);
        assertEquals(id, userDAO.getIDByEmail(student.getEmail()));
        userDAO.removeUser(student);
        id = userDAO.addUser(student);
        assertEquals(id, userDAO.getIDByEmail(student.getEmail()));

        assertEquals(id2, userDAO.getIDByEmail(student2.getEmail()));
        assertTrue(userDAO.isValidUser(student2.getEmail(),"xAE12$#%" ));

        assertEquals(-1, userDAO.getIDByEmail("invalidMail"));
        assertEquals(-1, userDAO.getIDByEmail("invaaaaaaaaaalid"));

    }

    @Test
    public void testAllUsers(){
        userDAO.addUser(student);
        userDAO.addUser(lecturer);
        userDAO.addUser(student2);
        userDAO.addUser(admin);
        List<User> users = userDAO.getAllUsers();
        assertEquals(3, users.size());
        for(int i = 0; i < users.size(); i++){
            User current = users.get(i);
            assert(current.equals(student) || current.equals(lecturer) || current.equals(student2));
        }
        userDAO.removeUser(student2);
        userDAO.removeUser(lecturer);
        users = userDAO.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(student, users.get(0));
    }
}