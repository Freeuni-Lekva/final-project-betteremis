package DAO;

import DAO.Interfaces.*;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.*;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SqlStudentDAOTest {
    static User stud1, stud2, stud3;
    static UserDAO usrDAO;
    static StudentDAO studDAO;

    @BeforeAll
    static void initAll(){
        ConnectionPool pool = new ConnectionPool(10, true);
        try {
            SqlScriptRunner.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        usrDAO = new SqlUserDAO(pool);
        studDAO = new SqlStudentDAO(pool);
        stud1 = new User("dbena20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "bena20".toCharArray()), USERTYPE.STUDENT);
        stud2 = new User("garse20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "arsena33".toCharArray()), USERTYPE.STUDENT);
        stud3 = new User("ntsim20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "tsimtsim".toCharArray()), USERTYPE.STUDENT);
        stud1 = new Student(stud1.getEmail(), stud1.getPasswordHash(), stud1.getType(), "dato", "benashvili", "macs", 4, GENDER.MALE, Date.valueOf("2002-08-06"),
                "olive street", STATUS.ACTIVE, "22 public", 26, 3.32, new BigInteger("599595919"), "Kings", 1);
        stud2 = new Student(stud2.getEmail(), stud2.getPasswordHash(), stud2.getType(), "giorgi", "arsenadze", "macs", 4, GENDER.MALE, Date.valueOf("2002-02-12"),
                "apple street", STATUS.ACTIVE, "1231 public", 35, 3.99, new BigInteger("599777779"), "Kings", 2);
        stud3 = new Student(stud3.getEmail(), stud3.getPasswordHash(), stud3.getType(), "nikoloz", "tsimakuridze", "macs", 4, GENDER.FEMALE, Date.valueOf("2002-09-25"),
                "grape street", STATUS.ACTIVE, "2223 public", 15, 3.89, new BigInteger("599123919"), "Kings", 3);
    }

    /**
     * Tests if adding users and then students works correctly
     * */
    @Test
    @Order(1)
    public void testAddingUsersThenStudents(){
        /*
        Check if user and student DAO add users correctly
        and returns the correct IDs.
         */
        assertEquals(1, usrDAO.addUser(stud1));
        assertEquals(2, usrDAO.addUser(stud2));
        assertEquals(3, usrDAO.addUser(stud3));

        assertEquals(1, studDAO.addStudent((Student) stud1));
        assertEquals(2, studDAO.addStudent((Student) stud2));
        assertEquals(3, studDAO.addStudent((Student) stud3));

        /*
        Check if user and student DAO will return correct
        value(-1) when given user is already in the database.
         */
        assertEquals(-1, usrDAO.addUser(stud1));
        assertEquals(-1, usrDAO.addUser(stud2));
        assertEquals(-1, usrDAO.addUser(stud3));

        assertEquals(-1, studDAO.addStudent((Student) stud1));
        assertEquals(-1, studDAO.addStudent((Student) stud2));
        assertEquals(-1, studDAO.addStudent((Student) stud3));
    }

    /**
     * Checks if information is correctly stored and retrieved
     * from the database. And checks if Equals method works Correctly.
     */
    @Test
    @Order(2)
    public void testDatabaseAuthenticity(){
        //Check if information is stored and retrieved correctly from the database.
        assertEquals(stud1, studDAO.getStudentWithEmail(stud1.getEmail()));
        assertEquals(stud2, studDAO.getStudentWithEmail(stud2.getEmail()));
        assertEquals(stud3, studDAO.getStudentWithEmail(stud3.getEmail()));
        //Check if Student.equals method works correctly
        assertNotSame(stud1, studDAO.getStudentWithEmail(stud2.getEmail()));
        assertNotSame(stud2, studDAO.getStudentWithEmail(stud1.getEmail()));
        assertNotSame(stud3, studDAO.getStudentWithEmail(stud2.getEmail()));
    }

    /**
     * Checks if remove method of User removes the Student as well.
     */
    @Test
    @Order(3)
    public void testRemoveUser(){
        //Remove user from database and check that student is removed as well.
        assertEquals(true, usrDAO.removeUser(stud1));
        assertEquals(null, usrDAO.getUserByEmail(stud1.getEmail()));
        assertEquals(null, studDAO.getStudentWithEmail(stud1.getEmail()));

        //Check that other students are left intact.
        assertEquals(stud2, studDAO.getStudentWithEmail(stud2.getEmail()));
        assertEquals(stud3, studDAO.getStudentWithEmail(stud3.getEmail()));

        //Remove other users as well and confirm that they are removed.
        assertEquals(true, usrDAO.removeUser(stud2));
        assertEquals(true, usrDAO.removeUser(stud3));

        assertEquals(null, usrDAO.getUserByEmail(stud2.getEmail()));
        assertEquals(null, usrDAO.getUserByEmail(stud3.getEmail()));
        assertEquals(null, studDAO.getStudentWithEmail(stud2.getEmail()));
        assertEquals(null, studDAO.getStudentWithEmail(stud3.getEmail()));

        //Try to remove once again, must return false.
        assertEquals(false, usrDAO.removeUser(stud1));
        assertEquals(false, usrDAO.removeUser(stud2));
        assertEquals(false, usrDAO.removeUser(stud3));

    }

    @Test
    @Order(4)
    public void testGetStudentIDByUserID(){
        User user = new User("garse20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "arsena33".toCharArray()), USERTYPE.STUDENT);
        int ID = usrDAO.addUser(user);
        Student st = new Student(stud2.getEmail(), stud2.getPasswordHash(), stud2.getType(), "giorgi", "arsenadze", "macs", 4, GENDER.MALE, Date.valueOf("2002-02-12"),
                "apple street", STATUS.ACTIVE, "1231 public", 35, 3.99, new BigInteger("599777779"), "Kings", ID);

        assertEquals(studDAO.addStudent(st), studDAO.getStudentIDByUserID(ID));
    }
}
