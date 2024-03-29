package Servlets;


import DAO.*;
import Model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(Mapping.EMAIL), password = req.getParameter(Mapping.PASSWORD);
        SqlUserDAO usrDAO = (SqlUserDAO) req.getServletContext().getAttribute(Mapping.USER_DAO);
        User usr = usrDAO.getUserByEmail(email);
        if(!usrDAO.isValidUser(email, password)){
            req.setAttribute("incorrect", true);
            req.setAttribute("mess", "Username Or Password Is Not Correct");
            req.getRequestDispatcher("invalidUser.jsp").forward(req, resp);
            return;
        }
        if(usr.getType() == USERTYPE.STUDENT){
            SqlStudentDAO studDAO = (SqlStudentDAO) req.getServletContext().getAttribute(Mapping.STUDENT_DAO);
            Student stud = studDAO.getStudentWithEmail(email);
            if(stud.getStatus() == STATUS.ACTIVE) {
                req.getSession().setAttribute(Mapping.USER_OBJECT, stud);
                resp.sendRedirect("studentPages/studentProfile.jsp");
            }else{
                req.setAttribute("incorrect", true);
                req.setAttribute("mess", "Your account is currently unavailable");
                req.getRequestDispatcher("invalidUser.jsp").forward(req, resp);
            }
        } else if(usr.getType() == USERTYPE.LECTURER){
            SqlLecturerDAO lecDAO = (SqlLecturerDAO) req.getServletContext().getAttribute(Mapping.LECTURER_DAO);
            Lecturer lec = lecDAO.getLecturerWithEmail(email);
            if(lec.getStatus() == STATUS.ACTIVE) {
                req.getSession().setAttribute(Mapping.USER_OBJECT, lec);
                resp.sendRedirect("lecturerPages/lecturerProfile.jsp");
            }else{
                req.setAttribute("incorrect", true);
                req.setAttribute("mess", "Your account is currently unavailable");
                req.getRequestDispatcher("invalidUser.jsp").forward(req, resp);
            }
        }else {
            req.getSession().setAttribute(Mapping.USER_OBJECT, usr);
            resp.sendRedirect("adminPages/adminProfile.jsp");
        }
    }
}


