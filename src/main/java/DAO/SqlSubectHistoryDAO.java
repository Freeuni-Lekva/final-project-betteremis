package DAO;

import DAO.Interfaces.SubjectHistoryDAO;
import Model.Student;
import Model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SqlSubectHistoryDAO implements SubjectHistoryDAO {

    private ConnectionPool pool;

    public SqlSubectHistoryDAO(ConnectionPool p) {
        this.pool = p;
    }

    @Override
    public Map<Integer, ArrayList<Subject>> getAllSubjects(Student student) {
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT S.Semester, SU.SubjectName, " +
                    " SU.Credits, SU.SubjectSemester, SU.LecturerID " +
                    " FROM STUDENTS U JOIN SUBJECTS_HISTORY S on U.ID = S.UserID " +
                    " JOIN SUBJECTS SU ON S.SubjectID = SU.ID HAVING U.UserID = ?");
            stm.setInt(1, student.getUserID() );
            ResultSet set = stm.executeQuery();
            Map<Integer, ArrayList<Subject> > result = new HashMap<>();
            while(set.next()){
                int semester = set.getInt(1);
                String name = set.getString(2); int credits = set.getInt(3);
                int semesterS = set.getInt(4); int lecID = set.getInt(5);
                Subject subject = new Subject(name,credits,semesterS,lecID);
                if(result.containsKey(semester)){
                    result.get(semester).add(subject);
                }else{
                    ArrayList<Subject> list = new ArrayList<>();
                    list.add(subject);
                    result.put(semester, list);
                }
            }
            return result;
        } catch (SQLException e) {
            System.out.println("");
            return null;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }



    @Override
    public void addStudentAndSubject(Student st, Subject sb) {

    }
}
