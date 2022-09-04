package DAO;

import DAO.Interfaces.SemesterDAO;

import java.sql.*;

public class SqlSemesterDAO implements SemesterDAO {
    private ConnectionPool pool;

    public SqlSemesterDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public boolean startSemester() {
        Connection conn = pool.getConnection();
        String stm = "UPDATE CURRENT_SEMESTER SET CurrentSemester = CurrentSemester + 1";
        try {
            PreparedStatement prepStm = conn.prepareStatement(stm);
            prepStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
        return changeSemesterStatus(true);
    }

    @Override
    public boolean endSemester() {
        return changeSemesterStatus(false);
    }

    @Override
    public boolean getSemesterStatus() {
        Connection conn = pool.getConnection();
        String statement = "SELECT * FROM SEMESTER_STATUS;";
        try {
            PreparedStatement stm = conn.prepareStatement(statement);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                return resultSet.getBoolean(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
    }

    @Override
    public int getCurrentSemester() {
        Connection conn = pool.getConnection();
        String stm = "SELECT * FROM current_semester";
        try {
            PreparedStatement prepStm = conn.prepareStatement(stm);
            ResultSet rs = prepStm.executeQuery();
            if(rs.next()){
                int semester = rs.getInt("CurrentSemester");
                return semester;
            }else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return -1;
    }

    @Override
    public boolean setCurrentSemester(int semester){
        Connection conn = pool.getConnection();
        String stm = "UPDATE current_semester SET CurrentSemester = ?";
        try {
            PreparedStatement prepStm = conn.prepareStatement(stm);
            prepStm.setInt(1, semester);
            return prepStm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
    }


    private boolean changeSemesterStatus(boolean flag){
        Connection conn = pool.getConnection();
        String stm = "UPDATE SEMESTER_STATUS SET IsStarted = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(stm);
            preparedStatement.setBoolean(1, flag);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
    }
}
