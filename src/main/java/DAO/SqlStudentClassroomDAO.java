package DAO;

import DAO.Interfaces.StudentClassroomDAO;
import DAO.Interfaces.StudentDAO;
import Model.Classroom;
import Model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStudentClassroomDAO implements StudentClassroomDAO {
    ConnectionPool pool;

    public SqlStudentClassroomDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Student> getStudentsInClassroom(int ClassroomID) {
        StudentDAO stDAO = new SqlStudentDAO(pool);

        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM STUDENT_CLASSROOMS WHERE ClassroomID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, ClassroomID);
            ResultSet rs = ps.executeQuery();
            List<Integer> IDS = new ArrayList<>();
            while(rs.next()){
                IDS.add(rs.getInt(3));
            }
            pool.releaseConnection(conn);
            List<Student> result = new ArrayList<>();
            for(int ID : IDS){
                result.add(stDAO.getStudentByID(ID));
            }
            return result;
        }catch (SQLException e){
            //e.printStackTrace();
            pool.releaseConnection(conn);
            return null;
        }
    }

    @Override
    public int addStudentAndClassroom(String studentEmail, int ClassroomID) {
        StudentDAO stDAO = new SqlStudentDAO(pool);

        int StudentID = stDAO.getStudentIDByEmail(studentEmail);

        if(StudentID == -1)
            return -1;

        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO STUDENT_CLASSROOMS (ClassroomID, StudentID) VALUES (?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ClassroomID);
            ps.setInt(2, StudentID);
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return keys.getInt(1);
            }
            return -1;
        }catch (SQLException e){
            //e.printStackTrace();
            return -1;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean removeStudentAndClassroom(String studentEmail, int ClassroomID) {
        StudentDAO stDAO = new SqlStudentDAO(pool);

        int StudentID = stDAO.getStudentIDByEmail(studentEmail);

        if(StudentID == -1)
            return false;

        Connection conn = pool.getConnection();
        try{
            String statement = "DELETE FROM STUDENT_CLASSROOMS WHERE ClassroomID = ? AND StudentID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, ClassroomID);
            ps.setInt(2, StudentID);
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                return true;
            }
            return false;
        }catch (SQLException e){
            //e.printStackTrace();
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }
}
