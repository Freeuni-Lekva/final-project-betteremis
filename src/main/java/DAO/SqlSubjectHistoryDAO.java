package DAO;

import DAO.Interfaces.StudentDAO;
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
    public Map<Integer, ArrayList<Subject>> getAllSubjects(Student student, int mode) {
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT S.Semester, SU.SubjectName, " +
                    " SU.Credits, SU.LecturerID, S.IsCompleted " +
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
                boolean isCompleted = set.getBoolean(5);
                if(mode == -1 && isCompleted){
                    continue;
                }else if(mode == 1 && !isCompleted){
                    continue;
                }
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
        StudentDAO stDAO = new SqlStudentDAO(pool);
        int ID = stDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "INSERT INTO SUBJECTS_HISTORY (UserID, SubjectID, Semester, QUIZ, HOMEWORK, PROJECT, PRESENTATION, MIDTERM, FINAL, FX, IsCompleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ID);
            SubjectDAO sd = new SqlSubjectDAO(pool);
            pool.releaseConnection(conn);
            ps.setInt(2, sd.getSubjectIDByName(sb.getName()));
            conn = pool.getConnection();
            ps.setInt(3, st.getCurrentSemester());
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);
            ps.setInt(9, 0);
            ps.setInt(10, 0);
            ps.setBoolean(11, false);
            if (ps.executeUpdate() == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                int result = keys.getInt(1);
                pool.releaseConnection(conn);
                return result;
            }

        }catch (SQLException e){
            System.out.println("SQLException happened. Might have been constraint fail.");
            pool.releaseConnection(conn);
            return -1;
        }
        pool.releaseConnection(conn);
        return -1;
    }

    private boolean updateScore(Student st, Subject sb, double grade, String type){
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int SubjectID = sd.getSubjectIDByName(sb.getName());
        StudentDAO studDAO = new SqlStudentDAO(pool);
        int StudentID = studDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE SUBJECTS_HISTORY SET " + type + " = ? WHERE UserID = ? AND SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setDouble(1, grade);
            ps.setInt(2, StudentID);
            ps.setInt(3, SubjectID);
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

    @Override
    public boolean updateStudentQuiz(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "QUIZ");
    }

    @Override
    public boolean updateStudentHomework(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "HOMEWORK");
    }

    @Override
    public boolean updateStudentProject(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "PROJECT");
    }

    @Override
    public boolean updateStudentPresentation(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "PRESENTATION");
    }

    @Override
    public boolean updateStudentMidterm(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "MIDTERM");
    }

    @Override
    public boolean updateStudentFinal(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "FINAL");
    }

    @Override
    public boolean updateStudentFX(Student st, Subject sb, double grade) {
        return updateScore(st, sb, grade, "FX");
    }

    @Override
    public double getSumOfScores(Student st, Subject sb) {
        Map<String, Double> scores = getGrade(st, sb);
        if(scores == null)
            return -1;
        double result = 0;
        for (String task : scores.keySet()){
            double score = scores.get(task);
            result += score == -1 ? 0 : score;
        }
        return result;
    }

    @Override
    public Map<Integer, ArrayList<Subject>> getCompletedSubjects(Student st) {
        return getAllSubjects(st, 1);
    }

    @Override
    public Map<Integer, ArrayList<Subject>> getIncompleteSubjects(Student st) {
        return getAllSubjects(st, -1);
    }

    @Override
    public boolean isCompleted(Student st, Subject sb) {
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int SubjectID = sd.getSubjectIDByName(sb.getName());
        StudentDAO studDAO = new SqlStudentDAO(pool);
        int StudentID = studDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM SUBJECTS_HISTORY WHERE UserID = ? AND SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, StudentID);
            ps.setInt(2, SubjectID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                boolean result = rs.getBoolean(12);
                pool.releaseConnection(conn);
                return result;
            }

        }catch (SQLException e){
            System.out.println("SQLException happened.");
            pool.releaseConnection(conn);
            return false;
        }
        pool.releaseConnection(conn);
        return false;
    }

    @Override
    public Map<String, Double> getGrade(Student st, Subject sb) {
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int SubjectID = sd.getSubjectIDByName(sb.getName());
        StudentDAO studDAO = new SqlStudentDAO(pool);
        int StudentID = studDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "SELECT * FROM SUBJECTS_HISTORY WHERE UserID = ? AND SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, StudentID);
            ps.setInt(2, SubjectID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Map<String, Double> result = new HashMap<>();
                result.put(Mapping.QUIZ, rs.getDouble(5));
                result.put(Mapping.HOMEWORK, rs.getDouble(6));
                result.put(Mapping.PROJECT, rs.getDouble(7));
                result.put(Mapping.PRESENTATION, rs.getDouble(8));
                result.put(Mapping.MIDTERM, rs.getDouble(9));
                result.put(Mapping.FINAL, rs.getDouble(10));
                result.put(Mapping.FX, rs.getDouble(11));

                pool.releaseConnection(conn);
                return result;
            }

        }catch (SQLException e){
            System.out.println("SQLException happened.");
            pool.releaseConnection(conn);
            return null;
        }
        pool.releaseConnection(conn);
        return null;
    }

    @Override
    public boolean updateCompletedColumn(Student st, Subject sb, boolean flag) {
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int SubjectID = sd.getSubjectIDByName(sb.getName());
        StudentDAO studDAO = new SqlStudentDAO(pool);
        int StudentID = studDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "UPDATE SUBJECTS_HISTORY SET IsCompleted = ? WHERE UserID = ? AND SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setBoolean(1, flag);
            ps.setInt(2, StudentID);
            ps.setInt(3, SubjectID);
            if (ps.executeUpdate() == 1){
                pool.releaseConnection(conn);
                return true;
            }

        }catch (SQLException e){
            System.out.println("SQLException happened.");
            //e.printStackTrace();
            pool.releaseConnection(conn);
            return false;
        }
        pool.releaseConnection(conn);
        return false;
    }

    @Override
    public boolean removeStudentAndSubject(Student st, Subject sb) {
        SubjectDAO sd = new SqlSubjectDAO(pool);
        int SubjectID = sd.getSubjectIDByName(sb.getName());
        StudentDAO studDAO = new SqlStudentDAO(pool);
        int StudentID = studDAO.getStudentIDByUserID(st.getUserID());
        Connection conn = pool.getConnection();
        try{
            String statement = "DELETE FROM SUBJECTS_HISTORY WHERE UserID = ? AND SubjectID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, StudentID);
            ps.setInt(2, SubjectID);
            if (ps.executeUpdate() == 1){
                pool.releaseConnection(conn);
                return true;
            }

        }catch (SQLException e){
            System.out.println("SQLException happened.");
            //e.printStackTrace();
            pool.releaseConnection(conn);
            return false;
        }
        pool.releaseConnection(conn);
        return false;
    }
}
