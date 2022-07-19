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

    Lecturer getLecturerByUser(User user);

    List<Subject> getAllSubjects(Lecturer lecturer);

}
