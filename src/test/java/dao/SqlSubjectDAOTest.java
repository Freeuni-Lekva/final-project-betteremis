//package dao;
//
//import DAO.ConnectionPool;
//import DAO.SqlSubjectDAO;
//import Model.Subject;
//import org.junit.*;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//
///**
// * Expects Users, Lecturers and Subjects tables to be empty.
// */
//public class SqlSubjectDAOTest {
//    static ConnectionPool pool;
//    static SqlSubjectDAO sqlSubjectDAO;
//    static int ID;
//
//    @BeforeClass
//    public static void runOnce(){
//        pool = new ConnectionPool(1);
//        ID = prepareTablesForAddingSubject();
//        sqlSubjectDAO = new SqlSubjectDAO(pool);
//    }
//
//    public static int prepareTablesForAddingSubject(){
//        Connection conn = pool.getConnection();
//        String statement = "insert into users (email, passwordhash, privilege) values ('gmail@gmail.com', 'totallyhashedpassword', 'Lecturer')";
//        PreparedStatement ps;
//        int ID = 0;
//        try {
//            ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
//            int result = ps.executeUpdate();
//            ID = -1;
//            ResultSet rs = ps.getGeneratedKeys();
//            rs.next();
//            ID = rs.getInt(1);
//
//            assertEquals(1, result);
//
//            statement = "insert into lecturers (userid, firstname, lastname, profession, gender, dateofbirth, address, lecturerstatus, phonenumber)" +
//                    " values (?, 'a', 'b', 'c', 'Male', '2000-01-01', 'a', 'Active', '2' )";
//            ps = conn.prepareStatement(statement);
//            /** Here an error is occurring (For some reason I don't know), but it doesn't stop program for running. */
//            ps.setInt(1, ID);
//            result = ps.executeUpdate();
//            assertEquals(1, result);
//
//            pool.releaseConnection(conn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            pool.releaseConnection(conn);
//        }
//        return ID;
//    }
//
//    @Test
//    public void SubjectDAOTest1(){
//        sqlSubjectDAO.removeAll();
//        Subject subject = new Subject("Math", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject, ID));
//
//        Subject subject2 = new Subject("Math2", 6, 2);
//        assertTrue(sqlSubjectDAO.addSubject(subject2, ID));
//    }
//
//    @Test
//    public void SubjectDAOTest2(){
//        sqlSubjectDAO.removeAll();
//        Subject subject = new Subject("Computer Science 1", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject, ID));
//
//        Subject subject2 = new Subject("Computer Science 2", 6, 2);
//        assertTrue(sqlSubjectDAO.addSubject(subject2, ID));
//
//        assertTrue(sqlSubjectDAO.removeSubject("Computer Science 1"));
//        assertFalse(sqlSubjectDAO.removeSubject("Computer Science 1"));
//        assertTrue(sqlSubjectDAO.removeSubject("Computer Science 2"));
//        assertFalse(sqlSubjectDAO.removeSubject("Computer Science 2"));
//    }
//
//    @Test
//    public void SubjectDAOTest3(){
//        sqlSubjectDAO.removeAll();
//        Subject subject = new Subject("Computer Science 3", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject, ID));
//
//        Subject returned = sqlSubjectDAO.getSubject("computer science 3");
//        assertEquals(subject.getName(), returned.getName());
//        assertEquals(subject.getNumCredits(), returned.getNumCredits());
//        assertEquals(subject.getSemester(), returned.getSemester());
//
//        assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
//        assertEquals(null, sqlSubjectDAO.getSubject("computer science 3"));
//    }
//
//    @Test
//    public void SubjectDAOTest4(){
//        sqlSubjectDAO.removeAll();
//        Subject subject = new Subject("Computer Science 3", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject, ID));
//
//        Subject subject2 = new Subject("Computer Science 4", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject2, ID));
//
//        Subject subject3 = new Subject("Computer Science 5", 6, 1);
//        assertTrue(sqlSubjectDAO.addSubject(subject3, ID));
//
//        List<Subject> returned = sqlSubjectDAO.listSubjects(ID);
//        assertEquals(3, returned.size());
//        assertEquals("Computer Science 3", returned.get(0).getName());
//        assertEquals("Computer Science 4", returned.get(1).getName());
//        assertEquals("Computer Science 5", returned.get(2).getName());
//
//        assertTrue(sqlSubjectDAO.removeSubject("computer science 3"));
//        assertTrue(sqlSubjectDAO.removeSubject("computer science 4"));
//        assertTrue(sqlSubjectDAO.removeSubject("computer science 5"));
//
//        returned = sqlSubjectDAO.listSubjects(ID);
//        assertEquals(new ArrayList<Subject>(), returned);
//
//
//    }
//
//
//
//}
