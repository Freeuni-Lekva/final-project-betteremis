package DAO;

import Model.Lecturer;
import Model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDAO {

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

    public SubjectDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * Searches for given lecturer in LECTURERS table with UserID, which is stored in provides object.
     * @param lecturerUserID lecturer's UserID needed for search.
     * @return Pair which is (false, -1) if lecturer could not be found or (true, ID) if lecturer could be found.
     */
    public Pair getLecturerID(int lecturerUserID) {
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

    /**
     * Here a new subject is added. If there already exists a subject with this name error will be thrown.
     * This is because subject's name is unique. For this reason information for two lecturers' teaching the same subject
     * cannot be saved in this table.
     * @param subject Subject to be added.
     * @param lecturerUserID Subject's lecturere's user id for determining who teaches the subject.
     * @return ture if added successfully, false otherwise.
     */
    public boolean addSubject(Subject subject, int lecturerUserID){
        Connection conn = pool.getConnection();
        boolean result = false;
        try{
            pool.releaseConnection(conn);
            Pair p = getLecturerID(lecturerUserID);
            conn = pool.getConnection();

            if(!p.first){
                return false;
            }

            int ID = p.second;

            String statement = "INSERT INTO SUBJECTS (SubjectName, Credits, SubjectSemester, LecturerID) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getNumCredits());
            ps.setInt(3, subject.getSemester());
            ps.setInt(4, ID);
            if (ps.executeUpdate() == 1)
                result = true;
            pool.releaseConnection(conn);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return result;
    }

}
