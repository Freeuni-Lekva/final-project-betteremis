package DAO;

import DAO.Interfaces.SubjectDAO;
import Model.Student;
import Model.Subject;
import org.apache.ibatis.jdbc.SQL;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlSubjectDAO implements SubjectDAO {
    private final ConnectionPool pool;

    public SqlSubjectDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public int addSubject(Subject subject){
        Connection conn = pool.getConnection();
        int result = -1;
        try{
            int ID = subject.getLecturerID();

            String statement = "INSERT INTO SUBJECTS (SubjectName, Credits, LecturerID) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getNumCredits());
            ps.setInt(3, ID);
            if (ps.executeUpdate() == 1) {
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                result = keys.getInt(1);
            }
        }catch (SQLException e){
            //e.printStackTrace();
            result = -1;
        }
        pool.releaseConnection(conn);
        return result;
    }

    /**
     * This method removes the subject with a given name. Since the subject is unique in table only name is sufficient.
     * @param subjectName Name of the subject.
     * @return true if subject was removed successfully, false otherwise.
     */
    public boolean removeSubject(String subjectName){
        Connection conn = pool.getConnection();
        boolean result = false;
        try{
            String statement = "DELETE FROM SUBJECTS WHERE SUBJECTNAME = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, subjectName);
            int updateResult = ps.executeUpdate();
            result = updateResult == 1? true : false;
        }catch (SQLException e){
            //e.printStackTrace();
            result = false;
        }
        finally{
            pool.releaseConnection(conn);
        }
        return result;
    }

    public Subject getSubjectByName(String subjectName){
        Connection conn = pool.getConnection();
        Subject result = null;
        try{
            String statement = "SELECT * FROM SUBJECTS WHERE SUBJECTNAME = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = new Subject(rs.getString(2), rs.getInt(3), rs.getInt(4));
            }
        }catch (SQLException e){
            //e.printStackTrace();
            return null;
        }
        finally{
            pool.releaseConnection(conn);
        }

        return result;
    }

    @Override
    public List<Student> getEnrolledStudents(int subject_id) {
        return null;
    }

    @Override
    public int getSubjectIDByName(String name) {
        Connection conn = pool.getConnection();
        int result = -1;
        try{
            String statement = "SELECT * FROM SUBJECTS WHERE SUBJECTNAME = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        }catch (SQLException e){
            //e.printStackTrace();
            result = -1;
        }
        pool.releaseConnection(conn);
        return result;
    }

    public void removeAll(){
        Connection conn = pool.getConnection();
        int updateResult;
        try {
            String statement = "DELETE FROM SUBJECTS;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.executeUpdate();
        }catch (SQLException e) {
            // e.printStackTrace();
        }
        finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> result = new ArrayList<>();
        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM SUBJECTS;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Subject s = new Subject(rs.getString(2), rs.getInt(3), rs.getInt(4));
                result.add(s);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            pool.releaseConnection(conn);
            return null;
        }

        pool.releaseConnection(conn);
        return result;
    }

}
