package DAO.Interfaces;

import Model.Student;
import Model.Subject;

import java.util.ArrayList;
import java.util.Map;

public interface SubjectHistoryDAO {


    /**
     * This method returns all subjects taken (completed or not) from the SUBJECTS_HISTORY table.
     * @param student Student for searching in history table.
     * @return Map<Semester, Arraylist<SubjectsDoneByGivenStudent> > Map containing subjects for given student in the following order,
     * 1 (Semester 1) -> Subjects taken in this semester...
     */

    Map<Integer, ArrayList<Subject>> getAllSubjects(Student student);


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
}
