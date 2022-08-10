package Model;

public enum GENDER {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        if(this == MALE) return "Male";
        return "Female";
    }
}
