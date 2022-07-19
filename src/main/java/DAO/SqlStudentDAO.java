package DAO;

import DAO.Interfaces.StudentDAO;
import Model.*;

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
            stm = conn.prepareStatement("INSERT INTO STUDENTS (UserID, FirstName, LastName, Profession, CurrentSemester, " +
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




        } catch (SQLException e) {
            System.out.println("There might be error in the script adding the student in the database!");
            return -1;
        }
        catch (IllegalArgumentException e){
            System.out.println("Something went wrong adding student in the database!");
            return -1;
        }
        finally {
            pool.releaseConnection(conn);
        }
        return studentID;
    }
    @Override
    public boolean terminateStatus(Student student){
        int studID = student.getUserID();
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE STUDENTS SET StudentStatus = ? WHERE UserID = ?");
            stm.setString(1, STATUS.INACTIVE.toString());
            stm.setInt(2, studID);
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
    public boolean removeStudent(Student student) {
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM STUDENTS WHERE UserID = ?");
            stm.
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student getStudentByUser(User user) {
        return null;
    }
}
