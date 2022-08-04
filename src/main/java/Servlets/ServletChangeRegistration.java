package Servlets;

import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Mapping;
import DAO.SqlUserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletChangeRegistration", value = "/ServletChangeRegistration")
public class ServletChangeRegistration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String demandForClose =  request.getParameter("Close");
        RegistrationStatusDAO regDao = (RegistrationStatusDAO) request.getServletContext().getAttribute(Mapping.REGISTRATION_STATUS_DAO);
        if(demandForClose == null){
            regDao.openRegistration();
        }else {
            regDao.closeRegistration();
        }
        response.sendRedirect("adminPages/adminProfile.jsp");
    }
}
