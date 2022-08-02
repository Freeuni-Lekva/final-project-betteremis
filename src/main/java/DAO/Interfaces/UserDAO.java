package DAO.Interfaces;

import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    /**
     * Adds the user into the database and returns the ID it got added to the table.
     * @param user user to be added
     * @return -1 if some kind of error occurred or user could not be found. returns ID otherwise.
     */
    int addUser(User user);

    /**
     * Removes the user from the database, if it's present.
     * @param user user to be removed
     * @return true if removed successfully, false otherwise
     */
    boolean removeUser(User user);

    /** Finds user in the database with the given email.
     * @param email email of the user
     * @return User with the given email. If a such user does not exist, returns NULL.
     **/
    User getUserByEmail(String email);

    /**
     * Returns true if the user with given email and password is present in the database.
     * @param email
     * @param password
     */
    boolean isValidUser(String email, String password);


    boolean setPassword(String email, String newPassword);


    int getIDByEmail(String email);

    List<User> getAllUsers();
}
