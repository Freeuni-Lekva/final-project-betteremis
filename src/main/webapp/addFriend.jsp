<%@ page import="static Servlets.ErrorMessages.ERROR_MESSAGE" %><%--
  Created by IntelliJ IDEA.
  User: gluncho
  Date: 8/13/2022
  Time: 11:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Friend</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User List</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body>
<!-- navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-custom">
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
            <li class="nav-item">
                <a class="nav-link" href="friend-requests.jsp">Friend requests
                </a>
            </li>
        </ul>
    </div>
</nav>
<p>lol</p>

<%
    if(request.getSession().getAttribute(ERROR_MESSAGE) != null){
        %>
        <p> <%=request.getSession().getAttribute(ERROR_MESSAGE)%></p>
<%
        request.getSession().removeAttribute(ERROR_MESSAGE);
    }
%>
<form action="SendFriendRequestServlet" method="get">
    <input type="text" name="email">
    <input type="submit" value="Send Friend Request">
</form>
</body>
</html>
