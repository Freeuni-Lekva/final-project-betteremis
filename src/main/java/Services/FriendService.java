package Services;

import DAO.Interfaces.FriendsDAO;
import DAO.Interfaces.UserDAO;
import Model.User;

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
    public RequestResult sendRequest(User sender, String receiverEmail, FriendsDAO friendsDAO, UserDAO userDAO){
        User receiver = userDAO.getUserByEmail(receiverEmail);
        if(receiver == null) return REQUEST_USER_NOT_FOUND;
        if(receiver.equals(sender)) return REQUEST_SAME_USER;
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
}
