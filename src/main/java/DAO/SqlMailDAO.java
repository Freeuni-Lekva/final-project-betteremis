package DAO;

import DAO.Interfaces.MailDAO;
import DAO.Interfaces.UserDAO;
import Model.Message;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlMailDAO implements MailDAO {
    ConnectionPool pool;

    public SqlMailDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public boolean addMail(Message message) {
        UserDAO userDAO = new SqlUserDAO(pool);
        int IDSender = userDAO.getIDByEmail(message.getSender());
        int IDReceiver = userDAO.getIDByEmail(message.getReceiver());

        if(IDSender == -1 || IDReceiver == -1)
            return false;

        Connection conn = pool.getConnection();

        try{
            String statement = "INSERT INTO MAIL (UserIDFrom, UserIDTo, Message) VALUE (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, IDSender);
            ps.setInt(2, IDReceiver);
            ps.setString(3, message.getMessage());
            int result = ps.executeUpdate();
            return result == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public int deleteAllMails(String sender, String receiver) {
        UserDAO userDAO = new SqlUserDAO(pool);
        int IDSender = userDAO.getIDByEmail(sender);
        int IDReceiver = userDAO.getIDByEmail(receiver);

        if(IDSender == -1 || IDReceiver == -1)
            return -1;

        Connection conn = pool.getConnection();

        try{
            String statement = "DELETE FROM MAIL WHERE (UserIDFrom = ? AND UserIDTo = ?) OR (UserIDFrom = ? AND UserIDTo = ?)";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, IDSender);
            ps.setInt(2, IDReceiver);
            ps.setInt(3, IDReceiver);
            ps.setInt(4, IDSender);
            int result = ps.executeUpdate();
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public List<Message> getAllMails(String sender, String receiver, boolean asc) {
        List<Message> res = getAllMailsHelper(sender, receiver, asc);
        List<Message> secondRes = getAllMailsHelper(receiver, sender, asc);
        res.addAll(secondRes);
        Collections.sort(res);
        if(!asc){
            Collections.reverse(res);
        }
        return res;
    }

    private List<Message> getAllMailsHelper(String sender, String receiver, boolean asc){
        UserDAO userDAO = new SqlUserDAO(pool);
        int IDSender = userDAO.getIDByEmail(sender);
        int IDReceiver = userDAO.getIDByEmail(receiver);

        if(IDSender == -1 || IDReceiver == -1)
            return null;

        List<Message> result = new ArrayList<>();

        Connection conn = pool.getConnection();

        try{
            String statement = "SELECT * FROM MAIL WHERE  UserIDFrom = ? AND UserIDTo = ? ORDER BY SendDate "+ (asc ? "asc" : "desc") +";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, IDSender);
            ps.setInt(2, IDReceiver);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(new Message(sender, receiver,rs.getString(5),rs.getTimestamp(4)));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            pool.releaseConnection(conn);
        }
    }

}
