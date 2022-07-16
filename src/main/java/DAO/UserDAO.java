package DAO;

import Model.USERTYPE;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final ConnectionPool pool;

    public UserDAO(ConnectionPool pool){
        this.pool = pool;
    }

    /**
     * Adds the user into the database.
     * @param user user to be added
     * @return true if the user has been added successfully, false otherwise.
     */
    public boolean addUser(User user) {
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("INSERT INTO USERS VALUES (?, ?, ?)");
            stm.setString(1, user.getEmail());
            stm.setString(2, user.getPasswordHash());
            stm.setString(3, user.getType().toString());
            int added = stm.executeUpdate();
            return added == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            throw new RuntimeException(e);
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
            while(rs.next()){
                String privilege = rs.getString(3);
                USERTYPE type = USERTYPE.toUserType(privilege);
                return new User(email, passHash, type);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
