package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlCommentsDAOTest {
    static ConnectionPool pool;
    static UserDAO userDAO;
    static StudentDAO studentDAO;
    static SubjectDAO subjectDAO;
    static SubjectHistoryDAO subjectHistoryDAO;
    static LecturerDAO lecturerDAO;
    static ClassroomPostsDAO classroomPostsDAO;
    static CommentsDAO commentsDAO;
    int ID, ID2, LecID, Subj1, Subj2, ClassroomID;
    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        userDAO = new SqlUserDAO(pool);
        studentDAO = new SqlStudentDAO(pool);
        subjectDAO = new SqlSubjectDAO(pool);
        subjectHistoryDAO = new SqlSubjectHistoryDAO(pool);
        lecturerDAO = new SqlLecturerDAO(pool);
        classroomPostsDAO = new SqlClassroomPostsDAO(pool);
        commentsDAO = new SqlCommentsDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
        User u = new User("gmail@gmail.com", "passhash", USERTYPE.STUDENT);
        ID = userDAO.addUser(u);

        Student st = new Student("gmail@gmail.com", "passhash", USERTYPE.STUDENT, "First", "Second", "Pro", 1, GENDER.MALE, Date.valueOf("1111-11-11"), "a", STATUS.ACTIVE, "a", 1, 1.0, new BigInteger("11111"), "1", ID);
        studentDAO.addStudent(st);

        u = new User("totallylecturer@gmail.com", "totallyhashedpassword", USERTYPE.LECTURER);
        ID2 = userDAO.addUser(u);

        Lecturer lecturer = new Lecturer("totallylecturer@gmail.com","totallyhashedpassword",USERTYPE.LECTURER,ID2,"dimitri","shishniashvili",
                "developer",GENDER.MALE,Date.valueOf("2002-02-12"),"tbilisi",STATUS.ACTIVE,new BigInteger("599"));
        LecID = lecturerDAO.addLecturer(lecturer);

        Subject subject = new Subject("Computer Science 3", 6, LecID);
        Subj1 = subjectDAO.addSubject(subject);

        Subject subject2 = new Subject("Computer Science 4", 6, LecID);
        Subj2 = subjectDAO.addSubject(subject2);

        Connection connection = pool.getConnection();
        try{
            String statement = "INSERT INTO CLASSROOMS (SubjectID, Semester, LecturerID) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Subj1);
            ps.setInt(2, 1);
            ps.setInt(3, LecID);
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                ClassroomID = keys.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Test
    public void SqlClassroomPostsDAOTest1(){
        int FirstID =  classroomPostsDAO.addPost(new Post(-1, ClassroomID, ID, "Wow!", null));
        assertTrue(-1 != FirstID);
        int SecondID =  classroomPostsDAO.addPost(new Post(-1, ClassroomID, ID, "bla", null));
        assertTrue(-1 != SecondID);

        assertTrue(-1 != commentsDAO.addComment(new Comment(FirstID, ID2, "Comment11", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(FirstID, ID2, "Comment12", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(SecondID, ID2, "Comment21", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(SecondID, ID2, "Comment22", null)));
    }

    @Test
    public void SqlClassroomPostsDAOTest2(){
        int FirstID =  classroomPostsDAO.addPost(new Post(-1, ClassroomID, ID, "Wow!", null));
        assertTrue(-1 != FirstID);
        int SecondID =  classroomPostsDAO.addPost(new Post(-1, ClassroomID, ID, "bla", null));
        assertTrue(-1 != SecondID);

        assertTrue(-1 != commentsDAO.addComment(new Comment(FirstID, ID2, "Comment11", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(FirstID, ID2, "Comment12", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(SecondID, ID2, "Comment21", null)));
        assertTrue(-1 != commentsDAO.addComment(new Comment(SecondID, ID2, "Comment22", null)));

        List<Post> result = classroomPostsDAO.getPostsByClassroomID(ClassroomID, false);
        List<String> mapped = result.stream().map(x -> x.getPostContent()).collect(Collectors.toList());
        assertTrue(mapped.contains("Wow!"));
        assertTrue(mapped.contains("bla"));

        List<Comment> comments = commentsDAO.getCommentsByPostID(FirstID, false);
        List<String> mappedComments = comments.stream().map(x -> x.getMessage()).collect(Collectors.toList());
        assertTrue(mappedComments.contains("Comment11"));
        assertTrue(mappedComments.contains("Comment12"));

        comments = commentsDAO.getCommentsByPostID(SecondID, false);
        mappedComments = comments.stream().map(x -> x.getMessage()).collect(Collectors.toList());
        assertTrue(mappedComments.contains("Comment21"));
        assertTrue(mappedComments.contains("Comment22"));
    }
}
