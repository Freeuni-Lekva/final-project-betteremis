package Servlets;

import DAO.Interfaces.LecturerDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Mapping;
import Model.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletRemoveSubject", value = "/ServletRemoveSubject")
public class ServletRemoveSubject extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        LecturerDAO lecturerDAO = (LecturerDAO) request.getServletContext().getAttribute(Mapping.LECTURER_DAO);
        String subName = request.getParameter("sub_name");
        boolean res = subjectDAO.removeSubject(subName);
        if(!res){
            System.out.println("wrong");
        }
        response.sendRedirect("adminPages/subjectsForAdmin.jsp");
    }
}
