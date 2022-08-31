package DAO;

import DAO.Interfaces.CurrentSemesterDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlCurrentSemesterDAO implements CurrentSemesterDAO {
    ConnectionPool pool;

    public SqlCurrentSemesterDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    private boolean changeSemesterByOne(String operator){
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE CURRENT_SEMESTER SET CurrentSemester = CurrentSemester "+ operator +"1;";
            PreparedStatement ps = conn.prepareStatement(statement);
            return ps.executeUpdate() == 1;
        }catch (SQLException e){
            //e.printStackTrace();
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean incrementSemester() {
        return changeSemesterByOne("+");
    }

    @Override
    public boolean decrementSemester() {
        return changeSemesterByOne("-");
    }

    @Override
    public int getCurrentSemester() {
        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM CURRENT_SEMESTER;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(2);
            return -1;
        }catch (SQLException e){
            //e.printStackTrace();
            return -1;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean setSemester(int semester) {
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE CURRENT_SEMESTER SET CurrentSemester = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, semester);
            return ps.executeUpdate() == 1;
        }catch (SQLException e){
            //e.printStackTrace();
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }
}
