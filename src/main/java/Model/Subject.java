package Model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int numCredits;
    private int semester;

    public Subject(String name, int numCredits, int semester) {
        this.name = name;
        this.numCredits = numCredits;
        this.semester = semester;
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
}
