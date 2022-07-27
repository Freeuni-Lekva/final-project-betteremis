package Servlets;

import DAO.Interfaces.TokenDAO;
import DAO.Interfaces.UserDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.*;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = (String) request.getSession().getAttribute(EMAIL);
        String password = request.getParameter("password");
        UserDAO dao = (UserDAO) request.getServletContext().getAttribute(USER_DAO);
        boolean res = dao.setPassword(userEmail, password);
        if(res){
            response.sendRedirect("success.jsp");
        }else{
            request.setAttribute("incorrect", true);
            request.setAttribute("mess", "Failed to update password. Please try again.");
            request.getRequestDispatcher("/invalidUser.jsp").forward(request,response);
        }
    }

}
