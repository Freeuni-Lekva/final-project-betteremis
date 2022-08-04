<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.*" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 04.08.22
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Lecturer lecturer = (Lecturer) session.getAttribute(Mapping.USER_OBJECT);
    SubjectHistoryDAO subDao = (SubjectHistoryDAO) application.getAttribute(Mapping.SUBJECT_HISTORY_DAO);
    String subName = (String) request.getSession().getAttribute("subjectName");
    Map<Integer, ArrayList<Student> > students = subDao.getAllStudentsOfSubject(subName);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--TODO : Make students table ordered by semesters. --%>


</body>
</html>
