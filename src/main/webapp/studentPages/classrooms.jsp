<%@ page import="DAO.Mapping" %>
<%@ page import="Model.User" %>
<%@ page import="Model.Classroom" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.Interfaces.ClassroomDAO" %>
<%@ page import="DAO.Interfaces.SubjectDAO" %>
<%@ page import="DAO.Interfaces.UserDAO" %>
<%@ page import="DAO.Interfaces.LecturerDAO" %><%--
  Created by IntelliJ IDEA.
  User: dito
  Date: 21.07.22
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) request.getSession().getAttribute(Mapping.USER_OBJECT);
    String email = user.getEmail();
    ClassroomDAO classroomDAO = (ClassroomDAO) application.getAttribute(Mapping.CLASSROOM_DAO);
    SubjectDAO subjectDAO = (SubjectDAO) application.getAttribute(Mapping.SUBJECT_DAO);
    LecturerDAO lecturerDAO = (LecturerDAO) application.getAttribute(Mapping.LECTURER_DAO);
    List<Classroom> classrooms = classroomDAO.getClassroomsByStudent(email,false);
%>

<%!
    private String decorate(Classroom classroom, String sub_name, String lec_email){
        String result = " <tr>\n" +
                "            <td>" + sub_name + "</td>\n" +
                "            <td>" + classroom.getSemester()+ "</td>\n" +
                "            <td> " + lec_email + "</td>\n" +
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
                    <th><h1>Classroom's Lecturer</h1></th>
                    <th><h1>Classroom Semester</h1></th>
                    <th><h1>Visit Classroom</h1></th>
             </tr>
       </thead>

        <tbody>
            <%
                for(Classroom classroom : classrooms){
                    out.println(decorate(classroom,
                            subjectDAO.getSubjectNameByID(classroom.getSubjectID()),
                            lecturerDAO.getLecturerWithID(classroom.getLecturerID()).getEmail()));
                }
            %>
        </tbody>
 </table>
</body>
</html>
