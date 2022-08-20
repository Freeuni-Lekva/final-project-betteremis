package Servlets;

import DAO.Interfaces.PrerequisitesDAO;
import DAO.Interfaces.SubjectDAO;
import DAO.Mapping;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ServletChangePrerequisite", value = "/ServletChangePrerequisite")
public class ServletChangePrerequisite extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subject = request.getParameter(Mapping.SUB_NAME);
        String prerequisites = request.getParameter(Mapping.PREREQUISITE);
   //     System.out.println(subject  + " " + prerequisites);
        PrerequisitesDAO prerequisitesDAO=(PrerequisitesDAO) request.getServletContext().getAttribute(Mapping.PREREQUISITES_DAO);
        boolean update=prerequisitesDAO.updatePrerequisite(subject,prerequisites);
        if(update==false){
            request.getSession().setAttribute(Mapping.WRONG,"Invalid expression!");
            response.sendRedirect("adminPages/subjectsForAdmin.jsp");
        }else {
            response.sendRedirect("adminPages/adminProfile.jsp");
        }
    }
}
