package Servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletMark", value = "/ServletMark")
public class ServletMark extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subName = (String) request.getSession().getAttribute("subjectName");
        String studentEmail = request.getParameter("studentEmail");
        int semester = Integer.parseInt(request.getParameter("studentSemester"));
        request.getSession().setAttribute("studentSemester", semester);
        request.getSession().setAttribute("studentEmail", studentEmail);
        request.getSession().setAttribute("subjectName", subName);
        response.sendRedirect("lecturerPages/markStudent.jsp");
    }
}
