package DAO.Interfaces;

import Model.Classroom;

public interface StudentClassroomDAO {

    /**
     *
     * @param studentEmail
     * @param classroom
     * @return ID if the given classroom and student have been added in STUDENT_CLASSROOMS database successfully. Otherwise: -1.
     */
    int addStudentAndClassroom(String studentEmail, int ClassroomID);


    /**
     *
     * @param studentEmail
     * @param classroom
     * @return true if the given classroom and student have been removed from STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeStudentAndClassroom(String studentEmail, int ClassroomID);
}
