package DAO;

import DAO.Interfaces.LecturerDAO;
import Model.*;

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
     * @param user
     * @return A Lecturer specified in user.
     * If successfully found USER_ID , it finds appropriate lecturer using this ID.
     */

    private int getIdByUser(User user){
        if(user.getType() != USERTYPE.LECTURER) {
            System.out.println("Expected lecturer, got other.");
            //return null;
        }
        Connection conn = pool.getConnection();
        String query = "SELECT ID FROM USERS WHERE Email = '" + user.getEmail() + "';";
        ResultSet set = null;
        ArrayList<Integer> oneID = new ArrayList<>();
        try{
            PreparedStatement stm = conn.prepareStatement(query);
            set = stm.executeQuery();
            while(set.next()){
                oneID.add(set.getInt(0));
            }
        }catch (Exception e){
            System.out.println("Error while getting user from USERS");
            pool.releaseConnection(conn);
            return -1;
        }
        if(oneID.size()!=1){
            System.out.println("Email is not correct");
            pool.releaseConnection(conn);
            return -1;
        }
        pool.releaseConnection(conn);
        return oneID.get(0);
    }

    @Override
    public Lecturer getLecturerByUser(User user) {
        Connection conn = pool.getConnection();
        int id = getIdByUser(user);
        if(id == -1 ){
            pool.releaseConnection(conn);
            return null;
        }
        String qeuery2 = "SELECT * FROM LECTURERS WHERE UserID = " + Integer.toString(id) + ";";
        ResultSet resultSet = null;
        ArrayList<Lecturer> oneLecturer = new ArrayList<>();
        try{
            PreparedStatement stm = conn.prepareStatement(qeuery2);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                oneLecturer.add(new Lecturer(user.getEmail(),user.getPasswordHash(),USERTYPE.LECTURER,
                        resultSet.getInt(2),  resultSet.getString(3),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getString(6).equals(Mapping.IS_MALE) ? GENDER.MALE : GENDER.FEMALE,
                        resultSet.getDate(7), resultSet.getString(8),
                        resultSet.getString(9).equals(Mapping.IS_ACTIVE) ? STATUS.ACTIVE : STATUS.INACTIVE,
                        new BigInteger(resultSet.getBytes(10))));
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
     * @param lecturer
     * @return TODO:
     */

    @Override
    public List<Subject> getAllSubjects(Lecturer lecturer) {
        int id = getIdByLecturer(lecturer);
        if(id == -1){
            return null;
        }
        String query = "SELECT * FROM SUBJECTS WHERE LecturerID != " + Integer.toString(id) + ";";
        Connection conn = pool.getConnection();
        try {
            List<Subject> res = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement(query);
            ResultSet set = stm.executeQuery();
            while(set.next()){
                res.add(new Subject(set.getString(2), set.getInt(3),
                        set.getInt(4),set.getInt(5)));
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.releaseConnection(conn);
        }
    }

    private int getIdByLecturer(Lecturer lecturer) {
        Connection conn = pool.getConnection();
        String query = "SELECT ID FROM LECTURERS WHERE Email = '" + lecturer.getEmail() + "';";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(query);
            ResultSet set = stm.executeQuery();
            int counter = 0;
            int id = 0;
            while(set.next()){
                counter++;
                id = set.getInt(1);
            }
            pool.releaseConnection(conn);
            if(counter == 1){
                return id;
            }else{
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            pool.releaseConnection(conn);
        }
    }
}
