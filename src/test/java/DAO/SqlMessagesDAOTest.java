package DAO;

import DAO.Interfaces.MessagesDAO;
import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.UserDAO;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlMessagesDAOTest {

    static User user1, user2, user3;
    static UserDAO usrDAO;
    static MessagesDAO messagesDAO;


    static String[] messages ;

    @BeforeAll
    static void initAll(){
        ConnectionPool pool = new ConnectionPool(10, true);
        try {
            TestingUtils.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        messages = new String[]{"hello", "hi", "how are you", "hm, not too bad, you?", "Hah, very nice"};
        usrDAO = new SqlUserDAO(pool);
        messagesDAO = new SqlMessagesDAO(pool);
        user1 = new User("dbena20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "bena20".toCharArray()), USERTYPE.STUDENT);
        user2 = new User("garse20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "arsena33".toCharArray()), USERTYPE.STUDENT);
        user3 = new User("ntsim20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "tsimtsim".toCharArray()), USERTYPE.STUDENT);
    }


    @Test
    @Order(1)
    public void testAdd(){
        Message message1 = new Message(user1.getEmail(), user2.getEmail(), messages[1], new Date(20,12,20));
        Message message2 = new Message(user2.getEmail(), user3.getEmail(), messages[2], new Date(20,12,20));
        Message message3 = new Message(user3.getEmail(), user1.getEmail(), messages[3], new Date(20,12,20));
        messagesDAO.addMessage(message1);
        messagesDAO.addMessage(message2);
        messagesDAO.addMessage(message3);
        ArrayList<Message> lst1 = messagesDAO.getAllMessageBetweenUsers(user1.getEmail(), user2.getEmail());
        ArrayList<Message> lst2 = messagesDAO.getAllMessageBetweenUsers(user2.getEmail(), user3.getEmail());
        ArrayList<Message> lst3 = messagesDAO.getAllMessageBetweenUsers(user3.getEmail(), user1.getEmail());
        assertTrue(lst1.get(0).getMessage().equals(messages[1]));
        assertTrue(lst2.get(0).getMessage().equals(messages[2]));
        assertEquals(lst3.get(0).getMessage(), messages[3]);
    }

    // TODO: ADD Tests.
}
