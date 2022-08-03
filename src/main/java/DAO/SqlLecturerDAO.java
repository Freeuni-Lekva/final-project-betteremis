package DAO;

import DAO.Interfaces.LecturerDAO;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import java.math.BigInteger;
import java.sql.*;
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
     * @return the ID of a new lecturer.
     */

    @Override
    public int addLecturer(Lecturer lecturer){
        Connection conn = pool.getConnection();
        try {
            PreparedStatement stm = conn.prepareStatement("INSERT IGNORE INTO LECTURERS (UserID, FirstName, LastName, " +
                    "Profession, Gender, DateOfBirth, Address, LecturerStatus, PhoneNumber) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            setFields(stm,lecturer);
            int added = stm.executeUpdate();
            if(added != 1) throw new SQLException();
            ResultSet set = stm.getGeneratedKeys();
            int lecturerID = -1;
            if(set.next()){
                lecturerID = set.getInt(1);
            }
            return lecturerID;
        } catch (SQLException e) {
            System.out.println("Something happened while adding lecturer!");
            return -1;
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
     *
     * @param email Email of the lecturer
     * @return the Lecturer having the given email.
     */
    @Override
    public Lecturer getLecturerWithEmail(String email) {
        Connection conn = pool.getConnection();
        String query = "SELECT U.Email, U.PasswordHash, U.Privilege, S.UserID , S.FirstName," +
                "S.LastName, S.Profession, S.Gender, S.DateOfBirth, S.Address, S.LecturerStatus ,S.PhoneNumber " +
                "FROM USERS U JOIN LECTURERS S on U.ID = S.UserID WHERE U.Email = ?";
        try{
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1,email);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                return new Lecturer(resultSet.getString(1),resultSet.getString(2),
                        USERTYPE.LECTURER,
                        resultSet.getInt(4),  resultSet.getString(5),
                        resultSet.getString(6), resultSet.getString(7),
                        resultSet.getString(8).equals(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE,
                        resultSet.getDate(9), resultSet.getString(10),
                        resultSet.getString(11).equals(STATUS.ACTIVE.toString()) ? STATUS.ACTIVE : STATUS.INACTIVE,
                        new BigInteger(resultSet.getBytes(12)));
            }
            return null;
        }catch (Exception e){
            System.out.println("Error while getting user from USERS");
            return null;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    /**
     *
     * @param email Email of the lecturer
     * @return the list of all subjects of the Lecturer having the given email.
     */

    @Override
    public List<Subject> getAllSubjects(String email) {
        String query = "SELECT S.SubjectName, S.Credits, S.LecturerID " +
                "FROM SUBJECTS S JOIN LECTURERS L on S.LecturerID = L.ID JOIN USERS U on U.ID = L.UserID WHERE U.Email = ?";
        Connection conn = pool.getConnection();
        try {
            List<Subject> res = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1,email);
            ResultSet set = stm.executeQuery();
            while(set.next()){
                res.add(new Subject(set.getString(1), set.getInt(2),
                        set.getInt(3)));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public Lecturer getLecturerWithID(int ID) {
        Connection conn = pool.getConnection();
        String query = "SELECT U.Email, U.PasswordHash, U.Privilege, S.ID, S.UserID , S.FirstName," +
                "S.LastName, S.Profession, S.Gender, S.DateOfBirth, S.Address, S.LecturerStatus ,S.PhoneNumber " +
                "FROM USERS U JOIN LECTURERS S on U.ID = S.UserID WHERE S.ID = ?";
        try{
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, ID);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                return new Lecturer(resultSet.getString(1),resultSet.getString(2),
                        USERTYPE.LECTURER,
                        resultSet.getInt(5),  resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8),
                        resultSet.getString(9).equals(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE,
                        resultSet.getDate(10), resultSet.getString(11),
                        resultSet.getString(12).equals(STATUS.ACTIVE.toString()) ? STATUS.ACTIVE : STATUS.INACTIVE,
                        new BigInteger(resultSet.getBytes(13)));
            }
            return null;
        }catch (Exception e){
            System.out.println("Error while getting user from USERS");
            return null;
        }finally {
            pool.releaseConnection(conn);
        }
    }

    @Override
    public boolean terminateStatus(Lecturer lecturer) {
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE LECTURERS SET LecturerStatus = ? WHERE UserID = ?");
            stm.setString(1, STATUS.INACTIVE.toString());
            stm.setInt(2, lecturer.getUserID());
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
    public boolean recoverStatus(Lecturer lecturer) {
        Connection conn = pool.getConnection();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("UPDATE LECTURERS SET LecturerStatus = ? WHERE UserID = ?");
            stm.setString(1, STATUS.ACTIVE.toString());
            stm.setInt(2, lecturer.getUserID());
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
    public int getIDByEmail(String email) {
        Connection conn = pool.getConnection();
        String query = "SELECT S.ID "+
                "FROM USERS U JOIN LECTURERS S on U.ID = S.UserID WHERE U.Email = ?";
        try{
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1,email);
            ResultSet resultSet = stm.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
            return -1;
        }catch (Exception e){
            System.out.println("Error while getting user from USERS");
            return -1;
        }finally {
            pool.releaseConnection(conn);
        }
    }
}
