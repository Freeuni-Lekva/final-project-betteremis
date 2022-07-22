package Model;

import DAO.Mapping;

import java.util.Date;
import java.util.Map;

public class User {
    private String email;
    private String passwordHash;
    private USERTYPE type;

    public User(Map<String, Object> data){
        email = (String) data.get(Mapping.EMAIL);
        passwordHash = (String) data.get(Mapping.PASSWORD_HASH);
        type = (boolean) data.get(Mapping.IS_STUDENT) ? USERTYPE.STUDENT : USERTYPE.LECTURER;
    }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        User user = (User) o;
        return user.getEmail().equals(this.email) && user.getPasswordHash().equals(this.passwordHash)
                && user.getType().equals(this.type);
    }
}
