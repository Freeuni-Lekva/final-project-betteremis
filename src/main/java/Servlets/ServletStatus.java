package Servlets;

import DAO.Interfaces.LecturerDAO;
import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.USER_OBJECT;
import static Helper.ErrorPageRedirector.redirect;
import static Model.USERTYPE.ADMIN;

@WebServlet(name = "ServletStatus", value = "/ServletStatus")
public class ServletStatus extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute(USER_OBJECT);
        if(admin == null || admin.getType() != ADMIN){
            redirect(request, response);
        }
        String email = request.getParameter("email");
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute(Mapping.USER_DAO);
        User user = userDAO.getUserByEmail(email);
        if(user.getType() == USERTYPE.STUDENT){
            StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
            user = studentDAO.getStudentWithEmail(email);
        }else{
            LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
            user = lecturerDAO.getLecturerWithEmail(email);
        }
        request.getSession().setAttribute(Mapping.EMAIL, email);
        request.getRequestDispatcher("userForAdmin.jsp").forward(request,response);
    }
}
