package DAO.Interfaces;

import Model.*;

import java.util.*;

public interface FriendsDAO {


    /**
     *
     * @param user1
     * @param user2
     * @param mode is true : adds in FRIENDS table. mode if false : adds in FRIEND_REQS table.
     * @return
     */

    boolean addFriend(User user1, User user2, boolean mode);

    /**
     *
     * @param user1
     * @param user2
     * @param mode is true : removes in FRIENDS table. mode if false : removes in FRIEND_REQS table.
     * @return
     */
    boolean removeFriends(User user1, User user2, boolean mode);


    /**
     *
     * @param user
     * @param mode is true: returns all friends of the given user.
     *            mode if false: returns all the requests of the given user.
     * @return
     */
    List<User> getAllFriends(User user, boolean mode);

    /**
     *
     * @param user1
     * @param user2
     * @param mode is true: returns if user2 is user1's friend
     *             mode false: returns if user2 is user1's friend request
     *
     * @return
     */
    public boolean areFriends(User user1, User user2, boolean mode);


}
