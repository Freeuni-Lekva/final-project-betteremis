package Model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int numCredits;

    public Subject(String name, int credits) {
        this.name = name;
        this.numCredits = credits;
    }

    public String getName(){
        return name;
    }

    public int getNumCredits(){
        return numCredits;
    }
}
