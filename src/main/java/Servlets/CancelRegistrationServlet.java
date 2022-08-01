package Servlets;

import DAO.Interfaces.PrerequisitesDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CancelRegistrationServlet", value = "/studentPages/CancelRegistrationServlet")
public class CancelRegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ignored
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectToRemove = request.getParameter("subjectToRemove");
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Student student = (Student) request.getSession().getAttribute(Mapping.USER_OBJECT);
        shDAO.removeStudentAndSubject(student, subjectDAO.getSubjectByName(subjectToRemove));
        request.getRequestDispatcher("studyingCard.jsp").forward(request, response);
    }
}
