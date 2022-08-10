package Servlets;

import DAO.Interfaces.FriendsDAO;
import DAO.Interfaces.UserDAO;
import Model.*;
import Services.FriendService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static DAO.Mapping.*;

@WebServlet(name = "FriendRequestServlet", value = "/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        String email = request.getParameter(EMAIL);
        User user = (User) request.getSession().getAttribute(USER_OBJECT);
        UserDAO dao = (UserDAO) context.getAttribute(USER_DAO);
        User friend = dao.getUserByEmail(email);
        if(email == null || user == null || friend == null){
            response.sendRedirect("invalidUser.jsp");
        }
        FriendsDAO friendsDAO = (FriendsDAO) context.getAttribute(FRIENDS_DAO);
        FriendService service = (FriendService) context.getAttribute(FRIEND_SERVICE);
        service.addFriend(user, friend, friendsDAO);
    }

}
