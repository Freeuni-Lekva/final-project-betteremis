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


    int getStudentIDByEmail(String studentEmail);
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

    /**
     * Gets student from the database with a given ID
     * @param ID
     * @return Student object with required ID, null if some kind of error has happened.
     */
    Student getStudentByID(int ID);

    boolean recoverStatus(Student student);

    /**
     * Updates every student's current semester.
     * @param semester
     * @return true if everything is successful, false if some kind of error happened.
     */
    boolean updateStudentCurrentSemester(int semester);
}
