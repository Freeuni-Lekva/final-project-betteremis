package Servlets;

import DAO.Interfaces.*;
import Model.User;
import Services.FriendService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static DAO.Mapping.*;
import static Services.FriendService.RequestResult.*;
import static Servlets.ErrorMessages.ERROR_INVALID_USER;
import static Servlets.ErrorMessages.ERROR_MESSAGE;

@WebServlet(name = "SendFriendRequestServlet", value = "/SendFriendRequestServlet")
public class SendFriendRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = req.getServletContext();
        String email = req.getParameter(EMAIL);
        UserDAO userDAO = (UserDAO) sc.getAttribute(USER_DAO);
        FriendService friendService = (FriendService) sc.getAttribute(FRIEND_SERVICE);
        User currentUser = (User) req.getSession().getAttribute(USER_OBJECT);
        FriendsDAO friendsDAO = (FriendsDAO) sc.getAttribute(FRIENDS_DAO);
        FriendService.RequestResult result = friendService.sendRequest(currentUser, email, friendsDAO, userDAO);
        String message = null;
        if(result == REQUEST_SUCCESS){
            message = "Friend request sent to " + email + " successfully.";
        }else if(result == REQUEST_ALREADY_EXISTS){
            message = "You already have sent friend request to " + email + ".";
        }else if(result == REQUEST_BECAME_FRIENDS){
            message = "You and " + email + " became friends!";
        }else if(result == REQUEST_USER_NOT_FOUND){
            message = ERROR_INVALID_USER + " Please enter a valid user";
        }else if(result == REQUEST_ARE_FRIENDS){
            message = "You and " + email + " already are friends! Please enter a different email";
        }else if(result == REQUEST_SAME_USER){
            message = "You can't become friends with yourself! Please enter a different email";
        }
        req.getSession().setAttribute(ERROR_MESSAGE, message);
        resp.sendRedirect("addFriend.jsp");
    }
}
