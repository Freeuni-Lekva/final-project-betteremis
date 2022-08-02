package DAO;
import DAO.Interfaces.*;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

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
            // No need to print the error here
            //e.printStackTrace();
            return -1;
        }finally{
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
            // No need to print the error here
            //e.printStackTrace();
        }finally{
            pool.releaseConnection(conn);
        }
        return false;
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
                pool.releaseConnection(conn);
                return new User(email, passHash, type);
            }
            pool.releaseConnection(conn);
            return null;
        } catch (SQLException e) {
            // No need to print the error here
            //e.printStackTrace();
            pool.releaseConnection(conn);
            return null;
        }
    }

    @Override
    public boolean isValidUser(String email, String password) {
        User usr = getUserByEmail(email);
        if(usr == null) {
            return false;
        }

        BCrypt.Verifyer verifier = BCrypt.verifyer();
        BCrypt.Result res = verifier.verify(password.toCharArray(), usr.getPasswordHash().toCharArray());
        return res.verified;
    }

    @Override
    public boolean setPassword(String email, String newPassword){
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        char[] chars = new char[newPassword.length()];
        for(int i = 0; i < newPassword.length(); i++ ){
            chars[i] = newPassword.charAt(i);
        }
        String passHash = hasher.hashToString(10,chars);
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE USERS SET PasswordHash = ? WHERE Email = ? ;");
            stm.setString(1, passHash);
            stm.setString(2,email);
            int updated = stm.executeUpdate();
            if(updated == 1) return true;
            return false;
        } catch (SQLException e) {
            return false;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public int getIDByEmail(String email){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT ID FROM USERS WHERE Email=?;");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
               return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }finally {
            pool.releaseConnection(conn);
        }
    }
}
