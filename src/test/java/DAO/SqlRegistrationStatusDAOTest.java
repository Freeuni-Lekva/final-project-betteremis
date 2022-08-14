package DAO;

import DAO.Interfaces.RegistrationStatusDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqlRegistrationStatusDAOTest {
    static ConnectionPool pool;
    static RegistrationStatusDAO registrationStatusDAO;

    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        registrationStatusDAO = new SqlRegistrationStatusDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
    }

    @Test
    public void SqlRegistrationStatusDAOTest() throws SQLException {
        assertFalse(registrationStatusDAO.registrationStatus());
        assertTrue(registrationStatusDAO.openRegistration());
        assertTrue(registrationStatusDAO.registrationStatus());
        assertTrue(registrationStatusDAO.openRegistration());
        assertTrue(registrationStatusDAO.closeRegistration());
        assertFalse(registrationStatusDAO.registrationStatus());
    }
}
