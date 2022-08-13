package DAO.Interfaces;

import Model.*;

import java.util.*;

public interface FriendsDAO {


    /**
     * Adds a pair of users in FRIENDS table. Friendship in this table is bidirectional.
     * @param user1
     * @param user2
     * @return true if successfully added in database, false-otherwise.
     */
    boolean addFriend(User user1, User user2);


    /**
     * Adds a request in FRIEND_REQUESTS table.
     * @param user1
     * @param user2
     * @return true if successfully added in database, false-otherwise.
     */
    boolean addRequest(User user1, User user2);


    /**
     * Removes a pair of users in FRIENDS table. Friendship will end for both users.
     * @param user1
     * @param user2
     * @return true if successfully removed in database, false-otherwise.
     */
    boolean removeFriends(User user1, User user2);

    /**
     * Removes a request sent by user2 to user1.
     * @param user1
     * @param user2
     * @return
     */
    boolean removeRequest(User user1, User user2);


    /**.
     * @param user
     * @return all the friends of the given user
     */
    List<User> getAllFriends(User user);


    /**
     * @param user
     * @return all the users who sent a request to the given user.
     */
    List<User> getAllRequests(User user);


    /**
     * @param user1
     * @param user2
     * @return True if user1 and user2 are friends. Here, friendship is bidirectional. False-Otherwise.
     */
     boolean areFriends(User user1, User user2);


    /**
     * @param user1
     * @param User2
     * @return true if user2 is in user1's friendRequest list (if user2 has ever sent request to user1). False-otherwise.
     */
     boolean isInRequests(User user1, User User2);

}
