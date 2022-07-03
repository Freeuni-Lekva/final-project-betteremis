package Model;

import java.util.ArrayList;

public class subject {
    private String name;
    private int numCredits;
    private ArrayList<lecturer> lecturers;

    public subject(String name, int credits, lecturer lecturer) {
        lecturers = new ArrayList<>();
        this.name = name;
        this.numCredits = credits;
        lecturers.add(lecturer);
    }

    public void addLecturer(lecturer lec){
        lecturers.add(lec);
    }

    public String getName(){
        return name;
    }

    public int getNumCredits(){
        return numCredits;
    }
}
