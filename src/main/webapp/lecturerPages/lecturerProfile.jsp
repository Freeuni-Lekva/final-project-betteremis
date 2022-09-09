<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %>
<%@ page import="Model.Student" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Model.Lecturer" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 20.07.22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Lecturer lecturer = (Lecturer) session.getAttribute(Mapping.USER_OBJECT);
%>

<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

<div class="Profile">
    <img src="https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg">
    <br />
    <li style="font-family:Times ; font-size: 20px; color: lightyellow"><%=lecturer.getLastName()%></li>
    <li style="font-family:Times ; font-size: 20px; color: lightyellow" ><%=lecturer.getFirstName()%></li>
</div>

<div class="ProfileInfo">
    <h>Lecturer's Personal Info</h>
    <body>
    <br />
    <br />
    <li><a>Profession : <%=lecturer.getProfession()%> </a></li>
    <br />
    <li><a>Gender : <%=lecturer.getGender().toString()%> </a></li>
    <br />
    <li><a>Birth Date : <%=new SimpleDateFormat("dd.MM.yyyy").format(lecturer.getBirthDate())%> </a></li>
    <br />
    <li><a>Living Address : <%=lecturer.getAddress()%> </a></li>
    <br />
    <li><a>Status : <%=lecturer.getStatus()%> </a></li>
    <br />
    <li><a>Phone Number : <%=lecturer.getPhone()%> </a></li>
    <br />
    </body>
</div>
<div class="Menu">

    <%--            href ლინკავს მხოლოდ ტექტს. გადასაკეთებელია , რომ მთლიანი li ობიექტი იყოს clickable.--%>

    <ul>
        <li style="--i:7;" > <a href="../Chat/Chat.jsp"> Chat</a></li>
        <li style="--i:6;"> <a href="../friends.jsp">Friends</a></li>
        <li style="--i:5;" > <a href="lecturerProfile.jsp"> Personal Info</a></li>
        <li style="--i:4;"> <a href="classrooms.jsp"> Classrooms </a> </li>
        <li style="--i:3;"> <a href="subjects.jsp"> Subjects</a> </li>
        <li style="--i:1;"> <a href="../LogOutServlet"> Log Out</a> </li>
    </ul>
</div>
</body>

</html>
