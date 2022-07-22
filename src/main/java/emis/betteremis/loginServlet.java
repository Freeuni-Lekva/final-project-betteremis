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
        String email = req.getParameter(Mapping.EMAIL), password = req.getParameter(Mapping.PASSWORD);
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
                Lecturer lec = lecDAO.getLecturerWithEmail(email);
                req.getSession().setAttribute(Mapping.USER_OBJECT, lec);
            }
            resp.sendRedirect("profile.jsp");
        }
        else{
            req.setAttribute("incorrect", true);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }


}


