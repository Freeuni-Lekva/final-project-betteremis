package Servlets;

import DAO.Interfaces.TokenDAO;
import Model.User;

import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.*;
import static Helper.EmailSender.sendEmail;

@WebServlet(name = "ResetPassword", value = "/ResetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL);
        try {
            String token = sendEmail(email);
            request.getSession().setAttribute(EMAIL, email);
            TokenDAO dao = (TokenDAO) request.getServletContext().getAttribute(TOKEN_DAO);
            if (token != null) {
                dao.addToken(token);
                response.sendRedirect("../BetterEmis_war_exploded/successfullyChanged.jsp");
            } else {
                request.setAttribute("incorrect", true);
                request.setAttribute("mess", "Failed to send link. Please try again.");
                request.getRequestDispatcher("/invalidUser.jsp").forward(request, response);
            }
        }catch(MessagingException ex){
            ex.printStackTrace();
            request.setAttribute("incorrect", true);
            request.setAttribute("mess", "Failed to send link. Please try again.");
            request.getRequestDispatcher("/invalidUser.jsp").forward(request, response);
        }
    }

}
