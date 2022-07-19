package Model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int numCredits;
    private int semester;
    private int lecturerID;

    public Subject(String name, int numCredits, int semester, int lecturerID) {
        this.name = name;
        this.numCredits = numCredits;
        this.semester = semester;
        this.lecturerID = lecturerID;
    }

    public String getName() {
        return name;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public int getSemester() {
        return semester;
    }

    public int getLecturerID() {
        return lecturerID;
    }
}

