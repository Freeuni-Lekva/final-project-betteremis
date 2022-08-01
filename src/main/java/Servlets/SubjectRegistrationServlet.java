package Servlets;

import DAO.Interfaces.PrerequisitesDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;
import Model.Subject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SubjectRegistrationServlet", value = "/SubjectRegistrationServlet")
public class SubjectRegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Ignored
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrerequisitesDAO pDAO = (PrerequisitesDAO) request.getServletContext().getAttribute(Mapping.PREREQUISITES_DAO);
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Student student = (Student) request.getSession().getAttribute(Mapping.USER_OBJECT);
        String subjectName = request.getParameter("currentSubject");
        Subject subject = subjectDAO.getSubjectByName(subjectName);
        boolean canRegister = pDAO.canThisSubjectChosenByStudent(student.getEmail(), subjectName);
        if(canRegister){
            int result = shDAO.addStudentAndSubject(student, subject);
            System.out.println(result);
            if(result == -1)
                request.getRequestDispatcher("failedToRegisterOnSubject.jsp").forward(request, response);
            else
                request.getRequestDispatcher("successfullyRegisteredOnSubject.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("failedToRegisterOnSubject.jsp").forward(request, response);
        }
    }
}
