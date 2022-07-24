package DAO;

import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import Model.Student;
import Model.Subject;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.apache.ibatis.jdbc.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SqlSubjectHistoryDAO implements SubjectHistoryDAO {

    private ConnectionPool pool;

    public SqlSubjectHistoryDAO(ConnectionPool p) {
        this.pool = p;
    }

    @Override
    public Map<Integer, ArrayList<Subject>> getAllSubjects(Student student) {
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT S.Semester, SU.SubjectName, " +
                    " SU.Credits, SU.LecturerID " +
                    " FROM STUDENTS U JOIN SUBJECTS_HISTORY S on U.ID = S.UserID " +
                    " JOIN SUBJECTS SU ON S.SubjectID = SU.ID WHERE U.UserID = ?");
            stm.setInt(1, student.getUserID() );
            ResultSet set = stm.executeQuery();
            Map<Integer, ArrayList<Subject> > result = new HashMap<>();
            while(set.next()){
                int semester = set.getInt(1);
                String name = set.getString(2); int credits = set.getInt(3);
                int lecID = set.getInt(4);
                Subject subject = new Subject(name,credits,lecID);
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
            return null;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public int addStudentAndSubject(Student st, Subject sb) {
        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO SUBJECTS_HISTORY (UserID, SubjectID, Semester, Grade, IsCompleted) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, st.getUserID());
            SubjectDAO sd = new SqlSubjectDAO(pool);
            pool.releaseConnection(conn);
            ps.setInt(2, sd.getSubjectIDByName(sb.getName()));
            conn = pool.getConnection();
            ps.setInt(3, st.getCurrentSemester());
            ps.setInt(4, 0);
            ps.setBoolean(5, false);
            if (ps.executeUpdate() == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                pool.releaseConnection(conn);
                return keys.getInt(1);
            }

        }catch (SQLException e){
            System.out.println("SQLException happened. Might have been constraint fail.");
            pool.releaseConnection(conn);
            return -1;
        }
        pool.releaseConnection(conn);
        return -1;
    }

    @Override
    public boolean updateStudentGrade(Student st, Subject sb, int grade) {
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int ID = sd.getSubjectIDByName(sb.getName());
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE SUBJECTS_HISTORY SET Grade = ? WHERE SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, grade);
            ps.setInt(2, ID);
            if (ps.executeUpdate() == 1){
                pool.releaseConnection(conn);
                return true;
            }
        }catch(SQLException e){
            pool.releaseConnection(conn);
            return false;
        }
        pool.releaseConnection(conn);
        return false;
    }
}
