package Servlets;

import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.EMAIL;
import static DAO.Mapping.USER_OBJECT;
import static Helper.EmailSender.sendEmail;

@WebServlet(name = "ResetPassword", value = "/ResetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("?");
        String email = request.getParameter(EMAIL);
        sendEmail(email);
    }

}
