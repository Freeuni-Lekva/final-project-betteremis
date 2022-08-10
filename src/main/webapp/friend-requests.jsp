<%@ page import="Model.*" %>
<%@ page import="static DAO.Mapping.*" %>
<%@ page import="DAO.Interfaces.FriendsDAO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: gluncho
  Date: 7/29/2022
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        User user = (User) request.getSession().getAttribute(USER_OBJECT);
        if(user == null){
            response.sendRedirect("invalidUser.jsp");
        }
        FriendsDAO dao = (FriendsDAO) request.getServletContext().getAttribute(FRIENDS_DAO);
        List<User> friends = dao.getAllFriends(user, false);
        friends.add(new User("hello@freeuni.edu.ge", "passw", USERTYPE.ADMIN));
        for(int i=0; i<100; i++){
            friends.add(new User("hello"+i, "passw"+i, USERTYPE.ADMIN));
        }
    %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="friend-requests.js"></script>
    <link rel="stylesheet" href="css/friend-requests.css">
    <title>Friend Requests</title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/index.html">People List</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="friends.jsp">Friends
                </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="studentPages/friend-requests.jsp">Friend requests
                    <span class="sr-only">(current)</span>
                </a>
            </li>
        </ul>
    </div>
</nav>
<div class="friend-requests">
    <%
        int cnt = 0;
        for(User friend : friends){
//            String firstName = friend.getFirstName();
//            String lastName = friend.getLastName();
            String email = friend.getEmail();
            String type = friend.getType().toString();
    %>
    <div class="friend-box">
        <div class="friend-profile"
             style="background-image: url('https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg');"></div>
    <div class="name-box"><%=email%>></div>
    <div class="user-name-box">@Nick sent u a friend request<br> <%=type%></div>
    <div class="request-btn-row" data-username="angrytiger584">
        <a href="<%=request.getContextPath()%>/FriendRequestServlet?email=<%=email%>" class="friend-request accept-request btn btn-primary">Accept</a>
<%--        <button class="friend-request accept-request" id="<%=(2*cnt)%>" onclick="buttonClicked(this)" data-username="angrytiger584">Accept--%>
<%--        </button>--%>
<%--        <button class="friend-request decline-request" id="<%=(2*cnt+1)%>" onclick="buttonClicked(this)" data-username="angrytiger584">Decline--%>
<%--        </button>--%>
        <a href="<%=request.getContextPath()%>/FriendRequestServlet?email=<%=email%>" id="<%=email%>" class="friend-request decline-request-request btn btn-primary">Decline</a>
    </div>
    </div>
    <%
        cnt++;
        }
    %>
</div>
</body>
</html>
