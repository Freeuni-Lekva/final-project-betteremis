package DAO.Interfaces;

import Model.Classroom;

public interface StudentClassroomDAO {

    /**
     *
     * @param studentEmail
     * @param classroom
     * @return true if the given classroom and student have been added in STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean addStudentAndClassroom(String studentEmail, Classroom classroom);


    /**
     *
     * @param studentEmail
     * @param classroom
     * @return true if the given classroom and student have been removed from STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeStudentAndClassroom(String studentEmail, Classroom classroom);
}
