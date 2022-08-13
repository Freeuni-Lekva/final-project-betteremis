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
import static Model.USERTYPE.ADMIN;

@WebServlet(name = "FriendRequestServlet", value = "/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
    public static final String FRIEND_REQUEST_RESPONSE = "response";
    public static final String FRIEND_REQUEST_ACCEPT = "Accept";
    public static final String FRIEND_REQUEST_DECLINE = "Decline";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        String email = request.getParameter(EMAIL);
        String friendRequestResponse = request.getParameter(FRIEND_REQUEST_RESPONSE);
        User user = (User) request.getSession().getAttribute(USER_OBJECT);
        UserDAO dao = (UserDAO) context.getAttribute(USER_DAO);
        User friend = dao.getUserByEmail(email);
        if(email == null || user == null || friend == null || friend.getType() == ADMIN){
            response.sendRedirect("invalidUser.jsp");
        }
        FriendsDAO friendsDAO = (FriendsDAO) context.getAttribute(FRIENDS_DAO);
        FriendService friendService = (FriendService) context.getAttribute(FRIEND_SERVICE);
        if(friendRequestResponse.equals(FRIEND_REQUEST_ACCEPT)){
            if(friendService.acceptRequest(user, friend, friendsDAO)){
                response.sendRedirect("friendRequests.jsp");
            }else{
                response.sendRedirect("invalidUser.jsp");
            }
        }else if(friendRequestResponse.equals(FRIEND_REQUEST_DECLINE)){
            if(friendService.declineRequest(user, friend, friendsDAO)){
                response.sendRedirect("friendRequests.jsp");
            }else{
                response.sendRedirect("invalidUser.jsp");
            }
        }
    }

}
