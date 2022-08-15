package Model;

import java.sql.Timestamp;
import java.util.Objects;

public class Classroom {

    private int tableID;
    private int subjectID ;
    private int semester ;
    private int lecturerID ;
    private Timestamp time ;

    public Classroom(int tableID, int subjectID, int semester, int lecturerID, Timestamp time) {
        this.tableID = tableID;
        this.subjectID = subjectID;
        this.semester = semester;
        this.lecturerID = lecturerID;
        this.time = time;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public int getSemester() {
        return semester;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public Timestamp getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return subjectID == classroom.subjectID && semester == classroom.semester && lecturerID == classroom.lecturerID && Objects.equals(time, classroom.time);
    }

}
