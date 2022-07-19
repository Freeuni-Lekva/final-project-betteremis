package DAO;

import Model.Lecturer;
import Model.Subject;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class LecturerDAO {

    private ConnectionPool pool;

    public LecturerDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public boolean addLecturer(Lecturer lecturer){
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO LECTURERS (UserID, FirstName, LastName, " +
                    "Profession, Gender, DateOfBirth, Address, LecturerStatus, PhoneNumber) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)");
            stm.setInt(1, lecturer.getUserID());
            stm.setString(2, lecturer.getFirstName());
            stm.setString(3, lecturer.getLastName());
            stm.setString(4, lecturer.getProfession());
            stm.setString(5, lecturer.getGender().toString());
            stm.setDate(6, new java.sql.Date(lecturer.getBirthDate().getTime()));
            stm.setString(7, lecturer.getAddress());
            stm.setString(8, lecturer.getStatus().toString());
            stm.setString(9, lecturer.getPhone().toString());
            int added = stm.executeUpdate();
            if(added != 1) throw new SQLException();
            return true;
        } catch (SQLException e) {
            System.out.println("Something happened while adding lecturer!");
            return false;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }
    public boolean removeLecturer(int lecturer_id){
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("DELETE FROM LECTURERS WHERE UserID = ?");
            stm.setInt(1, lecturer_id);
            int removed = stm.executeUpdate();
            if(removed != 1) throw new SQLException();
            return true;
        } catch (SQLException e) {
            System.out.println("Could not find lecturer with given ID or something happened while removing from database!");
            return false;
        }
        finally {
            pool.releaseConnection(conn);
        }
    }

}
