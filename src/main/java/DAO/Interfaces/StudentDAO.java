package DAO.Interfaces;

import Model.*;

import java.util.ArrayList;
import java.util.Map;

public interface StudentDAO {
    /**
     * Adds the student into the database.
     * @param student
     * @return StudentID if added to the database, -1 - otherwise.
     */
    int addStudent(Student student);

    /**
     * Finds the student with the given id and returns it as a Student object.
     * @param email email of a student.
     * @return Student with the given ID.
     */
    Student getStudentWithEmail(String email);

    /**
     * Sets student's status to INACTIVE.
     * @param student student to Deactivate.
     * @return true - if student status was ACTIVE before the change, false - otherwise.
     */
    boolean terminateStatus(Student student);

    /**
     * This method gets ID of a student with student's UserID.
     * @param UserID of a student
     * @return ID of a student
     */
    int getStudentIDByUserID(int UserID);

    boolean recoverStatus(Student student);

}
