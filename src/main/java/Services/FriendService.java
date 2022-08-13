package Services;

import DAO.Interfaces.FriendsDAO;
import Model.User;

public class FriendService {

    /**
     * Checks whether one user has sent friend request to the other, and if so,
     * makes them friends.
     * @param user1 User Object
     * @param user2 User Object
     * @param @NotNull FriendsDAO
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
     * @NotNull @param dao FriendsDAO object
     */
    public boolean declineRequest(User user1, User user2, FriendsDAO dao){
        if(!dao.isInRequests(user1, user2)) return false;
        dao.removeRequest(user1, user2);
        return true;
    }
}
