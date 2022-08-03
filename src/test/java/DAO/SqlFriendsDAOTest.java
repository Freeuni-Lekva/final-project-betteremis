package DAO;

import DAO.Interfaces.FriendsDAO;
import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.UserDAO;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlFriendsDAOTest {
    static User stud1, stud2, stud3;
    static UserDAO usrDAO;
    static StudentDAO studDAO;
    static FriendsDAO friendsDAO;
    static private ConnectionPool pool;
    @BeforeAll
    static void initAll() {
        pool = new ConnectionPool(10, true);
        try {
            TestingUtils.emptyTables(pool.getConnection());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        usrDAO = new SqlUserDAO(pool);
        studDAO = new SqlStudentDAO(pool);
        friendsDAO = new SqlFriendsDAO(pool);
        stud1 = new User("dbena20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "bena20".toCharArray()), USERTYPE.STUDENT);
        stud2 = new User("garse20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "arsena33".toCharArray()), USERTYPE.STUDENT);
        stud3 = new User("nitsim20@freeuni.edu.ge", BCrypt.withDefaults().hashToString(12, "tsimtsim".toCharArray()), USERTYPE.STUDENT);
        stud1 = new Student(stud1.getEmail(), stud1.getPasswordHash(), stud1.getType(), "dato", "benashvili", "macs", 4, GENDER.MALE, Date.valueOf("2002-08-06"),
                "olive street", STATUS.ACTIVE, "22 public", 26, 3.32, new BigInteger("599595919"), "Kings", 1);
        stud2 = new Student(stud2.getEmail(), stud2.getPasswordHash(), stud2.getType(), "giorgi", "arsenadze", "macs", 4, GENDER.MALE, Date.valueOf("2002-02-12"),
                "apple street", STATUS.ACTIVE, "1231 public", 35, 3.99, new BigInteger("599777779"), "Kings", 2);
        stud3 = new Student(stud3.getEmail(), stud3.getPasswordHash(), stud3.getType(), "nikoloz", "tsimakuridze", "macs", 4, GENDER.FEMALE, Date.valueOf("2002-09-25"),
                "grape street", STATUS.ACTIVE, "2223 public", 15, 3.89, new BigInteger("599123919"), "Kings", 3);
        usrDAO.addUser(stud1);
        usrDAO.addUser(stud2);
        usrDAO.addUser(stud3);

        studDAO.addStudent((Student) stud1);
        studDAO.addStudent((Student) stud2);
        studDAO.addStudent((Student) stud3);
    }

    /**
     * Tests if adding friends to user works well
     */
    @Test
    @Order(1)
    public void testAddingFriends() {
        initAll();
        assertEquals(true, friendsDAO.AddFriend(stud1, stud2, false));
        assertEquals(true, friendsDAO.AddFriend(stud2, stud3, false));
        assertEquals(true, friendsDAO.AddFriend(stud3, stud1, false));
        assertEquals(false, friendsDAO.AddFriend(stud1, stud2, false));
        assertEquals(false, friendsDAO.AddFriend(stud2, stud3, false));
        assertEquals(false, friendsDAO.AddFriend(stud3, stud1, false));
        assertEquals(true, friendsDAO.AddFriend(stud1, stud2, true));
        assertEquals(true, friendsDAO.AddFriend(stud2, stud3, true));
        assertEquals(true, friendsDAO.AddFriend(stud3, stud1, true));
        assertEquals(false, friendsDAO.AddFriend(stud1, stud2, false));
        assertEquals(false, friendsDAO.AddFriend(stud1, stud2, true));
    }
    @Test
    @Order(2)
    public void testRemovingFriends() {
        initAll();
        testAddingFriends();
        assertEquals(false, friendsDAO.Remove(stud1, stud2, false));
        assertEquals(false, friendsDAO.Remove(stud1, stud2, false));
        assertEquals(false, friendsDAO.AddFriend(stud1, stud2, true));
        assertEquals(true, friendsDAO.Remove(stud1, stud2, true));
        assertEquals(true, friendsDAO.AddFriend(stud1, stud2, true));
    }
    @Test
    @Order(3)
    public void testFriendList(){
        initAll();
        List<String> L=new ArrayList<>();
        L.add(stud2.getEmail());
        L.add(stud3.getEmail());
        friendsDAO.AddFriend(stud1, stud2, true);
        friendsDAO.AddFriend(stud1, stud3, true);
        List<User >L1=friendsDAO.GetAllFriends(stud1,true);
        List<String>L2=new ArrayList<String >();
        for(int i=0;i<L1.size();i++){
            L2.add(L1.get(i).getEmail());
        }
        assertTrue(L.equals(L2));

    }
}


