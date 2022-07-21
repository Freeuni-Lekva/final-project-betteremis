package DAO.Interfaces;

public interface PrerequisitesDAO {
    /*
    email is email of student; subjectName is name of subject;
    This function tells us if this student (with this email) can choose this subject;
     */
    public boolean canThisSubjectChosenByStudent(String email,String subjectName);
    /*
    subjectName is name of subject for which we are interested to know it's prerequisites;
    this function gives String of prerequisites;( P.S. currently ID's have to change it into Names)
     */
    public String getSubjectPrerequisitesByName(String subjectName);
    /*
      subjectName is name of subject for which we are interested to update it's prerequisites;
      prerequisites is String of prerequisites written with names; (P.S. must transform into IDs)
      this function updates prerequisites of subject;

     */
    public void updatePrerequisite(String subjectName,String prerequisites);
}
