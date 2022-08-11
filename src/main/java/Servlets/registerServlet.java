package Servlets;

import DAO.SqlLecturerDAO;
import DAO.Mapping;
import DAO.SqlStudentDAO;
import DAO.SqlUserDAO;
import Helper.Utils;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.apache.ibatis.jdbc.SQL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        map contains all the necessary information needed for registration

        String email = req.getParameter(Mapping.EMAIL);
        String pass = req.getParameter(Mapping.PASSWORD);
        USERTYPE usertype = req.getParameter(Mapping.IS_STUDENT).equals("student")? USERTYPE.STUDENT : USERTYPE.LECTURER;
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        char[] chars = new char[pass.length()];
        for(int i = 0; i < pass.length(); i++ ){
            chars[i] = pass.charAt(i);
        }
        String passHash = hasher.hashToString(10,chars);
        User newUser = new User(email, passHash, usertype);
        SqlUserDAO uDAO = (SqlUserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        String firstName = req.getParameter(Mapping.FIRST_NAME); String lastname = req.getParameter(Mapping.LAST_NAME);
        GENDER gender = req.getParameter("Gender").equals("M")?GENDER.MALE:GENDER.FEMALE;
        String profession = req.getParameter(Mapping.PROFESSION);

        String birth = req.getParameter(Mapping.DATE_OF_BIRTH);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String address = req.getParameter(Mapping.ADDRESS);
        String phone = req.getParameter(Mapping.PHONE_NUMBER);

        int userID = uDAO.addUser(newUser);
        if(userID == -1){
            req.setAttribute("mess","Email already exists, Please try again.");
            req.getRequestDispatcher("invalidUser.jsp").forward(req, resp);
            return;
        }
        if (usertype.equals(USERTYPE.STUDENT)) {
            String group = req.getParameter(Mapping.GROUP_NAME);
            String school = req.getParameter(Mapping.SCHOOL);
            SqlStudentDAO sDAO = (SqlStudentDAO) req.getServletContext().getAttribute(Mapping.STUDENT_DAO);
            sDAO.addStudent(new Student(email,passHash,usertype, firstName, lastname, profession, 1 , gender, new java.sql.Date(date.getTime()) ,
                    address,STATUS.ACTIVE,school,0,4,new BigInteger(phone),group,userID));
        } else {
            SqlLecturerDAO lDAO = (SqlLecturerDAO) req.getServletContext().getAttribute(Mapping.LECTURER_DAO);
            lDAO.addLecturer(new Lecturer(email,passHash,usertype,userID, firstName, lastname, profession,gender, new java.sql.Date(date.getTime()) ,
                    address, STATUS.ACTIVE, new BigInteger(phone)));
        }
        req.setAttribute("userMessage", firstName + " " + lastname);
        req.getRequestDispatcher("welcome.jsp").forward(req, resp);
    }
}