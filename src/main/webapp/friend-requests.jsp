<%@ page import="Model.*" %>
<%@ page import="static DAO.Mapping.*" %>
<%@ page import="DAO.Interfaces.FriendsDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="Services.FriendService" %><%--
  Created by IntelliJ IDEA.
  User: gluncho
  Date: 7/29/2022
  Time: 9:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null) {
        redirect(request, response);
        return;
    }
%>
<head>
    <%
        FriendService service = (FriendService) request.getServletContext().getAttribute(FRIEND_SERVICE);
        List<User> friends = service.getAllRequests(user, request.getServletContext());
//        friends.add(new User("hello@freeuni.edu.ge", "passw", USERTYPE.ADMIN));
//        for(int i=0; i<100; i++){
//            friends.add(new User("hello"+i, "passw"+i, USERTYPE.ADMIN));
//        }
        int size = friends.size();
    %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link rel="stylesheet" href="css/friend-requests.css">
    <title>Friend Requests</title>
</head>
<body style="background-color: #434750">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="addFriend.jsp">Add Friend</a>
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
                <a class="nav-link" href="friend-requests.jsp">Friend requests
                </a>
                <span class="sr-only">(current)</span>
            </li>
        </ul>
    </div>
</nav>
<h1 style="text-align: center; color: #F44336"> You have <%=size%> friend requests in total:</h1>
<div class="friend-requests">
    <%
        int cnt = 0;
        for(User friend : friends){
            String firstName = friend.getFirstName();
            String lastName = friend.getLastName();
            String email = friend.getEmail();
            String type = friend.getType().toString();
    %>
    <div class="friend-box">
        <div class="friend-profile"
             style="background-image: url('https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg');"></div>
    <div class="name-box"><%=(firstName+" " + lastName)%></div>
    <div class="user-name-box"><%=firstName%> sent you a friend request<br> <%=type%></div>
    <div class="request-btn-row" data-username="angrytiger584">
        <a href="<%=request.getContextPath()%>/FriendRequestServlet?email=<%=email%>&response=Accept" class="friend-request accept-request btn btn-primary">Accept</a>
<%--        <button class="friend-request accept-request" id="<%=(2*cnt)%>" onclick="buttonClicked(this)" data-username="angrytiger584">Accept--%>
<%--        </button>--%>
<%--        <button class="friend-request decline-request" id="<%=(2*cnt+1)%>" onclick="buttonClicked(this)" data-username="angrytiger584">Decline--%>
<%--        </button>--%>
        <a href="<%=request.getContextPath()%>/FriendRequestServlet?email=<%=email%>&response=Decline" class="friend-request decline-request-request btn btn-primary" style="background-color: #E91E63">Decline</a>
    </div>
    </div>
    <%
        cnt++;
        }
    %>
</div>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
        integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
        integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
        crossorigin="anonymous"></script>
<script src="friend-requests.js" type="text/javascript"></script>
</body>
</html>
