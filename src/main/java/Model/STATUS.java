package Model;

public enum STATUS {
    ACTIVE,
    INACTIVE;
    @Override
    public String toString(){
        if(this == ACTIVE) return "Active";
        else return "Inactive";
    }
}
