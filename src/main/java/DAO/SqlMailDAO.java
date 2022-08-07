package DAO;

import DAO.Interfaces.MailDAO;
import DAO.Interfaces.UserDAO;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqlMailDAO implements MailDAO {

    final String FORMAT = "DD-MM-YYYY HH24:MI:SS";

    ConnectionPool pool;

    public SqlMailDAO(ConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public boolean addMail(User sender, User receiver, String message) {
        return addMail(sender.getEmail(), receiver.getEmail(), message);
    }

    @Override
    public boolean addMail(String sender, String receiver, String message) {
        UserDAO userDAO = new SqlUserDAO(pool);
        int IDSender = userDAO.getIDByEmail(sender);
        int IDReceiver = userDAO.getIDByEmail(receiver);

        if(IDSender == -1 || IDReceiver == -1)
            return false;

        Connection conn = pool.getConnection();

        try{
            String statement = "INSERT INTO MAIL (UserIDFrom, UserIDTo, Message) VALUE (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, IDSender);
            ps.setInt(2, IDReceiver);
            ps.setString(3, message);
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
    public int deleteAllMails(User sender, User receiver) {
        return deleteAllMails(sender.getEmail(), receiver.getEmail());
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
    public List<String> getAllMails(User sender, User receiver, boolean asc) {
        return getAllMails(sender.getEmail(), receiver.getEmail(), asc);
    }

    @Override
    public List<String> getAllMails(String sender, String receiver, boolean asc) {
        UserDAO userDAO = new SqlUserDAO(pool);
        int IDSender = userDAO.getIDByEmail(sender);
        int IDReceiver = userDAO.getIDByEmail(receiver);

        if(IDSender == -1 || IDReceiver == -1)
            return null;

        List<String> result = new ArrayList<>();

        Connection conn = pool.getConnection();

        try{
            String statement = "SELECT * FROM MAIL WHERE (UserIDFrom = ? AND UserIDTo = ?) OR (UserIDFrom = ? AND UserIDTo = ?) ORDER BY SendDate "+ (asc ? "asc" : "desc") +";";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, IDSender);
            ps.setInt(2, IDReceiver);
            ps.setInt(3, IDReceiver);
            ps.setInt(4, IDSender);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result.add(rs.getString(5));
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
