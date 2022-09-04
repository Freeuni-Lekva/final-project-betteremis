package Servlets;

import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Interfaces.SemesterDAO;
import DAO.Mapping;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.SEMESTER_DAO;

@WebServlet(name = "ChangeSemesterServlet", value = "/ChangeSemesterServlet")
public class ChangeSemesterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isDemandingStart = request.getParameter("Start");
        SemesterDAO semesterDAO = (SemesterDAO) request.getServletContext().getAttribute(SEMESTER_DAO);
        if(isDemandingStart != null){
            semesterDAO.startSemester();
        }else{
            semesterDAO.endSemester();
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
