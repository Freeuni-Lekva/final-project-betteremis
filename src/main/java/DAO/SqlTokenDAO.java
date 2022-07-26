package DAO;

import DAO.Interfaces.TokenDAO;

import java.sql.*;

public class SqlTokenDAO implements TokenDAO {
    private ConnectionPool pool;

    public SqlTokenDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public boolean addToken(String token) {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO TOKENS VALUES (?, ?);");
            stm.setString(1, token);
            stm.setString(2, "CURRENT_TIMESTAMP()");
            int rows_updated = stm.executeUpdate();
            if(rows_updated == 1) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
    }

    @Override
    public boolean isValidToken(String token) {
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM TOKENS WHERE Token = ?;");
            stm.setString(1, token);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
    }
}
