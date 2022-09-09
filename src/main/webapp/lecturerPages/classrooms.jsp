<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.Classroom" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Interfaces.ClassroomDAO" %>
<%@ page import="DAO.Interfaces.SubjectDAO" %>
<%@ page import="DAO.Interfaces.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 21.07.22
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="static Helper.ErrorPageRedirector.redirect" %>
<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.USERTYPE" %>
<%
    User user = (User) session.getAttribute(Mapping.USER_OBJECT);
    if(user == null || user.getType() != USERTYPE.LECTURER) {
        redirect(request, response);
        return;
    }
    String email = user.getEmail();
    ClassroomDAO classroomDAO = (ClassroomDAO) application.getAttribute(Mapping.CLASSROOM_DAO);
    SubjectDAO subjectDAO = (SubjectDAO) application.getAttribute(Mapping.SUBJECT_DAO);
    List<Classroom> classrooms = classroomDAO.getClassroomsByLecturer(email,false);
%>

<%!
    private String decorate(Classroom classroom, String sub_name){
        String result = " <tr>\n" +
                "            <td>" + sub_name + "</td>\n" +
                "            <td>" + classroom.getSemester()+ "</td>\n" +
                "          <td>\n" +
                "              <a href=\"../classroom.jsp?"+Mapping.CLASSROOM_ID + "=" +classroom.getTableID() + "\" >Visit</a>\n"+
                "           </td>\n"+
                "         </tr>\n";
        return result;
    }
%>
<html>
<head>
    <title>Classrooms</title>
    <link rel="stylesheet" href="../css/cardStyle.css">

</head>
<body>

<table>
    <thead>
    <th>Enrolled Classrooms</th>
    <tr >
        <th><h1>Classroom's Subject</h1></th>
        <th><h1>Visit Classroom</h1></th>
    </tr>
    </thead>

    <tbody>
    <%
        for(Classroom classroom : classrooms){
            out.println(decorate(classroom,
                    subjectDAO.getSubjectNameByID(classroom.getSubjectID()) ));
        }
    %>
    </tbody>
</table>
</body>
</html>
