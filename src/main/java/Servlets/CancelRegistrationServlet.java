package Servlets;

import DAO.Interfaces.PrerequisitesDAO;
import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;
import Model.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CancelRegistrationServlet", value = "/studentPages/CancelRegistrationServlet")
public class CancelRegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ignored
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RegistrationStatusDAO rsDAO = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
        try {
            if(!rsDAO.registrationStatus()){
                request.setAttribute("userMessage", "You can only remove subjects when registration is open.");
                request.setAttribute("path", "studentProfile.jsp");
                request.setAttribute("cssPath", "../css/welcome.scss");
                request.getRequestDispatcher("MessagePrinter.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.getRequestDispatcher("studyingCard.jsp").forward(request, response);
            return;
        }

        String subjectToRemove = request.getParameter("subjectToRemove");
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Subject subject = subjectDAO.getSubjectByName(subjectToRemove);
        Student student = (Student) request.getSession().getAttribute(Mapping.USER_OBJECT);
        if(!shDAO.isCompleted(student, subject)){
            shDAO.removeStudentAndSubject(student, subject);
        }
        request.getRequestDispatcher("studyingCard.jsp").forward(request, response);
    }
}
