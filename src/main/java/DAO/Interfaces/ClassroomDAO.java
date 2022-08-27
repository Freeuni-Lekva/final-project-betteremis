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
     * @param studentEmail
     * @param asc
     * @return all classrooms in which given student was (is) involved. if asc is true classrooms are sorted in ascending order.
     */
    List<Classroom> getClassroomsByStudent(String studentEmail, boolean asc);

    /**
     *
     * @param classroom
     * @return true if the given classroom has been added in CLASSROOMS database successfully. Otherwise: false.
     */
    int addClassroom(Classroom classroom);


    /**
     *
     * @param classroom
     * @return true if the given classroom has been removed from CLASSROOMS database successfully. Otherwise: false.
     */
    boolean removeClassroom(Classroom classroom);

    /**
     * Gets classroom by subject name and given semester. This can be done because this pair of fields uniquely identifies a classroom.
     * @param name Subject name
     * @param semester Semester
     * @return Classroom of given subject in a given semester, null if some kind of error happened.
     */
    Classroom getClassroomBySubjectNameAndSemester(String name, int semester);

    /**
     * Gets all the existing classrooms.
     * @return List of existing classroomsm, null if some kind of error happened.
     */
    List<Classroom> getAllClassrooms();
}
