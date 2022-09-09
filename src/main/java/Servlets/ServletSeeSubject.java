package Servlets;

import DAO.Mapping;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;
import static Model.USERTYPE.LECTURER;

@WebServlet(name = "ServletSeeSubject", value = "/ServletSeeSubject")
public class ServletSeeSubject extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User lecturer = (User) request.getSession().getAttribute(Mapping.USER_OBJECT);
        if(lecturer == null || lecturer.getType() != LECTURER){
            redirect(request, response);
        }
        String subName = request.getParameter("subjectName");
        request.getSession().setAttribute("subjectName", subName);
        response.sendRedirect("lecturerPages/subject.jsp");
    }
}
