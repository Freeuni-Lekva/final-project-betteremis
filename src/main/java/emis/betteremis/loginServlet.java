package emis.betteremis;


import DAO.*;
import Helper.Utils;
import Model.*;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String)req.getParameter(Mapping.EMAIL), password = (String)req.getParameter(Mapping.PASSWORD);
        SqlUserDAO usrDAO = (SqlUserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        User usr = usrDAO.getUserByEmail(email);
        if(usrDAO.isValidUser(email, password)){
            if(usr.getType() == USERTYPE.STUDENT){
                SqlStudentDAO studDAO = (SqlStudentDAO) req.getServletContext().getAttribute(Mapping.STUDENT_DAO);
                Student stud = studDAO.getStudentWithEmail(email);
                req.getSession().setAttribute(Mapping.USER_OBJECT, stud);
            }
            else{
                SqlLecturerDAO lecDAO = (SqlLecturerDAO) req.getServletContext().getAttribute(Mapping.LECTURER_DAO);
                Lecturer lec = lecDAO.getLecturerByUser(usr);
                req.getSession().setAttribute(Mapping.USER_OBJECT, lec);
            }
            //TODO: Redirect to another servlet which will print data about user accordingly.
        }
        else{
            //TODO: Print that provided information is not correct, ask them to try again.
        }
    }


}


