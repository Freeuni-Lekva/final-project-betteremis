<%@ page import="Model.Lecturer" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="DAO.Interfaces.LecturerDAO" %>
<%@ page import="Model.Subject" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 02.08.22
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Lecturer lecturer = (Lecturer) session.getAttribute(Mapping.USER_OBJECT);
    LecturerDAO lecturerDAO = (LecturerDAO) application.getAttribute(Mapping.LECTURER_DAO);
    List<Subject> allSubsOfLecturer = lecturerDAO.getAllSubjects(lecturer.getEmail());
%>

<%!
    private String decorate(Subject subject){
        String res = "<tr>\n" +
                "                <td>" + subject.getName() + "</td>\n" +
                "                <td>" + subject.getNumCredits() + "</td>\n" +
                "                <td>" + "Currently unavailable" + "</td>\n" +
                "                <td>"+ "Currently unavailable" + "</td>\n" +
                "                <td>\n" +
                "               <form action = \"../ServletSeeSubject\" method = \"POST\">\n" +
                "                   <input type = \"hidden\" name = \"subjectName\" value = \""+ subject.getName() + "\"/>\n" +
                "                   <input type = \"submit\" value = \"See More\"/>\n" +
                "               </form>\n"
                                +  "</td>\n" +
                "     </tr>";

        return res;
    }
%>

<html>
<head>
    <link rel="stylesheet" href="../css/cardStyle.css">
    <title>Subjects</title>
</head>
<body>
    <table>
         <thead>
            <th>All Subjects</th>
            <tr >
                <th><h1>Subject Name</h1></th>
                <th><h1>Credits</h1></th>
                <th><h1>Score distribution</h1></th>
                <th><h1>Syllabus</h1></th>
                <th><h1>See More</h1></th>
            </tr>
        </thead>
        <tbody>
            <%
                for(Subject subject : allSubsOfLecturer){
                    out.println(decorate(subject));
                }
            %>
        </tbody>
    </table>
</body>
</html>
