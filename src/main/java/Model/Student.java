package Model;

import java.math.BigInteger;
import java.util.*;

public class Student {
    private String firstName, lastName, profession, address, school, group;
    private int currentSemester, creditsDone;
    private double gpa;
    private BigInteger phone;
    private GENDER gender;
    private STATUS status;
    private Date birthDate;

    public Student(String firstName, String lastName, String profession, int currentSemester, GENDER gender,
                   Date birthDate, String address, STATUS status, String school,
                   int creditsDone, double gpa, BigInteger phone, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = profession;
        this.address = address;
        this.school = school;
        this.group = group;
        this.currentSemester = currentSemester;
        this.creditsDone = creditsDone;
        this.gpa = gpa;
        this.phone = phone;
        this.gender = gender;
        this.status = status;
        this.birthDate = birthDate;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfession() {
        return profession;
    }

    public String getAddress() {
        return address;
    }

    public String getSchool() {
        return school;
    }

    public String getGroup() {
        return group;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public int getCreditsDone() {
        return creditsDone;
    }

    public double getGpa() {
        return gpa;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public GENDER getGender() {
        return gender;
    }

    public STATUS getStatus() {
        return status;
    }

    public Date getBirthDate() {
        return birthDate;
    }



}
