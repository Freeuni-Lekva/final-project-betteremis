package DAO;

import Model.Student;
import Model.User;

public class UserDAO {

    public UserDAO(){

    }
    public void addUser(User user){

    }

    public void removeUser(User user){

    }

    /** If there exists user with given email or password hash,
     * method returns User object which will contain information:
     * email, passhash and user type which identifies user as
     * student, lecturer or admin. else method returns NULL.
     * @param email email of the user
     * @param passHash hash of the password used to log in
     * @return User object containing information about user*/
    public User isValidUser(String email, String passHash){
        //TODO
        return null;
    }
}
