package DAO.Interfaces;

public interface PrerequisitesDAO {
    public boolean canThisSubjectChosenByStudent(String email,String subjectName);
    public String getSubjectPrerequisitesByName(String subjectName);
    public void updatePrerequisite(String subjectName,String prerequisites);
}
