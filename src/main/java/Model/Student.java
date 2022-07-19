package Model;

import DAO.Mapping;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student extends User{

    private int userID;
    private String firstName, lastName, profession, address, school, group;
    private int currentSemester, creditsDone;
    private double gpa;
    private BigInteger phone;
    private GENDER gender;
    private STATUS status;
    private Date birthDate;
    public Student(Map<String, Object> data, int userID){
        super(data);
        this.firstName = (String)data.get(Mapping.FIRST_NAME);
        this.lastName = (String) data.get(Mapping.LAST_NAME);
        this.profession = (String) data.get(Mapping.PROFESSION);
        this.address = (String) data.get(Mapping.ADDRESS);
        this.school = (String) data.get(Mapping.SCHOOL);
        this.group = (String) data.get(Mapping.GROUP_NAME);
        this.currentSemester = 1;
        this.creditsDone = 0;
        this.userID = userID;
        this.gpa = 4;
        this.phone = new BigInteger((String) data.get(Mapping.PHONE_NUMBER));
        this.gender = (boolean) data.get(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE;
        this.status = STATUS.ACTIVE;
        try {
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) data.get(Mapping.DATE_OF_BIRTH));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public Student(String email, String passwordHash, USERTYPE usertype,
                   String firstName, String lastName, String profession, int currentSemester, GENDER gender,
                   Date birthDate, String address, STATUS status, String school,
                   int creditsDone, double gpa, BigInteger phone, String group, int userID) {
        super(email, passwordHash, usertype);
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
        this.userID = userID;
    }


    public int getUserID() {
        return userID;
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
