package Servlets;

import DAO.Mapping;
import DAO.SqlMailDAO;
import DAO.SqlUserDAO;
import Model.Message;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletSendMessage", value = "/ServletSendMessage")
public class ServletSendMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Mapping.USER_OBJECT);
        String senderEmail = request.getParameter(Mapping.SENDER);
        if(user == null || !user.getEmail().equals(senderEmail)){
            redirect(request, response);
        }
        String receiverEmail = request.getParameter(Mapping.RECEIVER);
        //request.getSession().setAttribute(Mapping.RECEIVER, receiverEmail);

        response.sendRedirect("privateMessenger.jsp?"+ Mapping.RECEIVER + "=" + receiverEmail);
    }
}
