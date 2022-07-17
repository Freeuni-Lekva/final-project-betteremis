package Model;

import java.util.Date;

public class Lecturer  {

    private int userID;
    private String firstname;
    private String lastname;
    private String field;
    private GENDER gender;
    private Date birthDate;
    private String location;
    private STATUS status;
    private Number phone;

    public Lecturer(int userID, String firstName, String lastName,String field,
                GENDER gender,Date birthDate, String location,STATUS status, Number phone) {
        this.userID = userID;
        this.firstname = firstName;
        this.lastname = lastName;
        this.field = field;
        this.gender = gender;
        this.birthDate = birthDate;
        this.location = location;
        this.status = status;
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }

    public Date getBirthDate() {
        return birthDate;
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

    public String getLocation(){
        return location;
    }

    public STATUS getStatus(){
        return status;
    }
    public Number getPhone(){
        return phone;
    }
}
