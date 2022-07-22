<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %>
<%@ page import="Model.Student" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 20.07.22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Student student = (Student) session.getAttribute(Mapping.USER_OBJECT);
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
        <li style="font-family:Times ; font-size: 20px; color: lightyellow"><%=student.getLastName()%></li>
        <li style="font-family:Times ; font-size: 20px; color: lightyellow" ><%=student.getFirstName()%></li>
    </div>

    <div class="ProfileInfo">
        <h>Personal Info</h>
        <body>
            <br />
            <br />
            <li><a>Profession : <%=student.getProfession()%> </a></li>
            <br />
            <li><a>Current Semester : <%=student.getCurrentSemester()%> </a></li>
            <br />
            <li><a>Gender : <%=student.getGender().toString()%> </a></li>
            <br />
            <li><a>Birth Date : <%=new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthDate())%> </a></li>
            <br />
            <li><a>Living Address : <%=student.getAddress()%> </a></li>
            <br />
            <li><a>Status : <%=student.getStatus()%> </a></li>
            <br />
            <li><a>School : <%=student.getSchool()%> </a></li>
            <br />
            <li><a>Total Credits : <%=student.getCreditsDone()%> </a></li>
            <br />
            <li><a>GPA : <%=student.getGpa()%> </a></li>
            <br />
            <li><a>Phone Number : <%=student.getPhone()%> </a></li>
            <br />
            <li><a>Group : <%=student.getGroup()%> </a></li>
            <br />
        </body>
    </div>
    <div class="Menu">

<%--            href ლინკავს მხოლოდ ტექტს. გადასაკეთებელია , რომ მთლიანი li ობიექტი იყოს clickable.--%>

        <ul>
            <li style="--i:7;" > <a href="studentProfile.jsp"> Personal Info</a></li>
            <li style="--i:6;"> <a href="studyingCard.jsp"> Studying Card </a> </li>
            <li style="--i:5;"> <a href="registration.jsp"> Academic Registration</a> </li>
            <li style="--i:4;"> <a href="records.jsp"> Academic Records</a> </li>
            <li style="--i:3;"> <a href="finances.jsp"> Finances</a> </li>
            <li style="--i:2;"> <a href="library.jsp"> Library</a> </li>
            <li style="--i:1;"> <a href="LogOutServlet"> Log Out</a> </li>
        </ul>
    </div>
</body>

</html>
