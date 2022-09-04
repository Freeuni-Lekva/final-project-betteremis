package DAO.Interfaces;

public interface SemesterDAO {

    /**
     * Starts the current semester for the university,
     * meaning that lecturers can now edit students' grades.
     * @return false if semester can't be started, and true otherwise.
     */
    boolean startSemester();

    /**
     * Ends the current semester for the university,
     * meaning that lecturers now can't edit students' grades.
     * @return false if for some reason semester can't be ended,
     * and true otherwise.
     */
    boolean endSemester();

    /**
     * Finds whether the semester has been started or not.
     * @return true if semester is in its active phase, and false
     * if it's in its passive phase.
     */
    boolean getSemesterStatus();

    /**
     * Finds the current semester in the database and returns it.
     * This number basically tells how many semesters have passed since
     * the university has created.
     * @return one integer - current semester.
     */
    int getCurrentSemester();

    /**
     * Changes the current semester value according to the parameter.
     * @return true if changed semester successfully, and false otherwise.
     */
    boolean setCurrentSemester(int semester);
}
