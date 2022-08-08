package DAO.Interfaces;

import Model.Message;
import Model.User;

import java.util.Date;
import java.util.List;

public interface MailDAO {

    /**
     *  Adds a mail sent by a user into the table.
     * @param message contains sender, receiver , message and time.
     * @return true if added successfully, false otherwise.
     */
    boolean addMail(Message message);

    /**
     * Deletes all mails between two users.
     * @param sender Sender's email
     * @param receiver Receiver's email
     * @return number of rows affected, -1 if error happened.
     */
    int deleteAllMails(String sender, String receiver);


    /**
     * Gets all the messages between two users in ascending or descending order.
     * @param sender Sender's email
     * @param receiver Receiver's email
     * @param asc Sort order of results
     * @return null if some kind of error happened, empty list if nothing was retrieved from database, or List containing messages between users ascending or descending order.
     */
    List<Message> getAllMails(String sender, String receiver, boolean asc);

}
