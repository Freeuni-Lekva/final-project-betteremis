package Model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int numCredits;
    private int lecturerID;

    public Subject(String name, int numCredits, int lecturerID) {
        this.name = name;
        this.numCredits = numCredits;
        this.lecturerID = lecturerID;
    }

    public String getName() {
        return name;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public int getLecturerID() {
        return lecturerID;
    }
}

