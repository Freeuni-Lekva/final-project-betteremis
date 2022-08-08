package DAO;

import DAO.Interfaces.MessagesDAO;
import Model.Message;
import Model.USERTYPE;
import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class SqlMessagesDAO implements MessagesDAO {

    private ConnectionPool pool;


    public SqlMessagesDAO(ConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public int addMessage(Message message) {
        Connection conn = pool.getConnection();
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO MESSAGES (SenderEmail, ReceiverEmail, Message, DateOfSend) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,message.getSender());
            stm.setString(2, message.getReceiver());
            stm.setString(3, message.getMessage());
            stm.setDate(4, sqlDate);
            int added = stm.executeUpdate();
            if(added != 1)  return -1;
            ResultSet rs = stm.getGeneratedKeys();
            if(rs.next())
                return rs.getInt(1);
            return -1;
        } catch (SQLException e) {
            return -1;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean deleteMessage(Message message) {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM USERS WHERE SenderEmail=? AND ReceiverEmail = ? AND  Message=?;");
            stm.setString(1, message.getSender()); stm.setString(2, message.getReceiver());
            stm.setString(3, message.getMessage());
            int added = stm.executeUpdate();
            return added == 1;
        } catch (SQLException e) {
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean deleteConversation(String first, String second) {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM USERS WHERE SenderEmail=? AND ReceiverEmail = ?;");
            stm.setString(1, first); stm.setString(2, second);
            int added = stm.executeUpdate();
            return added == 1;
        } catch (SQLException e) {
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean deleteAllMessageByUser(String userEmail) {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM USERS WHERE SenderEmail=?;");
            stm.setString(1, userEmail);
            int added = stm.executeUpdate();
            return added == 1;
        } catch (SQLException e) {
            return false;
        }finally{
            pool.releaseConnection(conn);
        }
    }

    @Override
    public ArrayList<Message> getAllMessagesByUser(String userEmail) {
        ArrayList<Message> res = new ArrayList<>();
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT SenderEmail, ReceiverEmail, Message, DateOfSend FROM MESSAGES WHERE SenderEmail=?;");
            stm.setString(1, userEmail);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                res.add(new Message(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4)));
            }
            return res;
        } catch (SQLException e) {
            return null;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public ArrayList<Message> getAllMessageBetweenUsers(String first, String second) {
        ArrayList<Message> res = new ArrayList<>();
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT SenderEmail, ReceiverEmail, Message, DateOfSend FROM MESSAGES WHERE SenderEmail=? AND ReceiverEmail = ?;");
            stm.setString(1, first);
            stm.setString(2, second);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                res.add(new Message(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4)));
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            pool.releaseConnection(conn);
        }
    }

}
