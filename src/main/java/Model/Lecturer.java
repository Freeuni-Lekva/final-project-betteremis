package Model;

import java.util.Date;

public class Lecturer extends User {

    //TODO: add some variables if needed.

    public Lecturer(String username, String hash, String firstName, String lastName, String field,
                    GENDER gender, Date bornDate, String location, STATUS status, Number phone) {
        super(username, USERTYPE.LECTURER,hash,firstName,lastName,field,gender,bornDate,location,status,phone);
    }
}
