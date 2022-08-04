package DAO;

import DAO.Interfaces.StudentDAO;
import Model.*;

import java.math.BigInteger;
import java.sql.*;

public class SqlStudentDAO implements StudentDAO {

    private ConnectionPool pool;
    public SqlStudentDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public int addStudent(Student user){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        int studentID = 0;
        try {
            stm = conn.prepareStatement("INSERT IGNORE INTO STUDENTS (UserID, FirstName, LastName, Profession, CurrentSemester, " +
                    "Gender, DateOfBirth, Address, StudentStatus, School, Credits, GPA, PhoneNumber, GroupName) VALUES" +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, user.getUserID());
            stm.setString(2, user.getFirstName());
            stm.setString(3, user.getLastName());
            stm.setString(4, user.getProfession());
            stm.setInt(5, user.getCurrentSemester());
            stm.setString(6, user.getGender().toString());
            stm.setDate(7, new java.sql.Date(user.getBirthDate().getTime()));
            stm.setString(8, user.getAddress());
            stm.setString(9, user.getStatus().toString());
            stm.setString(10, user.getSchool());
            stm.setInt(11, user.getCreditsDone());
            stm.setDouble(12, user.getGpa());
            stm.setString(13, user.getPhone().toString());
            stm.setString(14, user.getGroup());
            int added = stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();

            if(added != 1) throw new IllegalArgumentException();

            if(rs.next()) studentID = rs.getInt(1);
            return studentID;



        } catch (SQLException e) {
            System.out.println("There might be error in the script adding the student in the database!");
            return -1;
        }
        catch (IllegalArgumentException e){
            System.out.println("Given student already exists in the database.");
            return -1;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }
    @Override
    public boolean terminateStatus(Student student){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE STUDENTS SET StudentStatus = ? WHERE UserID = ?");
            stm.setString(1, STATUS.INACTIVE.toString());
            stm.setInt(2, student.getUserID());
            int added = stm.executeUpdate();
            if(added != 1) throw new SQLException();
            return true;
        } catch (SQLException e) {
            System.out.println("Student with given ID either cannot be found or something happened executing query.");
            return false;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }


    @Override
    public boolean recoverStatus(Student student){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE STUDENTS SET StudentStatus = ? WHERE UserID = ?");
            stm.setString(1, STATUS.ACTIVE.toString());
            stm.setInt(2, student.getUserID());
            int added = stm.executeUpdate();
            if(added != 1) throw new SQLException();
            return true;
        } catch (SQLException e) {
            System.out.println("Student with given ID either cannot be found or something happened executing query.");
            return false;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }
    @Override
    public int getStudentIDByUserID(int UserID) {
        Connection conn = pool.getConnection();
        int result = -1;
        try{
            String statement = "SELECT * FROM STUDENTS WHERE UserID = ?;";
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, UserID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        }catch (SQLException e){
            //e.printStackTrace();
            result = -1;
        }
        pool.releaseConnection(conn);
        return result;
    }


    @Override
    public Student getStudentWithEmail(String email) {
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT U.Email, U.PasswordHash, U.Privilege, S.FirstName," +
                    "S.LastName, S.Profession, S.CurrentSemester, S.Gender, S.DateOfBirth, S.Address," +
                    "S.StudentStatus, S.School, S.Credits, S.GPA, S.PhoneNumber, S.GroupName, S.UserID  " +
                    "FROM USERS U JOIN STUDENTS S on U.ID = S.UserID HAVING U.Email = ?");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                String newEmail = rs.getString(1), hash = rs.getString(2);
                USERTYPE priv = rs.getString(3).equals(USERTYPE.STUDENT.toString()) ? USERTYPE.STUDENT : USERTYPE.LECTURER;
                String fName = rs.getString(4), lName = rs.getString(5), prof = rs.getString(6);
                int curSem = rs.getInt(7);
                GENDER gender = rs.getString(8).equals(GENDER.MALE.toString()) ? GENDER.MALE : GENDER.FEMALE;
                java.util.Date date = new java.util.Date(rs.getDate(9).getTime());
                String address = rs.getString(10);
                STATUS status = rs.getString(11).equals(STATUS.ACTIVE.toString()) ? STATUS.ACTIVE : STATUS.INACTIVE;
                String school = rs.getString(12);
                int credits = rs.getInt(13);
                double gpa = rs.getDouble(14);
                BigInteger phone = new BigInteger(rs.getString(15));
                String group = rs.getString(16);
                int userID = rs.getInt(17);

                Student newStud = new Student(newEmail, hash, priv, fName, lName, prof, curSem, gender, date, address,
                        status, school, credits, gpa, phone, group, userID);

                return newStud;
            }
            else return null;
        } catch (SQLException e) {
            System.out.println("Something happened while executing query for searching student by user!");
            return null;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }
}
