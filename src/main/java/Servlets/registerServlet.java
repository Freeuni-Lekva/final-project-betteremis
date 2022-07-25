package Servlets;

import DAO.SqlLecturerDAO;
import DAO.Mapping;
import DAO.SqlStudentDAO;
import DAO.SqlUserDAO;
import Helper.Utils;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

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
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        USERTYPE usertype = req.getParameter("type").equals("student")? USERTYPE.STUDENT : USERTYPE.LECTURER;
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        char[] chars = new char[pass.length()];
        for(int i = 0; i < pass.length(); i++ ){
            chars[i] = pass.charAt(i);
        }
        String passHash = hasher.hashToString(10,chars);
        User newUser = new User(email, passHash, usertype);
        SqlUserDAO uDAO = (SqlUserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        int userID = uDAO.addUser(newUser);
        if(userID == -1){
            resp.sendRedirect("pageNotFound.jsp");
            return;
        }
        String firstName = req.getParameter("firstname"); String lastname = req.getParameter("lastname");
        GENDER gender = req.getParameter("Gender").equals("Male")?GENDER.MALE:GENDER.FEMALE;
        String profession = req.getParameter("profession");

        String birth = req.getParameter("dateofbirth");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(birth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String address = req.getParameter("address");
        String phone = req.getParameter("number");

        if (usertype.equals(USERTYPE.STUDENT)) {
            String group = req.getParameter("groupname");
            String school = req.getParameter("school");
            SqlStudentDAO sDAO = (SqlStudentDAO) req.getServletContext().getAttribute(Mapping.STUDENT_DAO);
            sDAO.addStudent(new Student(email,passHash,usertype, firstName, lastname, profession, 1 , gender, date ,
                    address,STATUS.ACTIVE,school,0,4,new BigInteger(phone),group,userID));
        } else {
            SqlLecturerDAO lDAO = (SqlLecturerDAO) req.getServletContext().getAttribute(Mapping.LECTURER_DAO);
            lDAO.addLecturer(new Lecturer(email,passHash,usertype,userID, firstName, lastname, profession,gender, date ,
                    address, STATUS.ACTIVE, new BigInteger(phone)));
        }
        resp.sendRedirect("welcome.jsp");
    }
}
