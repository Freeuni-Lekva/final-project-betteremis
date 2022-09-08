package Servlets;

import DAO.Mapping;
import DAO.SqlMailDAO;
import DAO.SqlUserDAO;
import Model.Message;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletDirectSend", value = "/ServletDirectSend")
public class ServletDirectSend extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Mapping.USER_OBJECT);
        String sender = request.getParameter(Mapping.SENDER);
        if(user == null || user.getEmail() != sender){
            redirect(request, response);
        }
        String receiver = request.getParameter(Mapping.RECEIVER);
        String text = request.getParameter("sendMessage");

        SqlMailDAO mailDAO = (SqlMailDAO) request.getServletContext().getAttribute(Mapping.MAIL_DAO);
        mailDAO.addMail(new Message(sender, receiver, text, null));
        request.getSession().setAttribute(Mapping.RECEIVER, receiver);
        response.sendRedirect("privateMessenger.jsp?"+ Mapping.RECEIVER + "=" + receiver);
    }
}
