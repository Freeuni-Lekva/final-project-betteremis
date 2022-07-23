<%@ page import="Model.Student" %>
<%@ page import="DAO.Mapping" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 21.07.22
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Student student = (Student) session.getAttribute(Mapping.USER_OBJECT);
%>
<html>
<head>
    <title>Card</title>
    <link rel="stylesheet" href="cardStyle.css">
</head>
<body>

</body>

<table>

    <thead>
        <th> Unknown Semester </th>
        <tr >
            <th><h1>Subject Name</h1></th>
            <th><h1>Lecturer</h1></th>
            <th><h1>Credits</h1></th>
            <th><h1>Mark</h1></th>
            <th><h1>Grade</h1></th>
            <th><h1>Syllabus</h1></th>
        </tr>
    </thead>

    <tbody>
        <%--For testing purposes--%>
        <tr>
            <td>Mathematics</td>
            <td>Tsimakuridze</td>
            <td>6</td>
            <td>100</td>
            <td>A</td>
            <td>Syllabus is currently unavailable</td>
        </tr>

    </tbody>
</table>

<table>

    <thead>
        <th> Known Semester </th>

        <%--For testing purposes--%>
        <tr >
            <th><h1>Subject Name</h1></th>
            <th><h1>Lecturer</h1></th>
            <th><h1>Credits</h1></th>
            <th><h1>Mark</h1></th>
            <th><h1>Grade</h1></th>
            <th><h1>Syllabus</h1></th>
        </tr>
    </thead>

    <tbody>


        <tr>
            <td>Mathematics</td>
            <td>Tsimakuridze</td>
            <td>6</td>
            <td>100</td>
            <td>A</td>
            <td>Syllabus is currently unavailable</td>
        </tr>
        <tr>
            <td>Theory of computation</td>
            <td>Tsimakuridze</td>
            <td>6</td>
            <td>100</td>
            <td>A</td>
            <td>Syllabus is currently unavailable</td>
        </tr>
        <tr>
            <td>Physics</td>
            <td>Tsimakuridze</td>
            <td>6</td>
            <td>100</td>
            <td>A</td>
            <td>Syllabus is currently unavailable</td>
        </tr>

    </tbody>

</table>


</html>
