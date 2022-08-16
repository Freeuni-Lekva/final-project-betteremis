package DAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class SqlTokenTest {

    private SqlTokenDAO dao;
    @BeforeAll
    public static void setUp() throws FileNotFoundException {
        ConnectionPool pool = new ConnectionPool(5, true);
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
    }

    @Test
    public void test1(){
        ConnectionPool pool = new ConnectionPool(1,true);

        dao = new SqlTokenDAO(pool);
        String token = "assssssss";
        dao.addToken(token);
        assertTrue(dao.isValidToken(token));
        assertTrue(!dao.isValidToken("bla"));
    }

    @Test
    public void test2(){
        ConnectionPool pool = new ConnectionPool(3, true);
        dao = new SqlTokenDAO(pool);
        for(int i = 0 ; i < 10; i++){
            assertEquals(true, dao.addToken("Token#:" + i));
        }

        for(int i = 0 ; i < 10; i++){
            assertEquals(false, dao.addToken("Token#:" + i));
        }

        for(int i = 0; i < 10; i++){
            assertEquals(true, dao.isValidToken("Token#:" + i));
            assertEquals(false, dao.isValidToken("Token#:"));
            assertEquals(false, dao.isValidToken("Token#:" + (i + 10)));
        }
    }
}
