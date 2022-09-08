package Servlets;

import DAO.Interfaces.*;
import DAO.Mapping;

import Model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "SubjectRegistrationServlet", value = "/SubjectRegistrationServlet")
public class SubjectRegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Mapping.USER_OBJECT);
        if(user == null || user.getType() != USERTYPE.STUDENT){
            redirect(request, response);
        }
        RegistrationStatusDAO rsDAO = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
        try {
            if(!rsDAO.registrationStatus() || ((User) request.getSession().getAttribute(Mapping.USER_OBJECT)).getType() != USERTYPE.STUDENT){
                request.setAttribute("userMessage", "Access denied.");
                request.setAttribute("path", "studentPages/studentProfile.jsp");
                request.setAttribute("cssPath", "css/welcome.scss");
                request.getRequestDispatcher("studentPages/MessagePrinter.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }
        PrerequisitesDAO pDAO = (PrerequisitesDAO) request.getServletContext().getAttribute(Mapping.PREREQUISITES_DAO);
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Student student = (Student) user;
        StudentClassroomDAO studentClassroomDAO = (StudentClassroomDAO) request.getServletContext().getAttribute(Mapping.STUDENT_CLASSROOM_DAO);
        ClassroomDAO classroomDAO = (ClassroomDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_DAO);
        String subjectName = request.getParameter("currentSubject");
        Subject subject = subjectDAO.getSubjectByName(subjectName);
        boolean canRegister = pDAO.canThisSubjectChosenByStudent(student.getEmail(), subjectName);
        if(canRegister){
            int result = shDAO.addStudentAndSubject(student, subject);
            if(result == -1)
                request.getRequestDispatcher("failedToRegisterOnSubject.jsp").forward(request, response);
            else {
                Classroom c = classroomDAO.getClassroomBySubjectNameAndSemester(subjectName, student.getCurrentSemester());
                studentClassroomDAO.addStudentAndClassroom(student.getEmail(), c.getTableID());
                request.getRequestDispatcher("successfullyRegisteredOnSubject.jsp").forward(request, response);
            }
        }else{
            request.getRequestDispatcher("failedToRegisterOnSubject.jsp").forward(request, response);
        }
    }
}
