package DAO.Interfaces;

import Model.User;

import java.util.Date;
import java.util.List;

public interface MailDAO {

    /**
     * Adds a mail sent by a user into the table.
     * @param sender Sender User Object
     * @param receiver Receiver User Object
     * @param message
     * @return true if added successfully, false otherwise.
     */
    boolean addMail(User sender, User receiver, String message);

    /**
     * Adds a mail sent by a user into the table.
     * @param sender Sender's email
     * @param receiver Receiver's email
     * @param message
     * @return true if added successfully, false otherwise.
     */
    boolean addMail(String sender, String receiver, String message);

    /**
     * Deletes all mails between two users.
     * @param sender Sender User Object
     * @param receiver Receiver User Object
     * @return number of rows affected, -1 if error happened.
     */
    int deleteAllMails(User sender, User receiver);

    /**
     * Deletes all mails between two users.
     * @param sender Sender's email
     * @param receiver Receiver's email
     * @return number of rows affected, -1 if error happened.
     */
    int deleteAllMails(String sender, String receiver);

    /**
     * Gets all the messages between two users in ascending or descending order.
     * @param sender Sender User Object
     * @param receiver Receiver User Object
     * @param asc Sort order of results
     * @return null if some kind of error happened, empty list if nothing was retrieved from database, or List containing messages between users ascending or descending order.
     */
    List<String> getAllMails(User sender, User receiver, boolean asc);

    /**
     * Gets all the messages between two users in ascending or descending order.
     * @param sender Sender's email
     * @param receiver Receiver's email
     * @param asc Sort order of results
     * @return null if some kind of error happened, empty list if nothing was retrieved from database, or List containing messages between users ascending or descending order.
     */
    List<String> getAllMails(String sender, String receiver, boolean asc);

}
