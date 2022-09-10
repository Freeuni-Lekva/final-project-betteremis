<%@ page import="static Servlets.ErrorMessages.ERROR_MESSAGE" %><%--
  Created by IntelliJ IDEA.
  User: gluncho
  Date: 8/13/2022
  Time: 11:46 AM
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
    <title>Add Friend</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User List</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link rel="stylesheet" href="css/addFriend.css">
</head>
<body style="background-color: #434750; color: white">
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

<%
    if(request.getSession().getAttribute(ERROR_MESSAGE) != null){
        %>
        <p style="text-align: center"> <%=request.getSession().getAttribute(ERROR_MESSAGE)%></p>
<%
        request.getSession().removeAttribute(ERROR_MESSAGE);
    }
%>

<form action="SendFriendRequestServlet" method="get">
    <div class="input">
        <input type="text" name="email" id="email">
        <label for="email"> Send Friend Request! </label>
    </div>
    <button class="fancyButton" type="submit" role="button">Send</button>
</form>
<form action="RemoveFriendServlet" method="get">
    <div class="input2">
        <input type="text" name="email" id="email2">
        <label for="email2"> Remove Friend </label>
    </div>
    <button class="fancyButton" style="justify-content: center" type="submit" role="button">Remove</button>
</form>

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
</body>
</html>
