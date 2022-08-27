package DAO;

import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import Model.*;
import org.junit.jupiter.api.*;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlClassroomDAOTest {

    private static User user1, user2;

    private static Lecturer lecturer;
    private static  Subject subject1, subject2;
    private static Classroom classroom1, classroom2;

    private static SqlLecturerDAO lecturerDAO;


    private static Student student;
    private static SqlStudentDAO studentDAO;

    private static SqlStudentClassroomDAO studentClassroomDAO;
    private static SqlUserDAO  userDAO;

    private static SqlClassroomDAO sqlClassroomDAO;

    private static SubjectDAO subjectDAO;
    private static ConnectionPool pool;

    @BeforeAll
    static void initAll(){
        pool = new ConnectionPool(10, true);
        try {
            SqlScriptRunner.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        initDAOs();
        initVars();
    }

    private static void initVars() {
        user1 = new User("dshis20@freeuni.edu.ge", "123456" , USERTYPE.STUDENT);
        user2 = new User("nitsim@freeuni.edu.ge", "123456",USERTYPE.LECTURER);
        student = new Student(user1.getEmail(), user1.getPasswordHash(), user1.getType(), "dato", "benashvili", "macs",
                4, GENDER.MALE, Date.valueOf("2002-08-06"), "olive street", STATUS.ACTIVE,
                "22 public", 26, 3.32, new BigInteger("599595919"), "Kings", 1);
        lecturer = new Lecturer(user2.getEmail(),user2.getPasswordHash(),user2.getType(),2,"nikolozi","tsimaka",
                "developer",GENDER.MALE,Date.valueOf("2002-04-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("597"));
        subject1 = new Subject("mathematics",6,1);
        subject2 = new Subject("programming",6,1);
        classroom1 = new Classroom(1,1,1,1,null);
        classroom2 = new Classroom(2, 2, 2, 1, null);
    }

    private static void initDAOs() {
        lecturerDAO = new SqlLecturerDAO(pool);
        studentDAO = new SqlStudentDAO(pool);
        userDAO = new SqlUserDAO(pool);
        sqlClassroomDAO = new SqlClassroomDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        studentClassroomDAO = new SqlStudentClassroomDAO(pool);
    }


    @Test
    @Order(1)
    public void testAdd(){
        assertEquals(1, userDAO.addUser(user1));
        assertEquals(2, userDAO.addUser(user2));
        assertEquals(1, lecturerDAO.addLecturer(lecturer));
        assertEquals(1, studentDAO.addStudent(student));
        assertEquals(1, subjectDAO.addSubject(subject1));
        assertEquals(2, subjectDAO.addSubject(subject2));
        assertEquals(1, sqlClassroomDAO.addClassroom(classroom1));
        assertEquals(2, sqlClassroomDAO.addClassroom(classroom2));
        assertEquals(1, studentClassroomDAO.addStudentAndClassroom(student.getEmail(), 1));
        assertEquals(2, studentClassroomDAO.addStudentAndClassroom(student.getEmail(), 2));
    }

    @Test
    @Order(2)
    public void testRemove(){
        assertTrue(sqlClassroomDAO.removeClassroom(classroom1));
        assertTrue(sqlClassroomDAO.removeClassroom(classroom2));
        assertFalse(sqlClassroomDAO.removeClassroom(new Classroom(6,8,7,4,null)));
        int id1 = sqlClassroomDAO.addClassroom(classroom1);
        int id2 = sqlClassroomDAO.addClassroom(classroom2);
        assertTrue(id1 > 0);
        assertTrue(  id2> 0);
        assertTrue( studentClassroomDAO.addStudentAndClassroom(student.getEmail(), id1) > 0);
        assertTrue( studentClassroomDAO.addStudentAndClassroom(student.getEmail(), id2) > 0);
    }

    @Test
    @Order(3)
    public void testGetByLecturer(){
        List<Classroom> ascList = sqlClassroomDAO.getClassroomsByLecturer(lecturer.getEmail(), true);
        assertTrue(ascList.size() == 2);
        assertTrue(ascList.get(0).equals(classroom1));
        assertTrue(ascList.get(1).equals(classroom2));
        List<Classroom> descList = sqlClassroomDAO.getClassroomsByLecturer(lecturer.getEmail(), false);
        assertTrue(descList.size() == 2);
        assertTrue(descList.get(0).equals(classroom2));
        assertTrue(descList.get(1).equals(classroom1));
    }

    @Test
    @Order(4)
    public void testGetByStudent(){
        List<Classroom> ascList = sqlClassroomDAO.getClassroomsByStudent(student.getEmail(), true);
        assertTrue(ascList.size() == 2);
        assertTrue(ascList.get(0).equals(classroom1));
        assertTrue(ascList.get(1).equals(classroom2));
        List<Classroom> descList = sqlClassroomDAO.getClassroomsByStudent(student.getEmail(), false);
        assertTrue(descList.size() == 2);
        assertTrue(descList.get(0).equals(classroom2));
        assertTrue(descList.get(1).equals(classroom1));
    }

    @Test
    @Order(5)
    public void testGetClassroomBySubjectNameAndSemester() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);

        User u2 = new User("nitsim@freeuni.edu.ge", "123456",USERTYPE.LECTURER);
        int ID = userDAO.addUser(u2);
        Lecturer l1 = new Lecturer(user2.getEmail(),user2.getPasswordHash(),user2.getType(),ID,"nikolozi","tsimaka",
                "developer",GENDER.MALE,Date.valueOf("2002-04-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("597"));
        int LID1 = lecturerDAO.addLecturer(l1);
        Subject s1 = new Subject("mathematics1",6,1);
        int SUBID1 = subjectDAO.addSubject(s1);
        Subject s2 = new Subject("mathematics2",6,1);
        int SUBID2 = subjectDAO.addSubject(s2);
        classroom1 = new Classroom(-1,SUBID1,1,LID1,null);
        classroom2 = new Classroom(-1,SUBID2,2,LID1,null);

        sqlClassroomDAO.addClassroom(classroom1);
        sqlClassroomDAO.addClassroom(classroom2);

        assertEquals(SUBID1, sqlClassroomDAO.getClassroomBySubjectNameAndSemester("mathematics1", 1).getSubjectID());
        assertEquals(LID1, sqlClassroomDAO.getClassroomBySubjectNameAndSemester("mathematics1", 1).getLecturerID());
        assertEquals(SUBID2, sqlClassroomDAO.getClassroomBySubjectNameAndSemester("mathematics2", 2).getSubjectID());
        assertEquals(LID1, sqlClassroomDAO.getClassroomBySubjectNameAndSemester("mathematics2", 2).getLecturerID());
        assertEquals(null, sqlClassroomDAO.getClassroomBySubjectNameAndSemester("mathematics1", 2));
    }

    @Test
    @Order(6)
    public void testGetAllClassrooms() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);

        User u2 = new User("nitsim@freeuni.edu.ge", "123456",USERTYPE.LECTURER);
        int ID = userDAO.addUser(u2);
        Lecturer l1 = new Lecturer(user2.getEmail(),user2.getPasswordHash(),user2.getType(),ID,"nikolozi","tsimaka",
                "developer",GENDER.MALE,Date.valueOf("2002-04-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("597"));
        int LID1 = lecturerDAO.addLecturer(l1);
        Subject s1 = new Subject("mathematics1",6,1);
        int SUBID1 = subjectDAO.addSubject(s1);
        Subject s2 = new Subject("mathematics2",6,1);
        int SUBID2 = subjectDAO.addSubject(s2);
        classroom1 = new Classroom(-1,SUBID1,1,LID1,null);
        classroom2 = new Classroom(-1,SUBID2,2,LID1,null);

        sqlClassroomDAO.addClassroom(classroom1);
        sqlClassroomDAO.addClassroom(classroom2);

        List<Classroom> result = sqlClassroomDAO.getAllClassrooms();
        assertEquals(2, result.size());
        List<Integer> mapped = result.stream().map(x -> x.getSubjectID()).collect(Collectors.toList());
        assertTrue(mapped.contains(SUBID1));
        assertTrue(mapped.contains(SUBID2));
    }

}