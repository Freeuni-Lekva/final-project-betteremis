package DAO;

import DAO.Interfaces.RegistrationStatusDAO;
import org.apache.ibatis.jdbc.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlRegistrationStatusDAO implements RegistrationStatusDAO {
    ConnectionPool pool;

    public SqlRegistrationStatusDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     * Helper function to avoid code duplication.
     * @param flag boolean that will be set in the table.
     * @return true if method finished successfully, false otherwise.
     */
    public boolean updateIsOpenColumn(boolean flag){
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE REGISTRATION_STATUS SET IsOpen = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setBoolean(1, flag);
            if(1 == ps.executeUpdate()) {
                pool.releaseConnection(conn);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
            return false;
        }
        pool.releaseConnection(conn);
        return false;
    }

    @Override
    public boolean openRegistration() {
        return updateIsOpenColumn(true);
    }

    @Override
    public boolean closeRegistration() {
        return updateIsOpenColumn(false);
    }

    @Override
    public boolean registrationStatus() throws SQLException {
        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM REGISTRATION_STATUS;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                pool.releaseConnection(conn);
                return rs.getBoolean(2);
            }
        }catch (SQLException e){
            pool.releaseConnection(conn);
            throw new SQLException("Some kind of error happened while retrieving registration status info.");
        }
        pool.releaseConnection(conn);
        throw new SQLException("Some kind of error happened while retrieving registration status info.");
    }
}
