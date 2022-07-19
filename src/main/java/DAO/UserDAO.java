package DAO;

import Model.USERTYPE;
import Model.User;

import java.sql.*;

public class UserDAO {

    private final ConnectionPool pool;

    public UserDAO(ConnectionPool pool){
        this.pool = pool;
    }

    /**
     * Adds the user into the database and returns the ID it got added to the table.
     * @param user user to be added
     * @return -1 if some kind of error occurred or user could not be found. returns ID otherwise.
     */
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

    /**
     * Removes the user from the database, if it's present.
     * @param user user to be removed
     * @return true if removed successfully, false otherwise
     * @throws SQLException
     */
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

    /** If there exists user with given email or password hash,
     * method returns User object which will contain information:
     * email, passhash and user type which identifies user as
     * student, lecturer or admin. else method returns NULL.
     * @param email email of the user
     * @param passHash hash of the password used to log in
     * @return User object containing information about user*/
    public User getUser(String email, String passHash){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM USERS WHERE Email=? AND PasswordHash=?;");
            stm.setString(1, email);
            stm.setString(2, passHash);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                String privilege = rs.getString(4);
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
}
