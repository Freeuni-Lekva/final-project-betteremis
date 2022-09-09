package Servlets;

import DAO.Interfaces.*;
import DAO.Mapping;
import Model.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CancelRegistrationServlet", value = "/studentPages/CancelRegistrationServlet")
public class CancelRegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User usr = (User)request.getSession().getAttribute(Mapping.USER_OBJECT);
        RegistrationStatusDAO rsDAO = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
        try {
            if(usr.getType() != USERTYPE.STUDENT || !rsDAO.registrationStatus()){
                request.setAttribute("userMessage", "Access denied.");
                String path = "lecturerPages/lecturerProfile.jsp";
                if(usr.getType() == USERTYPE.ADMIN)
                    path = "adminPages/adminProfile.jsp";
                request.setAttribute("path", path);
                request.setAttribute("cssPath", "css/welcome.scss");
                request.getRequestDispatcher("studentPages/MessagePrinter.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            return;
        }

        String subjectToRemove = request.getParameter("subjectToRemove");
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        Subject subject = subjectDAO.getSubjectByName(subjectToRemove);
        Student student = (Student) request.getSession().getAttribute(Mapping.USER_OBJECT);
        StudentClassroomDAO studentClassroomDAO = (StudentClassroomDAO) request.getServletContext().getAttribute(Mapping.STUDENT_CLASSROOM_DAO);
        ClassroomDAO classroomDAO = (ClassroomDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_DAO);
        CurrentSemesterDAO currentSemesterDAO = (CurrentSemesterDAO) request.getServletContext().getAttribute(Mapping.CURRENT_SEMESTER_DAO);

        if(currentSemesterDAO.getCurrentSemester() != shDAO.getSemester(student, subject)){
            request.setAttribute("userMessage", "Access denied.");
            request.setAttribute("path", "studentProfile.jsp");
            request.setAttribute("cssPath", "../css/welcome.scss");
            request.getRequestDispatcher("MessagePrinter.jsp").forward(request, response);
            return;
        }

        if(!shDAO.isCompleted(student, subject)){
            Classroom c = classroomDAO.getClassroomBySubjectNameAndSemester(subjectToRemove, student.getCurrentSemester());
            studentClassroomDAO.removeStudentAndClassroom(student.getEmail(), c.getTableID());
            shDAO.removeStudentAndSubject(student, subject);
        }
        request.getRequestDispatcher("studyingCard.jsp").forward(request, response);
    }
}
