package Model;

import DAO.Mapping;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Lecturer extends User{

    private int userID;
    private String firstName;
    private String lastName;
    private String profession;
    private GENDER gender;
    private Date birthDate;
    private String address;
    private STATUS status;
    private BigInteger phone;

    public Lecturer(Map<String, Object> data, int userID){
        super(data);
        this.firstName = (String)data.get(Mapping.FIRST_NAME);
        this.lastName = (String) data.get(Mapping.LAST_NAME);
        this.profession = (String) data.get(Mapping.PROFESSION);
        this.address = (String) data.get(Mapping.ADDRESS);
        this.phone = new BigInteger((String) data.get(Mapping.PHONE_NUMBER));
        this.gender = (boolean) data.get(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE;
        this.status = STATUS.ACTIVE;
        this.userID = userID;
        try {
            this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) data.get(Mapping.DATE_OF_BIRTH));
        } catch (ParseException e) {
            System.out.println("Could not parse data");
            throw new RuntimeException(e);
        }
    }
    public Lecturer(String email, String passwordHash, USERTYPE usertype,
                    int userID, String firstName, String lastName, String profession,
                    GENDER gender, Date birthDate, String address, STATUS status, BigInteger phone) {
        super(email, passwordHash, usertype);
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profession = profession;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.status = status;
        this.phone = phone;
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

    public GENDER getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public STATUS getStatus() {
        return status;
    }

    public Number getPhone() {
        return phone;
    }


}
