package Servlets;

import DAO.Interfaces.ClassroomPostsDAO;
import DAO.Interfaces.CommentsDAO;
import DAO.Interfaces.UserDAO;
import DAO.Mapping;
import Model.Comment;
import Model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static Helper.ErrorPageRedirector.redirect;

@WebServlet(name = "ServletAddComment", value = "/ServletAddComment")
public class ServletAddComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User usr = (User)request.getSession().getAttribute(Mapping.USER_OBJECT);
        if(usr == null){
            redirect(request, response);
            return;
        }
        String comment = request.getParameter(Mapping.COMMENT);
        int postID = Integer.parseInt(request.getParameter(Mapping.POST_ID));
        System.out.println(postID + "  " + comment);
        int classroomID = Integer.parseInt(request.getParameter(Mapping.CLASSROOM_ID));
        CommentsDAO commentsDAO = (CommentsDAO) request.getServletContext().getAttribute(Mapping.COMMENTS_DAO);
        UserDAO userDAO = (UserDAO)request.getServletContext().getAttribute(Mapping.USER_DAO);
        Comment comment1 = new Comment(postID,userDAO.getIDByEmail(usr.getEmail()),comment,null);
        commentsDAO.addComment(comment1);
        response.sendRedirect("classroom.jsp?" + Mapping.CLASSROOM_ID + "=" + classroomID);
    }
}
