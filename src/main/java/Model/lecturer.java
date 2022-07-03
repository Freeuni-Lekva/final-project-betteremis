package Model;

import java.util.Date;

public class lecturer extends user{

    private String firstname;
    private String lastname;
    private String field;
    private GENDER gender;
    private Date bornDate;
    private String location;
    private STATUS status;
    private Number phone;


    public lecturer(String username, USERTYPE usertype,String firstName, String lastName, String field,
                   GENDER gender, Date bornDate, String location, STATUS status, Number phone) {
        super(username, usertype);
        this.firstname = firstName;
        this.lastname = lastName;
        this.field = field;
        this.gender = gender;
        this.bornDate = bornDate;
        this.location = location;
        this.status = status;
        this.phone = phone;
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
    public Date getBornDate(){
        return bornDate;
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
