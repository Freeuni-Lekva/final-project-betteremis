package DAO.Interfaces;

import Model.*;

import java.util.*;

public interface ClassroomDAO {


    /**
     *
     * @param lecturerEmail
     * @param asc
     * @return all classrooms created by given lecturer. if asc is true classrooms are sorted in ascending order.
     */
    List<Classroom> getClassroomsByLecturer(String lecturerEmail, boolean asc);

    /**
     *
     * @param subjectName
     * @param asc
     * @return all classrooms created for given subject. if asc is true classrooms are sorted in ascending order.
     */
    List<Classroom> getClassroomsBySubject(String subjectName, boolean asc);

    /**
     *
     * @param studentEmail
     * @param asc
     * @return all classrooms in which given student was (is) involved. if asc is true classrooms are sorted in ascending order.
     */
    List<Classroom> getClassroomsByStudent(String studentEmail, boolean asc);

    /**
     *
     * @param studentEmail
     * @param classroom
     * @return true if the given classroom and student have been added in STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean addStudentAndClassroom(String studentEmail, Classroom classroom);

    /**
     *
     * @param classroom
     * @return true if the given classroom has been added in CLASSROOMS database successfully. Otherwise: false.
     */
    boolean addClassroom(Classroom classroom);

    /**
     *
     * @param studentEmail
     * @param classroom
     * @return true if the given classroom and student have been removed from STUDENT_CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeStudentAndClassroom(String studentEmail, Classroom classroom);

    /**
     *
     * @param classroom
     * @return true if the given classroom has been removed from CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeClassroom(Classroom classroom);

}
