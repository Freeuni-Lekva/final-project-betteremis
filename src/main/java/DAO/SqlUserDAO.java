package DAO;
import DAO.Interfaces.*;
import Model.*;
import java.sql.*;

public class SqlUserDAO implements UserDAO {

    private final ConnectionPool pool;

    public SqlUserDAO(ConnectionPool pool){
        this.pool = pool;
    }

    @Override
    public int addUser(User user) {
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        int userID = -1;
        try {
            stm = conn.prepareStatement("INSERT INTO USERS (Email, PasswordHash, Privilege) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getPasswordHash());
            stm.setString(3, user.getType().toString());
            int added = stm.executeUpdate();

            if(added != 1)
                return -1;

            ResultSet rs = stm.getGeneratedKeys();

            if(rs.next())
                userID = rs.getInt(1);
        } catch (SQLException e) {
//            e.printStackTrace();
            return -1;
        }
        finally {
            pool.releaseConnection(conn);
        }
        return userID;
    }

    @Override
    public boolean removeUser(User user) {
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("DELETE FROM USERS WHERE Email=?;");
            stm.setString(1, user.getEmail());
            int added = stm.executeUpdate();
            return added == 1;
        } catch (SQLException e) {
//            e.printStackTrace();
            return false;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public User getUserByEmail(String email){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM USERS WHERE Email=?;");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                String privilege = rs.getString(4);
                String passHash = rs.getString(3);
                USERTYPE type = USERTYPE.toUserType(privilege);
                return new User(email, passHash, type);
            }
            return null;
        } catch (SQLException e) {
//            e.printStackTrace();
            return null;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean isValidUser(String email, String passHash) {
        return false; //TODO
    }
}
