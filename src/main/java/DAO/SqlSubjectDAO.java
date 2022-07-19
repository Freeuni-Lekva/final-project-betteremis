package DAO;

import DAO.Interfaces.SubjectDAO;
import Model.Student;
import Model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlSubjectDAO implements SubjectDAO {

    /**
     * Class for storing boolean integer pair.
     */
    private class Pair{
        private boolean first;
        private int second;

        public Pair(boolean first, int second) {
            this.first = first;
            this.second = second;
        }

        public boolean isFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
    private final ConnectionPool pool;

    public SqlSubjectDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * Searches for given lecturer in LECTURERS table with UserID, which is stored in provides object.
     * @param lecturerUserID lecturer's UserID needed for search.
     * @return Pair which is (false, -1) if lecturer could not be found or (true, ID) if lecturer could be found.
     */
    private Pair getLecturerID(int lecturerUserID) {
        Connection conn = pool.getConnection();
        int ID = -1;
        try{
            String statement = "select * from LECTURERS WHERE UserID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, lecturerUserID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ID = rs.getInt(1);
                pool.releaseConnection(conn);
                return new Pair(true, ID);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        pool.releaseConnection(conn);
        return new Pair(false, -1);
    }


    public boolean addSubject(Subject subject){
//        Connection conn = pool.getConnection();
//        boolean result = false;
//        try{
//            pool.releaseConnection(conn);
//
//            Pair p = getLecturerID(lecturerUserID);
//            conn = pool.getConnection();
//
//            if(!p.first){
//                return false;
//            }
//
//            int ID = p.second;
//
//            String statement = "INSERT INTO SUBJECTS (SubjectName, Credits, SubjectSemester, LecturerID) VALUES (?, ?, ?, ?);";
//            PreparedStatement ps = conn.prepareStatement(statement);
//            ps.setString(1, subject.getName());
//            ps.setInt(2, subject.getNumCredits());
//            ps.setInt(3, subject.getSemester());
//            ps.setInt(4, ID);
//            if (ps.executeUpdate() == 1)
//                result = true;
//            pool.releaseConnection(conn);
//        }catch (SQLException e){
//            e.printStackTrace();
//            result = false;
//        }
//
//        return result;
        return false;
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
            e.printStackTrace();
            pool.releaseConnection(conn);
            result = false;
        }

        pool.releaseConnection(conn);
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
                result = new Subject(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
            return null;
        }

        pool.releaseConnection(conn);
        return result;
    }

    @Override
    public List<Student> getEnrolledStudents(int subject_id) {
        return null;
    }

    public void removeAll(){

        Connection conn = pool.getConnection();
        int updateResult;
        try {
            String statement = "DELETE FROM SUBJECTS;";
            PreparedStatement ps = conn.prepareStatement(statement);
            pool.releaseConnection(conn);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
        }
    }

}
