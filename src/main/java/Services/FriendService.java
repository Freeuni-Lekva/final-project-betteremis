package Services;

import DAO.Interfaces.*;
import DAO.Interfaces.UserDAO;
import Model.*;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

import static DAO.Mapping.*;
import static Model.USERTYPE.*;
import static Services.FriendService.RequestResult.*;

public class FriendService {

    /**
     * Checks whether one user has sent friend request to the other, and if so,
     * makes them friends.
     * @param user1 User Object
     * @param user2 User Object
     * @param dao FriendsDAO
     * @return true if two users can make a friendship and false otherwise.
     */
    public boolean acceptRequest(User user1, User user2, FriendsDAO dao){
        if(!dao.isInRequests(user1, user2)) return false;

        dao.addFriend(user1, user2);
        dao.removeRequest(user1, user2);
        return true;
    }

    /**
     * Checks whether one user has sent friend request to the other, and if so,
     * makes them friends.
     * @param user1 User Object
     * @param user2 User Object
     * {@code @NotNull} @param dao FriendsDAO object
     */
    public boolean declineRequest(User user1, User user2, FriendsDAO dao){
        if(!dao.isInRequests(user1, user2)) return false;
        dao.removeRequest(user1, user2);
        return true;
    }


    public enum RequestResult{
        REQUEST_SUCCESS,
        REQUEST_USER_NOT_FOUND,
        REQUEST_ALREADY_EXISTS,
        REQUEST_BECAME_FRIENDS,
        REQUEST_ARE_FRIENDS,
        REQUEST_SAME_USER
    }

    /**
     * Responsible for handling sent requests. Uses FriendsDAO class to add/remove requests.
     * If the request was previously sent from receiver to sender, this method makes them friends.
     * @param sender User that's sending a request
     * @param receiverEmail Email of the receiver
     * @return
     */
    public RequestResult sendRequest(User sender, String receiverEmail, FriendsDAO friendsDAO, UserDAO userDAO){
        User receiver = userDAO.getUserByEmail(receiverEmail);
        if(receiver == null) return REQUEST_USER_NOT_FOUND;
        if(receiver.getEmail().equals(sender.getEmail())) return REQUEST_SAME_USER;
        if(friendsDAO.isInRequests(sender, receiver)){
            return REQUEST_ALREADY_EXISTS;
        }else if(friendsDAO.isInRequests(receiver, sender)){
            friendsDAO.removeRequest(receiver, sender);
            friendsDAO.addFriend(receiver, sender);
            return REQUEST_BECAME_FRIENDS;
        }else if(friendsDAO.areFriends(sender, receiver)){
            return REQUEST_ARE_FRIENDS;
        }else{
            friendsDAO.addRequest(sender, receiver);
            return REQUEST_SUCCESS;
        }
    }

    /**
     * Finds all friends of the user and returns them as a list of users, which are castable
     * to either a Student or a Lecturer type object.
     * @param user User object
     * @param sc ServletContext, used to get access to DAO objects
     * @return list of friends
     */
    public List<User> getAllFriends(User user, ServletContext sc){
        FriendsDAO friendsDAO = (FriendsDAO) sc.getAttribute(FRIENDS_DAO);
        StudentDAO studentDAO = (StudentDAO) sc.getAttribute(STUDENT_DAO);
        LecturerDAO lecturerDAO = (LecturerDAO) sc.getAttribute(LECTURER_DAO);
        List<User> partialList = friendsDAO.getAllFriends(user);
        return getCastableUsers(user, studentDAO, lecturerDAO, partialList);
    }

    /**
     * Finds all friend requests of the user and returns them as a list Castable users.
     * @param user User object
     * @param sc ServletContext, used to get access to DAO objects
     * @return list of friend requests
     */
    public List<User> getAllRequests(User user, ServletContext sc){
        FriendsDAO friendsDAO = (FriendsDAO) sc.getAttribute(FRIENDS_DAO);
        StudentDAO studentDAO = (StudentDAO) sc.getAttribute(STUDENT_DAO);
        LecturerDAO lecturerDAO = (LecturerDAO) sc.getAttribute(LECTURER_DAO);
        List<User> partialList = friendsDAO.getAllRequests(user);
        return getCastableUsers(user, studentDAO, lecturerDAO, partialList);
    }

    /**
     * Utility to convert list of users to the list of castable users.
     */
    private List<User> getCastableUsers(User user, StudentDAO studentDAO, LecturerDAO lecturerDAO, List<User> partialList) {
        List<User> allUsers = new ArrayList<>();
        for(User nonCastableUser : partialList){
            User castable = nonCastableUser;
            if(nonCastableUser.getType() == STUDENT){
                castable = studentDAO.getStudentWithEmail(nonCastableUser.getEmail());
            }else if(nonCastableUser.getType() == LECTURER){
                castable = lecturerDAO.getLecturerWithEmail(nonCastableUser.getEmail());
            }

            allUsers.add(castable);
        }
        return allUsers;
    }
}
