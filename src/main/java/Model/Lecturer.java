package Model;

import java.util.Date;

public class Lecturer extends User {

    private String field;
    private GENDER gender;
    private Date bornDate;
    private String location;
    private STATUS status;
    private Number phone;
    public Lecturer(String username, String hash, String firstName, String lastName, String field,
                    GENDER gender, Date bornDate, String location, STATUS status, Number phone) {
        super(username, USERTYPE.LECTURER,hash,firstName,lastName,field,gender,bornDate,location,status,phone);
    }
}
