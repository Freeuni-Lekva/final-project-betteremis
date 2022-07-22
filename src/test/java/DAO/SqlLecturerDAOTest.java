package DAO;

import DAO.Interfaces.*;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlLecturerDAOTest {

    /**
     * Private variables.
     */

    private static Random random;
    private static SqlLecturerDAO lecturerDAO;
    private static SqlUserDAO  userDAO;
    private static SqlSubjectDAO subjectDAO;
    private static User user1 , user2;
    private static Lecturer lecturer1, lecturer2;

    private static Subject subject1, subject2;


    /**
     * Stores private variables of DAO classes and creates users, lecturers and subjects.
     */

    @BeforeAll
    static void initAll(){
        ConnectionPool pool = new ConnectionPool(10, true);
        try {
            TestingUtils.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        lecturerDAO = new SqlLecturerDAO(pool);
        userDAO = new SqlUserDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        random = new Random();
        initVariables();
    }


    /**
     * Just initializes needed variables.
     */

    private static void initVariables() {
        user1 = new User("dshis20@freeuni.edu.ge", getRandomPassword() ,USERTYPE.LECTURER);
        user2 = new User("nitsim@freeuni.edu.ge", getRandomPassword(),USERTYPE.LECTURER);

        lecturer1 = new Lecturer(user1.getEmail(),user2.getPasswordHash(),user1.getType(),1,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        lecturer2 = new Lecturer(user2.getEmail(),user2.getPasswordHash(),user2.getType(),2,"nikolozi","tsimaka",
                "developer",GENDER.MALE,Date.valueOf("2002-04-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("597"));

        subject1 = new Subject("mathematics",6,5,1);
        subject2 = new Subject("programming",6,5,2);

    }


    /**
     * Function generates random password for the users.
     * @return random password.
     */

    private static String getRandomPassword(){
        String pass = "";
        for(int i = 0; i < 6; i++ ){
            pass.concat(String.valueOf((char) (random.nextInt(26) + 'a')));
        }
        return BCrypt.withDefaults().hashToString(12, pass.toCharArray());
    }

    /**
     * Tests functions of userDao , subjectDao and lecturerDao
     */


    @Test
    @Order(1)
    public void testAdd(){
        //Adding users
        assertEquals(1,userDAO.addUser(user1));
        assertEquals(2, userDAO.addUser(user2));

        //Adding lecturers
        assertEquals(1,lecturerDAO.addLecturer(lecturer1));
        assertEquals(2,lecturerDAO.addLecturer(lecturer2));

        //Adding subjects
        assertEquals(1, subjectDAO.addSubject(subject1));
        assertEquals(2, subjectDAO.addSubject(subject2));

        //Adding same user
        assertEquals(-1,userDAO.addUser(user1));

        //Adding same lecturer
        assertEquals(-1,lecturerDAO.addLecturer(lecturer1));

        //Adding same subject
        //assertEquals(-1,subjectDAO.addSubject(subject2));

    }


    /**
     * Tests getters of DAO classes.
     */

    @Test
    @Order(3)
    public void testGet(){

        User tempUser = userDAO.getUserByEmail(user1.getEmail());
        assertTrue(tempUser.getEmail().equals(user1.getEmail()));

        Lecturer tempLecturer = lecturerDAO.getLecturerWithEmail(lecturer2.getEmail());
        assertTrue(tempLecturer.getEmail().equals(lecturer2.getEmail()));

        User tempUser2 = userDAO.getUserByEmail(user2.getEmail());
        assertTrue(tempUser2.getEmail().equals(user2.getEmail()));

        Lecturer tempLecturer2 = lecturerDAO.getLecturerWithEmail(lecturer1.getEmail());
        assertTrue(tempLecturer2.getEmail().equals(lecturer1.getEmail()));

    }

    /**
     * Tests getAllSubjects
     */

    @Test
    @Order(3)
    public void testGetSubjects(){

        List<Subject> res = lecturerDAO.getAllSubjects(lecturer1.getEmail());
        assertTrue(res.get(0).getName().equals(subject1.getName()));

        List<Subject> res2 = lecturerDAO.getAllSubjects(lecturer2.getEmail());
        assertTrue(res2.get(0).getName().equals(subject2.getName()));

        //Now add more subjects
        String[] stA = new String[]{"A","B","C","D"};
        for (int i = 0; i < stA.length; i++){
            subjectDAO.addSubject(new Subject(stA[i],6,5,1));
        }
        res = lecturerDAO.getAllSubjects(lecturer1.getEmail());
        assertTrue(res.size() == stA.length+1);
        for (int i = 0; i < stA.length; i++){
            assertEquals(res.get(i+1).getName(), stA[i]);
        }
    }

}
