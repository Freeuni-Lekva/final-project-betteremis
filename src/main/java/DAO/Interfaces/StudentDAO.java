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
     * Finds the student with the given id and returns it as a Student object.
     * @param user id of a student.
     * @return Student with the given ID.
     */
    Student getStudentByUser(User user);

    /**
     * Sets student's status to INACTIVE.
     * @param student student to Deactivate.
     * @return true - if student status was ACTIVE before the change, false - otherwise.
     */
    boolean terminateStatus(Student student);
}
