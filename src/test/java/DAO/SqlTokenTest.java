package DAO;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class SqlTokenTest {

    private SqlTokenDAO dao;


    @Test
    public void test1(){
        ConnectionPool pool = new ConnectionPool(1,true);

        dao = new SqlTokenDAO(pool);
        String token = "assssssss";
        dao.addToken(token);
        assertTrue(dao.isValidToken(token));
        assertTrue(!dao.isValidToken("bla"));
    }

}
