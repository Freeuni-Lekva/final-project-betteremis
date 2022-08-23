package DAO.Interfaces;

public interface CurrentSemesterDAO {

    /**
     * This method increments the semester counter by 1.
     * @return true if operation was successful, false otherwise.
     */
    boolean incrementSemester();

    /**
     * This method decrements the semester counter by 1.
     * @return true if operation was successful, false otherwise.
     */
    boolean decrementSemester();

    /**
     * This method gets current semester.
     * @return current semester retrieved from database. -1 if some kind of error happened.
     */
    int getCurrentSemester();

    /**
     * This method sets current semester.
     * @param semester semester to be set
     * @return true if operation was successful, false otherwise.
     */
    boolean setSemester(int semester);

}
