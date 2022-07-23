package DAO.Interfaces;

import Model.Student;
import Model.Subject;

import java.util.ArrayList;
import java.util.Map;

public interface SubjectHistoryDAO {


    /**
     *
     * @param student
     * @return Map<Semester, Arraylist<SubjectsDoneByGivenStudent> >
     */

    Map<Integer , ArrayList<Subject>> getAllSubjects(Student student);


    /**
     * Adds row in SubjectsHistory table with the given student and subject.
     * @param st
     * @param sb
     */
    void addStudentAndSubject(Student st, Subject sb);


    void updateStudentGrade(Student st, Subject sb, int grade);
}
