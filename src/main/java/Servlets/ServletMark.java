package Servlets;

import DAO.Mapping;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
        String subName = (String) request.getSession().getAttribute("subjectName");
        String studentEmail = request.getParameter("studentEmail");
        int semester = Integer.parseInt(request.getParameter("studentSemester"));
        request.getSession().setAttribute("studentSemester", semester);
        request.getSession().setAttribute("studentEmail", studentEmail);
        request.getSession().setAttribute("subjectName", subName);
        response.sendRedirect("lecturerPages/markStudent.jsp");
    }
}
