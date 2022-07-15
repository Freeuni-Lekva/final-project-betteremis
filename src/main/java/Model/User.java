package Model;

import java.util.Date;

public class User {
    private String email;
    private String passwordHash;
    private USERTYPE type;

    public User(String email, String passwordHash, USERTYPE usertype) {
        this.email = email;
        this.type = usertype;
        this.passwordHash = passwordHash;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public USERTYPE getType(){
        return type;
    }

    public String getEmail(){
        return email;
    }

}
