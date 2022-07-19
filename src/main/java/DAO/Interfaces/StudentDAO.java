package DAO.Interfaces;

import Model.*;

public interface StudentDAO {
    /**
     * Adds the student into the database.
     * @param student
     * @return true if added successfully, and false otherwise.
     */
    int addStudent(Student student);

    /**
     * Removes the student from the database.
     * @param student
     * @return true if removed successfully, false otherwise.
     */
    boolean removeStudent(Student student);

    /**
     * Finds the student with the given id and returns it as a Student object.
     * @param user id of a student.
     * @return Student with the given ID.
     */
    Student getStudentByUser(User user);
}
