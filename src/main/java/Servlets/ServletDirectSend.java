package Servlets;

import DAO.Mapping;
import DAO.SqlMailDAO;
import DAO.SqlUserDAO;
import Model.Message;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletDirectSend", value = "/ServletDirectSend")
public class ServletDirectSend extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = request.getParameter(Mapping.SENDER);
        String receiver = request.getParameter(Mapping.RECEIVER);
        String text = request.getParameter("sendMessage");

        SqlMailDAO mailDAO = (SqlMailDAO) request.getServletContext().getAttribute(Mapping.MAIL_DAO);
        mailDAO.addMail(new Message(sender, receiver, text, null));
        request.getSession().setAttribute(Mapping.RECEIVER, receiver);
        response.sendRedirect("privateMessenger.jsp?"+ Mapping.RECEIVER + "=" + receiver);
    }
}
