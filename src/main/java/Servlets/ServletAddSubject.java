package Servlets;

import DAO.Interfaces.LecturerDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import Model.Subject;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;
import static Servlets.ErrorMessages.ERROR_MESSAGE;

@WebServlet(name = "ServletAddSubject", value = "/ServletAddSubject")
public class ServletAddSubject extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute(Mapping.USER_OBJECT);
        if(admin == null || admin.getType() != USERTYPE.ADMIN) {
            redirect(request, response);
            return;
        }
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
        String lecturermail = request.getParameter("lec_email");
        int credits = Integer.parseInt(request.getParameter("num_credits"));
        String subName = request.getParameter("sub_name");
        int lecturerID = lecturerDAO.getIDByEmail(lecturermail);
        int res = subjectDAO.addSubject(new Subject(subName, credits, lecturerID));
        if(res == -1 ){
            request.getSession().setAttribute(ERROR_MESSAGE, "Subject could not be added.");
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
