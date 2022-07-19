package Model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int numCredits;
    private int semester;
    private int lecturer_id;

    public Subject(String name, int numCredits, int semester, int lecturer_id) {
        this.name = name;
        this.numCredits = numCredits;
        this.semester = semester;
        this.lecturer_id = lecturer_id;
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
