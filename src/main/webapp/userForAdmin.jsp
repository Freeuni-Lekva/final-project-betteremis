<%@ page import="Model.User" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.Student" %>
<%@ page import="Model.Lecturer" %>
<%@ page import="Model.USERTYPE" %>
<%@ page import="DAO.Interfaces.LecturerDAO" %>
<%@ page import="DAO.Interfaces.StudentDAO" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 02.08.22
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    User user = (User) request.getAttribute(Mapping.USER_OBJECT);
%>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="profileForAdmin.scss">

</head>
<body>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.13.0/css/all.css">
<div class="card">
    <nav class="navbar">
        <div class="container flex">
            <div class="someone">
                <img src="https://i.postimg.cc/3xkf5Pmv/woman.png">
                <div class="text">
                    <h1><%=user.getFirstName() + " " + user.getLastName()%></h1>
                    <p><%=user.getType().toString()%></p>
                </div>
            </div>
        </div>
    </nav>
    <section class="about">
        <div class="who">
            <form action="#" method="post">
<%--                TODO : CHECK STATUS AND CREATE FORM--%>
            </form>
            <form action="#" method="post">
<%--                TODO : CHECK REGISTRATION AND CREATE FORM--%>
            </form>
            <span><strong>Phone :</strong><%= user.getPhone() %></span>
            <span><strong>Email :</strong><%= user.getEmail() %> </span>
            <span><strong>Address :</strong> <%= user.getAddress() %></span>
        </div>
    </section>
</div>
</body>
</html>
