package DAO.Interfaces;

import Model.Student;
import Model.Subject;

import java.util.ArrayList;
import java.util.Map;

public interface SubjectHistoryDAO {


    /**
     * This method returns all subjects taken from the SUBJECTS_HISTORY table.
     * This method has 3 modes. Depending on which it's going to return different results.
     * @param student Student for searching in history table.
     * @param mode For incomplete subjects, mode is -1. For all subjects (completed or not), mode is 0. For completed subjects, mode is 1.
     * @return Map<Semester, Arraylist<SubjectsDoneByGivenStudent> > Map containing subjects for given student in the following order,
     * 1 (Semester 1) -> Subjects taken in this semester...
     */

    Map<Integer, ArrayList<Subject>> getAllSubjects(Student student, int mode);


    /**
     * Adds row in SubjectsHistory table with the given student and subject. By default
     * grade is set to 0 and taken subject is marked as incomplete.
     * @param st Student object
     * @param sb Subject object
     * @return returns -1 if subject history could not be updated. Returns ID of added row otherwise.
     */
    int addStudentAndSubject(Student st, Subject sb);

    /**
     * Updates the grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentGrade(Student st, Subject sb, int grade);


    /**
     * Wrapper for getting completed subjects.
     * @param st
     * @return returns all the subjects this student has completed.
     */
    Map<Integer, ArrayList<Subject>> getCompletedSubjects(Student st);

    /**
     * Wrapper for getting incomplete subjects.
     * @param st
     * @return returns all the subjects this student has not yet completed.
     */
    Map<Integer, ArrayList<Subject>> getIncompleteSubjects(Student st);

    /**
     * Checks is given subject is completed by given student.
     * @param st
     * @param sb
     * @return true if subject is completed by the student, false otherwise.
     */
    boolean isCompleted(Student st, Subject sb);

    /**
     * Gets the grade given student has in a given subject.
     * @param st
     * @param sb
     * @return decimal number representing the grade. -1 is returned if either error has happened or
     * student subject pair could not be found in history.
     */
    double getGrade(Student st, Subject sb);

    /**
     * Updates the IsCompleted column in SUBJECTS_HISTORY table.
     * @param st
     * @param sb
     * @param flag
     * @return true if process was successful, false otherwise.
     */
    boolean updateCompletedColumn(Student st, Subject sb, boolean flag);

    /**
     * Removes student and subject from SUBJECTS_HISTORY table.
     * @param st
     * @param sb
     * @return true if removed successfully, false otherwise.
     */
    boolean removeStudentAndSubject(Student st, Subject sb);

}
