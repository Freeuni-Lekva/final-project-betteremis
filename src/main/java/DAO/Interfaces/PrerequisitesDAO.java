package DAO.Interfaces;

public interface PrerequisitesDAO {

    /**
     * This function tells us if this student (with this email) can choose this subject;
     * email is email of student; subjectName is name of subject;
     */
    boolean canThisSubjectChosenByStudent(String email,String subjectName);

    /**
     * This function gives String of prerequisites;
     * @param subjectName  is name of subject for which we are interested to know it's prerequisites;
     */
    String getSubjectPrerequisitesByName(String subjectName);

    /**
     * This function updates prerequisites of subject;
     * @param subjectName  Name of subject for which we are interested to update it's prerequisites;
     * @param prerequisites  String of prerequisites written with names;
     */
    void updatePrerequisite(String subjectName,String prerequisites);
}
