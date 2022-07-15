package Model;

import java.util.Date;

public abstract class User {
    private String firstname;
    private String lastname;
    private String name;
    private USERTYPE type;
    private String field;
    private GENDER gender;
    private String passwordHash;
    private Date bornDate;
    private String location;
    private STATUS status;
    private Number phone;

    public User(String username, USERTYPE usertype, String passwordHash,String firstName, String lastName,String field,
                GENDER gender,Date bornDate, String location,STATUS status, Number phone) {
        this.name = username;
        this.type = usertype;
        this.passwordHash = passwordHash;
        this.firstname = firstName;
        this.lastname = lastName;
        this.field = field;
        this.gender = gender;
        this.bornDate = bornDate;
        this.location = location;
        this.status = status;
        this.phone = phone;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public USERTYPE getType(){
        return type;
    }

    public String getUserName(){
        return name;
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
