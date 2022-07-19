package DAO;

import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO{

    private ConnectionPool pool;
    public StudentDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public void addStudent(Student user){
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("INSERT INTO STUDENTS (UserID, FirstName, LastName, Profession, CurrentSemester, " +
                    "Gender, DateOfBirth, Address, StudentStatus, School, Credits, GPA, PhoneNumber, GroupName) VALUES" +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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

            if(added != 1) throw new IllegalArgumentException();




        } catch (SQLException e) {
            System.out.println("There might be error in the script adding the student in the database!");
        }
        catch (IllegalArgumentException e){
            System.out.println("Something went wrong adding student in the database!");
        }
        finally {
            pool.releaseConnection(conn);
        }
    }
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
}
