<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="Model.*" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 02.08.22
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    UserDAO userDAO = (UserDAO) application.getAttribute(Mapping.USER_DAO);
    String email = (String) request.getSession().getAttribute(Mapping.EMAIL);
    User user = userDAO.getUserByEmail(email);
    STATUS status ;
    String address;
    Number phone;
    String firstName;
    String lastName;
    if(user.getType() == USERTYPE.STUDENT){
        StudentDAO studentDAO = (StudentDAO) application.getAttribute(Mapping.STUDENT_DAO);
        Student student = studentDAO.getStudentWithEmail(email);
        status = student.getStatus();
        address = student.getAddress();
        phone = student.getPhone();
        firstName = student.getFirstName();
        lastName = student.getLastName();
    }else{
        LecturerDAO lecturerDAO = (LecturerDAO) application.getAttribute(Mapping.LECTURER_DAO);
        Lecturer lecturer = lecturerDAO.getLecturerWithEmail(email);
        status = lecturer.getStatus();
        address = lecturer.getAddress();
        phone = lecturer.getPhone();
        firstName = lecturer.getFirstName();
        lastName = lecturer.getLastName();
    }
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
                    <h1><%=firstName + " " + lastName%></h1>
                    <p><%=user.getType().toString()%></p>
                </div>
            </div>
        </div>
    </nav>
    <section class="about">
        <div class="who">
            <form action="ServletChangeStatus" method="POST">
<%--                TODO : CHECK STATUS AND CREATE FORM--%>
                <%
                    String inp1 = "<input type=\"submit\" name=\"ban\" value=\"Ban User\">";
                    String inp2 = "<input type=\"submit\" name=\"removeBan\" value=\"Remove Ban\">";
                    if(status == STATUS.ACTIVE){
                        out.println(inp1);
                    }else{
                        out.println(inp2);
                    }
                %>
                <input type="hidden" name = "email" value = <%= user.getEmail() %> >
            </form>
            <span><strong>User Currently Is : </strong><%= status.toString() %></span>
            <span><strong>Phone : </strong><%= phone %></span>
            <span><strong>Email : </strong><%= user.getEmail() %> </span>
            <span><strong>Address : </strong> <%= address %></span>
        </div>
    </section>
</div>
</body>
</html>
