package DAO;

import java.sql.*;
import java.util.ArrayList;

public class SqlFriendsDao {
    private final ConnectionPool pool;

    public SqlFriendsDao(ConnectionPool pool) {
        this.pool = pool;
    }
    public ArrayList<Integer> getFriendsIDs(int userID) {
        Connection conn = pool.getConnection();
        ArrayList<Integer>friendList = null;
        try{
            String statement = "SELECT S.FRIENDID FROM SUBJECTS S WHERE S.USERID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            int temp=0;
            while(rs.next()){
                temp=rs.getInt(1);
                friendList.add(temp);
            }
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
        }
        return friendList;
    }
    public boolean removeFriend(int userID,int friendID){
        Connection conn = pool.getConnection();
        boolean result = false;
        try{
            String statement = "DELETE FROM FRIENDS F WHERE USERID = ? and FRIENDID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, userID);
            ps.setInt(2,friendID);
            int updateResult = ps.executeUpdate();
            result = updateResult == 1? true : false;
        }catch (SQLException e){
            e.printStackTrace();
            pool.releaseConnection(conn);
            result = false;
        }

        pool.releaseConnection(conn);
        return result;
    }
    public int addFriend(int userID,int friendID) throws SQLException {
        Connection conn = pool.getConnection();
        int result = -1;
        String statement = "INSERT INTO FRIENDS (userID, friendID) VALUES (?, ?);";
        PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, userID);
        ps.setInt(2, friendID);
        if (ps.executeUpdate() == 1) {
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            result = keys.getInt(1);
        }
        pool.releaseConnection(conn);
        return result;
    }
}
