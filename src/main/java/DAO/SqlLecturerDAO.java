package DAO;

import DAO.Interfaces.LecturerDAO;
import Model.Lecturer;
import Model.Subject;
import Model.User;

import java.util.List;

public class SqlLecturerDAO implements LecturerDAO {

    private ConnectionPool pool;

    public SqlLecturerDAO(ConnectionPool pool) {
        this.pool = pool;
    }


    @Override
    public boolean addLecturer(Lecturer lecturer) {
        return false;
    }

    @Override
    public boolean removeLecturer(Lecturer lecturer) {
        return false;
    }

    @Override
    public Lecturer getLecturerByUser(User user) {
        return null;
    }

    @Override
    public List<Subject> getAllSubjects(Lecturer lecturer) {
        return null;
    }
}
