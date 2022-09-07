package Servlets;

import DAO.Interfaces.RegistrationStatusDAO;
import DAO.Mapping;
import DAO.SqlUserDAO;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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
