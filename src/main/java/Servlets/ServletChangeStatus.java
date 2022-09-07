package Servlets;

import DAO.Interfaces.LecturerDAO;
import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import Model.Lecturer;
import Model.Student;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletChangeStatus", value = "/ServletChangeStatus")
public class ServletChangeStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(Mapping.USER_OBJECT);
        if(admin == null || admin.getType() != USERTYPE.ADMIN) {
            redirect(request, response);
            return;
        }
        String ifBanned = request.getParameter("ban");
        String email = request.getParameter("email");
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute(Mapping.USER_DAO);
        User user =  userDAO.getUserByEmail(email);
        if(ifBanned == null){
            //REMOVE BAN
            if(user.getType() == USERTYPE.STUDENT){
                StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
                Student student = studentDAO.getStudentWithEmail(user.getEmail());
                studentDAO.recoverStatus(student);
            }else{
                LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
                Lecturer lecturer  = lecturerDAO.getLecturerWithEmail(user.getEmail());
                lecturerDAO.recoverStatus(lecturer);
            }
        }else {
            //BAN USER
            if(user.getType() == USERTYPE.STUDENT){
                StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
                Student student = studentDAO.getStudentWithEmail(user.getEmail());
                studentDAO.terminateStatus(student);
            }else{
                LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
                Lecturer lecturer  = lecturerDAO.getLecturerWithEmail(user.getEmail());
                lecturerDAO.terminateStatus(lecturer);
            }
        }
        request.getSession().setAttribute(Mapping.EMAIL, email);
        response.sendRedirect("userForAdmin.jsp");
    }
}
