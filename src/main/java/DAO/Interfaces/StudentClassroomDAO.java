package DAO.Interfaces;

import Model.Classroom;
import Model.Student;

import java.util.List;

public interface StudentClassroomDAO {

    /**
     * This method gets all students in a classroom.
     * @param ClassroomID
     * @return a list containing all the students in a given classroom, null is returned if error has happened.
     */
    List<Student> getStudentsInClassroom(int ClassroomID);

    /**
     *
     * @param studentEmail
     * @param ClassroomID
     * @return ID if the given classroom and student have been added in STUDENT_CLASSROOMS database successfully. Otherwise: -1.
     */
    int addStudentAndClassroom(String studentEmail, int ClassroomID);


    /**
     *
     * @param studentEmail
     * @param ClassroomID
     * @return true if the given classroom and student have been removed from STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeStudentAndClassroom(String studentEmail, int ClassroomID);
}
