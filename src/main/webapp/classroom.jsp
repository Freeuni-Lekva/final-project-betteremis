<%@ page import="Model.Post" %>
<%@ page import="DAO.Interfaces.ClassroomDAO" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.ClassroomPostsDAO" %>
<%@ page import="DAO.Interfaces.CommentsDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Interfaces.UserDAO" %>
<%@ page import="Model.Comment" %>
<%@ page import="java.util.Collections" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 15.08.22
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ClassroomPostsDAO postsDAO = (ClassroomPostsDAO) request.getServletContext().getAttribute(Mapping.CLASSROOM_POSTS_DAO);
    UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute(Mapping.USER_DAO);
    CommentsDAO commentsDAO = (CommentsDAO) request.getServletContext().getAttribute(Mapping.COMMENTS_DAO);
    int classroomID = Integer.parseInt(request.getParameter(Mapping.CLASSROOM_ID));
    List<Post> postList = postsDAO.getPostsByClassroomID(classroomID, true);
    Collections.reverse(postList);

%>

<%!
    private String commentDecorator(Comment comment, String email){
        String result = "                       <div class=\"single-comment\">\n" +
                "                                    <p class=\"mb-0 pt-0 name\">"+ email + " , "+comment.getTime().toString() +"</p>\n" +
                "                                    <p><textarea type=\"text\"  name=\"content\" style=\"width: 1130px ; height: 12px\" disabled>"+comment.getMessage() + "</textarea></p>\n" +
                "                                </div>\n";

        return result;
    }
%>

<%!
    private String decorate(Post post, String email,CommentsDAO commentsDAO,UserDAO userDAO, int classroomID ){
        String result ="<div class=\"container\" style=\"width: 1200px; margin: 0 auto;\">\n" +
                "    <div class=\"post pb-4\">\n" +
                "        <div class=\"right\" >\n" +
                "            <div class='d-flex'>\n" +
                "                <div class=\"author\" style=\"justify-content: center; justify-items: center; align-items: center; text-align: center\">\n" +
                "                    <h2 style=\"font-size: 20px\">"+ email + "</h2>\n" +
                "                </div>\n" +
                "                <div class=\"date\">\n" +
                "                    <h2>"+ post.getTime().toString() +"</h2>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"separator\"></div>\n" +
                "            <p>\n" +
                "            <textarea type=\"text\"  name=\"content\" style=\"width: 1130px ; height: 100px\" disabled>" + post.getPostContent() +"</textarea>\n" +
                "            </p>\n" +
                "            <div class=\"separator\"></div>\n" +
                "            <div>\n" +
                "                <form id=\"addComment"+ post.getTableID() + "\" action=\"ServletAddComment\" method=\"post\">\n" +
                "                    <textarea name=\""+Mapping.COMMENT +"\" form=\"addComment" + post.getTableID() + "\" type=\"text\" class=\"col-9\" placeholder=\"Write a comment\" style=\"width: 500px\"></textarea>\n" +
                "                    <input type=\"hidden\" name =\"" + Mapping.POST_ID + "\" value=\"" + post.getTableID() + "\">\n" +
                "                    <input type=\"hidden\" name =\"" + Mapping.CLASSROOM_ID + "\" value=\""   +classroomID + "\">\n" +
                "                    <button class='btn' type=\"submit\">Add Comment</button>\n"+
                "                </form>\n" +
                "            </div>\n" +
                "            <div class=\"separator\"></div>\n" +
                "            <div class=\"comments\">\n";
        List<Comment> comments = commentsDAO.getCommentsByPostID(post.getTableID(),false);
        for(Comment comment : comments){
            result+= commentDecorator(comment,userDAO.getEmailByID(comment.getWriterID()));
        }
        result +=
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        return result;
    }
%>



<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="classroomStyle.scss">
</head>
<body style="text-align: center">
<header style="position: sticky ; top : 0; z-index: 999  ; font-family: sans-serif; border: 5px slateblue;
border-radius: 40px; font-size: 30px ; background-color: #016ba8; color: white">Classroom</header>
<div style="width: 1200px; margin: 0 auto;">
    <form id="addPost" action="ServletAddPost" method="post">
        <textarea form="addPost" type="text" placeholder="Write Post Content" name="<%=Mapping.USER_INPUT%>" style="width: 1200px ; height: 100px"></textarea>
        </br>
        <input type="hidden" name = "<%=Mapping.CLASSROOM_ID%>" value="<%=classroomID%>">
        <button class='btn col-3' type="submit" style="width: 200px; background-color: #016ba8; color: white"> Post </button>
    </form>
</div>


<%
    for(Post post: postList){
        out.println(decorate(post, userDAO.getEmailByID(post.getUserID()), commentsDAO, userDAO, classroomID));
    }
%>

</body>
</html>
