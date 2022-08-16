package DAO;

import DAO.Interfaces.ClassroomDAO;
import Model.Classroom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlClassroomDAO implements ClassroomDAO {

    private ConnectionPool pool;

    public SqlClassroomDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Classroom> getClassroomsByLecturer(String lecturerEmail, boolean asc) {
        SqlLecturerDAO lecturerDAO = new SqlLecturerDAO(pool);
        int lecturerID = lecturerDAO.getIDByEmail(lecturerEmail);
        Connection conn = pool.getConnection();
        List<Classroom> result = new ArrayList<>();
        try{
            String statement = "SELECT * FROM CLASSROOMS WHERE LecturerID = ? ORDER BY Semester " + (asc ? "asc" : "desc") + ";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, lecturerID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(new Classroom(rs.getInt(1), rs.getInt(2),rs.getInt(3),
                        rs.getInt(4), rs.getTimestamp(5)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Classroom> getClassroomsByStudent(String studentEmail, boolean asc) {
        SqlStudentDAO studentDAO = new SqlStudentDAO(pool);
        int studentID = studentDAO.getStudentIDByEmail(studentEmail);
        Connection conn = pool.getConnection();
        List<Classroom> result = new ArrayList<>();
        try{
            String statement = "SELECT C.ID, C.SubjectID, C.Semester, C.LecturerID, C.CreationDate "
                    + "FROM STUDENT_CLASSROOMS SC JOIN CLASSROOMS C ON C.ID = SC.ClassroomID " +
                    "WHERE SC.StudentID = ? ORDER BY C.Semester " + (asc ? "asc" : "desc") + ";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(new Classroom(rs.getInt(1), rs.getInt(2),rs.getInt(3),
                        rs.getInt(4), rs.getTimestamp(5)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public int addClassroom(Classroom classroom) {
        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO CLASSROOMS (SubjectID, Semester, LecturerID) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, classroom.getSubjectID());
            ps.setInt(2, classroom.getSemester());
            ps.setInt(3, classroom.getLecturerID());
            int updateResult = ps.executeUpdate();
            if(updateResult == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                return keys.getInt(1);
            }
            return -1;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean removeClassroom(Classroom classroom) {
        Connection conn = pool.getConnection();
        try{
            String statement = "DELETE FROM CLASSROOMS WHERE SubjectID = ? AND Semester = ? ;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, classroom.getSubjectID());
            ps.setInt(2, classroom.getSemester());
            int result = ps.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            pool.releaseConnection(conn);
        }
    }
}
