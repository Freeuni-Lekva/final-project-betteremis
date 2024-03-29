<%@ page import="DAO.Mapping" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 20.07.22
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="Model.User" %>
<%@ page import="static Model.USERTYPE.*" %>
<%@ page import="DAO.Interfaces.SubjectHistoryDAO" %>
<%@ page import="static DAO.Mapping.SUBJECT_HISTORY_DAO" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null || user.getType() != STUDENT) {
        redirect(request, response);
        return;
    }
    Student student = (Student) session.getAttribute(Mapping.USER_OBJECT);
    SubjectHistoryDAO dao = (SubjectHistoryDAO) application.getAttribute(SUBJECT_HISTORY_DAO);
    Map<Integer, ArrayList<Subject>> subjects = dao.getCompletedSubjects(student);
    int totalCredits = 0;
    for(Integer semester : subjects.keySet()){
        for(Subject subject : subjects.get(semester)){
            totalCredits += subject.getNumCredits();
        }
    }
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
        <li style="font-family:Times ; font-size: 20px; color: lightyellow"><%=student.getLastName()%></li>
        <li style="font-family:Times ; font-size: 20px; color: lightyellow" ><%=student.getFirstName()%></li>
    </div>

    <div class="ProfileInfo">
        <h>Student's Personal Info</h>
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
            <li><a>Total Credits : <%=totalCredits%> </a></li>
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
            <li style="--i:9;" > <a href="../Chat/Chat.jsp"> Chat</a></li>
            <li style="--i:8;"> <a href="../friends.jsp">Friends</a></li>
            <li style="--i:7;" > <a href="studentProfile.jsp"> Personal Info</a></li>
            <li style="--i:6;"> <a href="studyingCard.jsp"> Studying Card </a> </li>
            <li style="--i:5;"> <a href="../registration.jsp"> Academic Registration</a> </li>
            <li style="--i:4;"> <a href="records.jsp"> Academic Records</a> </li>
            <li style="--i:3;"> <a href="classrooms.jsp"> Classrooms </a> </li>
            <li style="--i:1;"> <a href="../LogOutServlet"> Log Out</a> </li>
        </ul>
    </div>
</body>

</html>
