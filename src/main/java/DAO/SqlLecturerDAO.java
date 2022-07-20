package DAO;

import DAO.Interfaces.LecturerDAO;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class SqlLecturerDAO implements LecturerDAO {

    private ConnectionPool pool;

    /**
     * stores connection pool variable for creating safe connections.
     * @param pool
     */
    public SqlLecturerDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    /**
     *
     * @param lecturer
     * @return true if lecturer has added successfully, otherwise returns false.
     */

    @Override
    public boolean addLecturer(Lecturer lecturer){
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO LECTURERS (UserID, FirstName, LastName, " +
                    "Profession, Gender, DateOfBirth, Address, LecturerStatus, PhoneNumber) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)");
            setFields(stm,lecturer);
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

    private void setFields(PreparedStatement stm, Lecturer lecturer) {
        try {
            stm.setInt(1, lecturer.getUserID());
            stm.setString(2, lecturer.getFirstName());
            stm.setString(3, lecturer.getLastName());
            stm.setString(4, lecturer.getProfession());
            stm.setString(5, lecturer.getGender().toString());
            stm.setDate(6, new java.sql.Date(lecturer.getBirthDate().getTime()));
            stm.setString(7, lecturer.getAddress());
            stm.setString(8, lecturer.getStatus().toString());
            stm.setString(9, lecturer.getPhone().toString());
        }catch (Exception e){
            System.out.println("Error while setting values");
        }
    }

    /**
     * @param lecturer
     * @return true if the given lecturer has been deleted successfully.
     */
    @Override
    public boolean removeLecturer(Lecturer lecturer){
        Connection conn = pool.getConnection();
        try{
            PreparedStatement stm = conn.prepareStatement("DELETE FROM LECTURERS WHERE UserID = ?");
            stm.setInt(1, lecturer.getUserID());
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


    /**
     *
     * @param email
     * @return Lecturer specified in user.
     * If successfully found USER_ID , it finds appropriate lecturer using this ID.
     */

    @Override
    public Lecturer getLecturerWithEmail(String email) {
        Connection conn = pool.getConnection();
        String query = "SELECT U.Email, U.PasswordHash, U.Privilege, S.UserID , S.FirstName," +
                "S.LastName, S.Profession, S.Gender, S.DateOfBirth, S.Address, S.GroupName" +
                "FROM USERS U JOIN LECTURERS S on U.ID = S.UserID HAVING U.Email = ?;";
        ResultSet resultSet = null;
        ArrayList<Lecturer> oneLecturer = new ArrayList<>();
        try{
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1,email);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                oneLecturer.add(new Lecturer(resultSet.getString(1),resultSet.getString(2),
                        USERTYPE.LECTURER,
                        resultSet.getInt(4),  resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8).equals(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE,
                        resultSet.getDate(9), resultSet.getString(10),
                        resultSet.getString(11).equals(STATUS.ACTIVE.toString()) ? STATUS.ACTIVE : STATUS.INACTIVE,
                        new BigInteger(resultSet.getBytes(12))));
            }
        }catch (Exception e){
            System.out.println("Error while getting user from USERS");
            pool.releaseConnection(conn);
            return null;
        }
        if(oneLecturer.size()!=1){
            System.out.println("More than one lecturer encountered");
            pool.releaseConnection(conn);
            return null;
        }
        pool.releaseConnection(conn);
        return oneLecturer.get(0);
    }

    /**
     *
     * @param email
     * @return TODO:
     */

    @Override
    public List<Subject> getAllSubjects(String email) {
        String query = "SELECT S.SubjectName, S.Credits, S.SubjectSemester, S.LecturerID" +
                "FROM SUBJECTS S JOIN LECTURERS L on S.LecturerID = L.ID HAVING L.Email = ?;";
        Connection conn = pool.getConnection();
        try {
            List<Subject> res = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1,email);
            ResultSet set = stm.executeQuery();
            while(set.next()){
                res.add(new Subject(set.getString(1), set.getInt(2),
                        set.getInt(3),set.getInt(4)));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.releaseConnection(conn);
        }
    }
}
