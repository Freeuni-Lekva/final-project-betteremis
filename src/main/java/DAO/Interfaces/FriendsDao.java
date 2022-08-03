package DAO.Interfaces;

import java.util.ArrayList;

public interface FriendsDao {
    public ArrayList<Integer> getFriendsIDs(int userID);
    public int addFriend(int userID,int friendID);
    public boolean removeFriend(int userID,int friendID);
}
