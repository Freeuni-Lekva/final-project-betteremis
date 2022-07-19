package DAO;

import DAO.Interfaces.StudentDAO;
import Model.*;

public class SqlStudentDAO implements StudentDAO {

    private ConnectionPool pool;
    public SqlStudentDAO(ConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public int addStudent(Student student) {
        return 0;
    }

    @Override
    public boolean removeStudent(Student student) {
        return false;
    }

    @Override
    public Student getStudentByUser(User user) {
        return null;
    }
}
