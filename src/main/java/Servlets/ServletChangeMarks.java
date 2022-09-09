package Servlets;

import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import DAO.SqlUserDAO;
import Model.Student;
import Model.Subject;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletChangeMarks", value = "/ServletChangeMarks")
public class ServletChangeMarks extends HttpServlet {
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
        String email = request.getParameter("email");
        String resetDemand = request.getParameter("reset");
        String subName = request.getParameter("subname");
        SubjectHistoryDAO subDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Subject subject = subjectDAO.getSubjectByName(subName);
        Student student = studentDAO.getStudentWithEmail(email);
        if(resetDemand == null) {
            double quiz = Double.parseDouble(request.getParameter(Mapping.QUIZ));
            double homework = Double.parseDouble(request.getParameter(Mapping.HOMEWORK));
            double project = Double.parseDouble(request.getParameter(Mapping.PROJECT));
            double presentation = Double.parseDouble(request.getParameter(Mapping.PRESENTATION));
            double midterm = Double.parseDouble(request.getParameter(Mapping.MIDTERM));
            double finalE = Double.parseDouble(request.getParameter(Mapping.FINAL));
            double fx = Double.parseDouble(request.getParameter(Mapping.FX));
            subDAO.updateStudentQuiz(student, subject, quiz);
            subDAO.updateStudentHomework(student, subject, homework);
            subDAO.updateStudentProject(student, subject, project);
            subDAO.updateStudentPresentation(student, subject, presentation);
            subDAO.updateStudentMidterm(student, subject, midterm);
            subDAO.updateStudentFinal(student, subject, finalE);
            subDAO.updateStudentFX(student, subject, fx);
            response.sendRedirect("lecturerPages/subject.jsp");
        }else{
            subDAO.updateStudentQuiz(student, subject, 0);
            subDAO.updateStudentHomework(student, subject, 0);
            subDAO.updateStudentProject(student, subject, 0);
            subDAO.updateStudentPresentation(student, subject, 0);
            subDAO.updateStudentMidterm(student, subject, 0);
            subDAO.updateStudentFinal(student, subject, 0);
            subDAO.updateStudentFX(student, subject, 0);
            response.sendRedirect("lecturerPages/markStudent.jsp");
        }
    }
}
