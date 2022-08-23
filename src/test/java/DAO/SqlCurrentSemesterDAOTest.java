package DAO;

import DAO.Interfaces.*;
import Model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.SqlScriptRunner;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SqlCurrentSemesterDAOTest {
    static ConnectionPool pool;
    static CurrentSemesterDAO currentSemesterDAO;
    @BeforeAll
    public static void setUp(){
        pool = new ConnectionPool(1, true);
        currentSemesterDAO = new SqlCurrentSemesterDAO(pool);
    }

    @BeforeEach
    public void clean() throws FileNotFoundException {
        Connection conn = pool.getConnection();
        SqlScriptRunner.emptyTables(conn);
        pool.releaseConnection(conn);
    }

    @Test
    public void SqlCurrentSemesterDAOTest1(){
        assertEquals(1, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.incrementSemester());
        assertEquals(2, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.incrementSemester());
        assertEquals(3, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.incrementSemester());
        assertEquals(4, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.incrementSemester());
        assertEquals(5, currentSemesterDAO.getCurrentSemester());
    }

    @Test
    public void SqlCurrentSemesterDAOTest2(){
        assertEquals(1, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.incrementSemester());
        assertEquals(2, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.decrementSemester());
        assertEquals(1, currentSemesterDAO.getCurrentSemester());
        assertFalse(currentSemesterDAO.decrementSemester());
    }

    @Test
    public void SqlCurrentSemesterDAOTest3(){
        assertEquals(1, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.setSemester(10));
        assertEquals(10, currentSemesterDAO.getCurrentSemester());
        assertTrue(currentSemesterDAO.decrementSemester());
        assertEquals(9, currentSemesterDAO.getCurrentSemester());
        assertFalse(currentSemesterDAO.setSemester(0));
        assertFalse(currentSemesterDAO.setSemester(-1));
    }
}
