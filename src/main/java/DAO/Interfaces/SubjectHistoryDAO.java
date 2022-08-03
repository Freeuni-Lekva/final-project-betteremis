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
     * Updates the quiz grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentQuiz(Student st, Subject sb, double grade);

    /**
     * Updates the homework grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentHomework(Student st, Subject sb, double grade);

    /**
     * Updates the project grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentProject(Student st, Subject sb, double grade);

    /**
     * Updates the presentation grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentPresentation(Student st, Subject sb, double grade);

    /**
     * Updates the midterm grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentMidterm(Student st, Subject sb, double grade);

    /**
     * Updates the final grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentFinal(Student st, Subject sb, double grade);

    /**
     * Updates the fx grade of the student in a given subject. If method returns false this means SQLException happened.
     * One of the possible such exceptions is grade check constraint fail. Setting -1 here indicates that this part of scoring isn't used.
     * @param st Student object
     * @param sb Subject object
     * @param grade Grade to be set
     * @return true if row was successfully updated, false otherwise.
     */
    boolean updateStudentFX(Student st, Subject sb, double grade);

    /**
     * returns the total score of a student.
     * @param st
     * @param sb
     * @return total score of a student.
     */
    double getSumOfScores(Student st, Subject sb);

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
     * Gets the grade given student has in a given subject. Since grade is distributed over different tasks, a map is returned
     * with appropriate key value pairs.
     * @param st
     * @param sb
     * @return null is returned if either error has happened or
     * student subject pair could not be found in history. Otherwise, a map is returned which has the following key value pairs :
     * Mapping.QUIZ - quiz score
     * Mapping.HOMEWORK - homework score
     * Mapping.PROJECT - project score
     * Mapping.PRESENTATION - presentation score
     * Mapping.MIDTERM - midterm score
     * Mapping.FINAL - final score
     * Mapping.FX - fx score
     * Scores may be -1, which means that kind of task isn't part of the subject's scoring system.
     */
    Map<String, Double> getGrade(Student st, Subject sb);

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
