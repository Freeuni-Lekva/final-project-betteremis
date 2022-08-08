package DAO.Interfaces;

import Model.Message;

import java.util.ArrayList;

public interface MessagesDAO {

    /**
     *
     * @param message: contains sender , receiver , message and send date.
     * @return Id of the added message. Returns -1 in case of unsuccessful adding trial.
     */
    int addMessage(Message message);


    /**
     * Function deletes one particular message from database.
     * @param message
     * @return false in case of unsuccessful deleting trial. Returns true if message has been deleted successfully.
     */
    boolean deleteMessage(Message message);

    /**
     * Function deletes all messages between two user.
     * @param first
     * @param second
     * @return false in case of unsuccessful deleting trial. Returns true if all messages have been deleted successfully.
     */
    boolean deleteConversation(String first, String second);


    /**
     * Function deletes all messages sent from user having given email.
     * @param userEmail
     * @return false in case of unsuccessful deleting trial. Returns true if all messages have been deleted successfully.
     */
    boolean deleteAllMessageByUser(String userEmail);


    /**
     * Function returns all messages sent from user having given email.
     * @param userEmail
     * @return null in case of unsuccessful trial, otherwise Arraylist, containing all these messages.
     */
    ArrayList<Message> getAllMessagesByUser(String userEmail);

    /**
     *  Function returns all messages sent from user having the first email and sent to user having the second email.
     * @param first
     * @param second
     * @return null in case of unsuccessful trial, otherwise Arraylist, containing all these messages.
     */
    ArrayList<Message> getAllMessageBetweenUsers(String first, String second);

}
