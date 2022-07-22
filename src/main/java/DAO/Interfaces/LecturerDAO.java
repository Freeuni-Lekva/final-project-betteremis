package DAO.Interfaces;
import Model.*;

import java.util.List;

public interface LecturerDAO {

    /**
     * Adds the lecturer into the database.
     * @param lecturer
     * @return true if added successfully, and false otherwise.
     */
    boolean addLecturer(Lecturer lecturer);

    /**
     * Removes the lecturer from the database.
     * @param lecturer
     * @return true if removed successfully, false otherwise.
     */
    boolean removeLecturer(Lecturer lecturer);

    /**
     * Finds the lecturer in the database given his/her email,
     * which is supposed to be unique for each lecturer.
     * @param email Email of the lecturer
     * @return Lecturer object found in the database, or null if it doesn't exist.
     */
    Lecturer getLecturerWithEmail(String email);

    /**
     * Finds all the subjects taught by the lecturer with the given email.
     * @param email Email of the lecturer
     * @return List of subjects taught by the lecturer.
     */
    List<Subject> getAllSubjects(String email);

}
