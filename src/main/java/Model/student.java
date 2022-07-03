package Model;

import java.util.*;

public class student extends user{

    private String firstname;
    private String lastname;
    private String field;
    private GENDER gender;
    private int currentSemester;
    private Date bornDate;
    private String location;
    private STATUS status;
    private String school;
    private int creditsDone;
    private double gpa;
    private Number phone;
    private String group;


    public student(String username, USERTYPE usertype, String firstName, String lastName, String field,GENDER gender, int currentSemester,
                    Date bornDate, String location, STATUS status, String school, int creditsDone,  double gpa, Number phone, String group) {
        super(username, usertype);
        this.firstname = firstName;
        this.lastname = lastName;
        this.field = field;
        this.gender = gender;
        this.currentSemester = currentSemester;
        this.bornDate = bornDate;
        this.location = location;
        this.status = status;
        this.school = school;
        this.creditsDone = creditsDone;
        this.gpa = gpa;
        this.phone = phone;
        this.group = group;
    }

    public String getName(){
        return getUserName();
    }

    public void setPhone(Number newPhone){
        this.phone = newPhone;
    }

    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getField(){
        return field;
    }
    public GENDER getGender(){
        return gender;
    }
    public int getCreditsDone(){
        return creditsDone;
    }
    public Date getBornDate(){
        return bornDate;
    }
    public String getLocation(){
        return location;
    }
    public STATUS getStatus(){
        return status;
    }
    public String getSchool(){
        return school;
    }
    public int getCurrentSemester(){
        return currentSemester;
    }
    public double getGpa(){
        return gpa;
    }
    public Number getPhone(){
        return phone;
    }
    public String getGroup(){
        return group;
    }
}
