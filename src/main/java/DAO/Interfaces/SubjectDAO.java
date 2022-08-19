package DAO.Interfaces;

import Model.Student;
import Model.Subject;

import java.util.List;

public interface SubjectDAO {
    /**
     * Adds a new subject in the database, if there isn't a subject with this name.
     * This is because subject's name is unique. For this reason information for two lecturers' teaching the same subject
     * cannot be saved in this table.
     * @param subject Subject to be added.
     * @return returns ID of added subject if added successfully, -1 otherwise.
     */
    int addSubject(Subject subject);

    /**
     * Removes the subject with a given name. Since the subject is unique in table only name is sufficient.
     * @param subjectName Name of the subject.
     * @return true if subject was removed successfully, false otherwise.
     */
    boolean removeSubject(String subjectName);

    /**
     * Given the subject name, returns its respective subject object.
     * @param subjectName Name of the subject.
     * @return Subject object containing the information. If subject could not be retrieved null is returned.
     */
    Subject getSubjectByName(String subjectName);

    String getSubjectNameByID(int id);
    /**
     * Given the subject name, gets subjects ID from the table.
     * @param name Name of the subject
     * @return ID or the subject if found, -1 otherwise.
     */
    int getSubjectIDByName(String name);

    /**
     * Gets all the added subjects from the database.
     * @return The list of the subjects that exist in the database.
     * If no subjects exist in the database empty list is returned.
     * If some kind of error happened null is returned.
     */
    List<Subject> getAllSubjects();
}
