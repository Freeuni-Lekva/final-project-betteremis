package Model;

public enum USERTYPE {
    STUDENT,
    LECTURER,

    ADMIN;
    public String toString(){
        if(this == STUDENT){
            return "Student";
        }else if(this == LECTURER){
            return "Lecturer";
        }else if(this == ADMIN){
            return "Admin";
        }
        return null;
    }
    public static USERTYPE toUserType(String type){
        if(type.equals("Student")) return STUDENT;
        if(type.equals("Lecturer")) return LECTURER;
        return ADMIN;
    }
}
