<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 20.07.22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);

%>

<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <div class="Profile">
        <img src="https://st2.depositphotos.com/1006318/5909/v/950/depositphotos_59095529-stock-illustration-profile-icon-male-avatar.jpg">
        <br />
        <li style="font-family:Times ; font-size: 20px; color: lightyellow">Shishniashvili</li>
        <li style="font-family:Times ; font-size: 20px; color: lightyellow" >Dimitri</li>
    </div>

    <div class="ProfileInfo">
        <h>Personal Info</h>
        <body>
            <br />
            <br />
            <li><a>Field : </a></li>
            <br />
            <li><a>Academic Level : </a></li>
            <br />
            <li><a>Current Semester : </a></li>
            <br />
            <li><a>Gender : </a></li>
            <br />
            <li><a> Nationality : </a></li>
            <br />
            <li><a>Birth Date : </a></li>
            <br />
            <li><a>Living Address : </a></li>
            <br />
            <li><a>Status : </a></li>
            <br />
            <li><a>School : </a></li>
            <br />
            <li><a>Total Credits : </a></li>
            <br />
            <li><a>GPA : </a></li>
            <br />
            <li><a>Phone Number : </a></li>
            <br />
            <li><a>Group : </a></li>
            <br />
        </body>
    </div>
    <div class="Menu">

<%--            href ლინკავს მხოლოდ ტექტს. გადასაკეთებელია , რომ მთლიანი li ობიექტი იყოს clickable.--%>

        <ul>
            <li style="--i:7;" > <a href="profile.jsp"> Personal Info</a></li>
            <li style="--i:6;"> <a href="studyingCard.jsp"> Studying Card </a> </li>
            <li style="--i:5;"> <a href="registration.jsp"> Academic Registration</a> </li>
            <li style="--i:4;"> <a href="records.jsp"> Academic Records</a> </li>
            <li style="--i:3;"> <a href="finances.jsp"> Finances</a> </li>
            <li style="--i:2;"> <a href="library.jsp"> Library</a> </li>
            <li style="--i:1;"> <a href="#"> Log Out</a> </li>
        </ul>
    </div>
</body>

</html>
