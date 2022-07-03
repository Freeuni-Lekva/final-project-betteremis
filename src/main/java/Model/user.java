package Model;

public class user {

    private String name;
    private USERTYPE type;

    public user(String username, USERTYPE usertype) {
        this.name = username;
        this.type = usertype;
    }

    public USERTYPE getType(){
        return type;
    }

    public String getUserName(){
        return name;
    }

}
