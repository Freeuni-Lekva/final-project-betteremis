package Servlets;

import DAO.Interfaces.ClassroomDAO;
import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Interfaces.StudentClassroomDAO;
import DAO.Mapping;
import DAO.SqlUserDAO;
import Model.Classroom;
import Model.Student;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletChangeRegistration", value = "/ServletChangeRegistration")
public class ServletChangeRegistration extends HttpServlet {
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
        StudentClassroomDAO studentClassroomDAO = (StudentClassroomDAO) request.getServletContext().getAttribute(Mapping.STUDENT_CLASSROOM_DAO);
        ClassroomDAO classroomDAO = (ClassroomDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_DAO);
        String demandForClose =  request.getParameter("Close");
        RegistrationStatusDAO regDao = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
        if(demandForClose == null){
            regDao.openRegistration();
        }else {
            List<Classroom> classroomList = classroomDAO.getAllClassrooms();
            for(Classroom c : classroomList){
                List<Student> list = studentClassroomDAO.getStudentsInClassroom(c.getTableID());
                if(list.isEmpty()){
                    classroomDAO.removeClassroom(c);
                }
            }
            regDao.closeRegistration();
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
