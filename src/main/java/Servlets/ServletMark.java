package Servlets;

import DAO.Interfaces.CurrentSemesterDAO;
import DAO.Interfaces.StudentDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Interfaces.SubjectHistoryDAO;
import DAO.Mapping;
import Model.Student;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletMark", value = "/ServletMark")
public class ServletMark extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User lecturer = (User) session.getAttribute(Mapping.USER_OBJECT);
        if(lecturer == null || lecturer.getType() != USERTYPE.LECTURER){
            redirect(request, response);
        }
        CurrentSemesterDAO currentSemesterDAO = (CurrentSemesterDAO) request.getServletContext().getAttribute(Mapping.CURRENT_SEMESTER_DAO);
        SubjectHistoryDAO subjectHistoryDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);

        String subName = (String) request.getSession().getAttribute("subjectName");
        String studentEmail = request.getParameter("studentEmail");
        Student st = studentDAO.getStudentWithEmail(studentEmail);
        int currentSemester = currentSemesterDAO.getCurrentSemester();

        if(subjectHistoryDAO.getSemester(st, subjectDAO.getSubjectByName(subName)) != currentSemester){
            request.setAttribute("userMessage", "Cannot change marks in current semester.");
            String path = "lecturerPages/lecturerProfile.jsp";
            request.setAttribute("path", path);
            request.setAttribute("cssPath", "css/welcome.scss");
            request.getRequestDispatcher("studentPages/MessagePrinter.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("studentEmail", studentEmail);
        request.getSession().setAttribute("subjectName", subName);
        response.sendRedirect("lecturerPages/markStudent.jsp");
    }
}
