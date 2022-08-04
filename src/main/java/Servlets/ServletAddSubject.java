package Servlets;

import DAO.Interfaces.LecturerDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import Model.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletAddSubject", value = "/ServletAddSubject")
public class ServletAddSubject extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
        String lecturermail = request.getParameter("lec_email");
        int credits = Integer.parseInt(request.getParameter("num_credits"));
        String subName = request.getParameter("sub_name");
        int lecturerID = lecturerDAO.getIDByEmail(lecturermail);
        int res = subjectDAO.addSubject(new Subject(subName, credits, lecturerID));
        if(res == -1 ){

            //TODO: handle unsuccessful trial

            System.out.println("wrong");
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
