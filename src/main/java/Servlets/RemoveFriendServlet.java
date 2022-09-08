package Servlets;

import DAO.Interfaces.FriendsDAO;
import DAO.Interfaces.UserDAO;
import Model.User;
import Services.FriendService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet.*;
import javax.servlet.annotation.*;
import javax.websocket.Session;
import java.io.IOException;

import static DAO.Mapping.*;
import static Helper.ErrorPageRedirector.redirect;
import static Services.FriendService.RemoveResult.REMOVE_FAIL;
import static Services.FriendService.RemoveResult.REMOVE_SUCCESS;
import static Services.FriendService.RequestResult.*;
import static Services.FriendService.RequestResult.REQUEST_SAME_USER;
import static Servlets.ErrorMessages.*;

@WebServlet(name = "RemoveFriendServlet", value = "/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sce = req.getServletContext();
        HttpSession session = req.getSession();
        String email = req.getParameter(EMAIL);
        UserDAO userDAO = (UserDAO) sce.getAttribute(USER_DAO);
        FriendService friendService = (FriendService) sce.getAttribute(FRIEND_SERVICE);
        User currentUser = (User) session.getAttribute(USER_OBJECT);
        if(currentUser == null) {
            redirect(req, resp);
        }
        FriendsDAO friendsDAO = (FriendsDAO) sce.getAttribute(FRIENDS_DAO);
        FriendService.RemoveResult result = friendService.removeFriend(currentUser, email, friendsDAO, userDAO);
        String message = null;
        if(result == REMOVE_SUCCESS){
            message = "Removed " + email + " from your friend list successfully.";
        }else if(result == REMOVE_FAIL){
            message = ERROR_CANT_REMOVE;
        }
        req.getSession().setAttribute(ERROR_MESSAGE, message);
        resp.sendRedirect("addFriend.jsp");
    }

}
