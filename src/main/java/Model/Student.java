package Model;

import java.util.*;

public class Student extends User {
    private int currentSemester;
    private String school;
    private int creditsDone;
    private double gpa;
    private String group;

    public Student(String username, String hash , String firstName, String lastName, String field, GENDER gender, int currentSemester,
                   Date bornDate, String location, STATUS status, String school, int creditsDone, double gpa, Number phone, String group) {
        super(username,USERTYPE.STUDENT,hash,firstName,lastName,field,gender,bornDate,location,status,phone);
        this.currentSemester = currentSemester;
        this.school = school;
        this.creditsDone = creditsDone;
        this.gpa = gpa;
        this.group = group;
    }

    public int getCreditsDone(){
        return creditsDone;
    }

    public String getSchool(){
        return school;
    }
    public int getCurrentSemester(){
        return currentSemester;
    }
    public double getGPA(){
        return gpa;
    }
    public String getGroup(){
        return group;
    }
}
