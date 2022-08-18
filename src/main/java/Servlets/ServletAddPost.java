package Servlets;

import DAO.Interfaces.ClassroomPostsDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import DAO.SqlClassroomPostsDAO;
import Model.Post;
import Model.USERTYPE;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "ServletAddPost", value = "/ServletAddPost")
public class ServletAddPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession curSession = request.getSession();
        int classroomID = (int)request.getAttribute(Mapping.CLASSROOM_ID);
        //int tableID = (int)request.getAttribute(Mapping.TABLE_ID);
        ClassroomPostsDAO classroomPostsDAO = (ClassroomPostsDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_POSTS_DAO);
        String postStr = (String) request.getAttribute(Mapping.USER_INPUT);
        User usr = (User)curSession.getAttribute(Mapping.USER_OBJECT);
        UserDAO userDAO = (UserDAO)request.getServletContext().getAttribute(Mapping.USER_DAO);
        Post post = new Post(-1, classroomID, userDAO.getIDByEmail(usr.getEmail()), postStr, new Timestamp(System.currentTimeMillis()));
        classroomPostsDAO.addPost(post);
        //TODO:send to posts page, in other words refresh.
//        response.sendRedirect();

    }
}
