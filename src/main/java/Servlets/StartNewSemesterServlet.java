package Servlets;

import DAO.Interfaces.*;
import DAO.Mapping;
import Model.Classroom;
import Model.Subject;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StartNewSemesterServlet", value = "/StartNewSemesterServlet")
public class StartNewSemesterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User usr = (User)request.getSession().getAttribute(Mapping.USER_OBJECT);
        if(usr.getType() != USERTYPE.ADMIN){
            request.setAttribute("userMessage", "Access denied.");
            String path = "studentsPages/studentProfile.jsp";
            if(usr.getType() == USERTYPE.LECTURER)
                path = "lecturerPages/lecturerProfile.jsp";
            request.setAttribute("path", path);
            request.setAttribute("cssPath", "css/welcome.scss");
            request.getRequestDispatcher("studentPages/MessagePrinter.jsp").forward(request, response);
            return;
        }
        SubjectDAO subjectDAO = (SubjectDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_DAO);
        ClassroomDAO classroomDAO = (ClassroomDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_DAO);
        CurrentSemesterDAO currentSemesterDAO = (CurrentSemesterDAO) request.getServletContext().getAttribute(Mapping.CURRENT_SEMESTER_DAO);
        SubjectHistoryDAO shDAO = (SubjectHistoryDAO) request.getServletContext().getAttribute(Mapping.SUBJECT_HISTORY_DAO);
        int semester = currentSemesterDAO.getCurrentSemester();
        shDAO.makeCompleted(semester);
        if(!currentSemesterDAO.incrementSemester())
            return;
        semester = currentSemesterDAO.getCurrentSemester();
        if(semester == -1)
            return;
        StudentDAO studentDAO = (StudentDAO) request.getServletContext().getAttribute(Mapping.STUDENT_DAO);
        studentDAO.updateStudentCurrentSemester(semester);
        List<Subject> subjectList = subjectDAO.getAllSubjects();
        for(Subject s : subjectList){
            Classroom c = new Classroom(-1, subjectDAO.getSubjectIDByName(s.getName()), semester, s.getLecturerID(), null);
            classroomDAO.addClassroom(c);
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
