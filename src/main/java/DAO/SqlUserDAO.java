package DAO;
import DAO.Interfaces.*;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
       //     stm.setString(3, USERTYPE.ADMIN.toString());
            int added = stm.executeUpdate();
            if(added != 1)
                return -1;
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next())
                userID = rs.getInt(1);
        } catch (SQLException e) {return -1;}
            // No need to print the error here
        finally{
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
        } catch (SQLException e) {}
            // No need to print the error here
        finally{
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
                return new User(email, passHash, type);
            }
            return null;
        } catch (SQLException e) {return null;}
        finally{
            pool.releaseConnection(conn);
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
        } catch (SQLException e) {return false;}
        finally {
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
        } catch (SQLException e) {return -1;}
        finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public String getEmailByID(int  ID){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT Email FROM USERS WHERE ID=?;");
            stm.setInt(1, ID);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
            return null;
        } catch (SQLException e) {return null;}
        finally {
            pool.releaseConnection(conn);
        }
    }
    @Override
    public List<User> getAllUsers(){
        List<User> res = new ArrayList<>();
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM USERS WHERE Privilege != ? ;");
            stm.setString(1, "Admin");
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                res.add(new User(rs.getString(2), rs.getString(3),
                        USERTYPE.toUserType(rs.getString(4))));
            }
            return res;
        } catch (SQLException e) {return null;}
        finally {
            pool.releaseConnection(conn);
        }

    }

}
